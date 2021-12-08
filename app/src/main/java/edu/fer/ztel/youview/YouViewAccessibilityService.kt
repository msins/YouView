package edu.fer.ztel.youview

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import edu.fer.ztel.youview.networking.Config
import edu.fer.ztel.youview.networking.YouViewRepository
import edu.fer.ztel.youview.utils.MetadataCollector
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.UnicastSubject
import java.lang.ref.WeakReference

typealias Metadata = Map<String, String>

class YouViewAccessibilityService : AccessibilityService() {
  companion object {
    val TAG = YouViewAccessibilityService::class.qualifiedName
    var repository = YouViewRepository(Config.service)
    lateinit var context: WeakReference<Context> private set
    lateinit var metadata: Metadata
  }

  private val history = UnicastSubject.create<Event>()
  private val disposable = CompositeDisposable()

  init {
    disposable.add(
      history
        .subscribeOn(Schedulers.trampoline())
        .doOnNext { Log.d(TAG, "Event: ${it.handledBy}") }
        .map { event ->
          mapOf(
            "android" to metadata["android_id"]!!,
            "event" to event.handledBy,
            "content" to "test",
            "video" to "remove this",
            "button" to "remove this",
            "connection" to metadata["connection"]!!,
            "time" to event.time.toString(),
          )
        }
        .flatMap { eventMap -> repository.send(eventMap).toObservable() }
        .subscribe()
    )
    context = WeakReference(this)
  }

  override fun onCreate() {
    metadata = MetadataCollector(applicationContext).collect()
    disposable.add(
      repository.sendMetadata(metadata)
        .subscribeOn(Schedulers.trampoline())
        .subscribe(
          { Log.d(TAG, "Metadata sent successfully.") },
          this::onError
        )
    )
  }

  private fun onError(throwable: Throwable) {
    Log.e(TAG, throwable.toString())
    Log.e(TAG, "Error occurred. Assuming research is over. Shutting down.")
    disableSelf()
  }

  override fun onAccessibilityEvent(event: AccessibilityEvent) {
    //ignore non parsable events

    if (event.contentDescription == null && event.text.isEmpty()) {
      return
    }

    EventHandler.forEvent(event).ifValid { history.onNext(it) }
  }

  override fun onInterrupt() {
    //todo
    Log.w(TAG, "Interrupt")
  }

  override fun onServiceConnected() {
    serviceInfo = AccessibilityServiceInfo().apply {
      packageNames = arrayOf("com.google.android.youtube")
      eventTypes = AccessibilityEvent.TYPES_ALL_MASK
      feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK
    }
  }

  override fun onDestroy() {
    if (disposable.isDisposed) return
    disposable.dispose()
  }

}

