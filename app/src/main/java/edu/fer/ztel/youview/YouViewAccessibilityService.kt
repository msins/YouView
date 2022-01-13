package edu.fer.ztel.youview

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import edu.fer.ztel.youview.networking.Config
import edu.fer.ztel.youview.networking.YouViewRepository
import edu.fer.ztel.youview.utils.MetadataCollector
import edu.fer.ztel.youview.utils.MetadataUtils
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

  private val matchedEvents = UnicastSubject.create<MatchedEvent>()
  private val disposable = CompositeDisposable()

  init {
    disposable.add(
      matchedEvents
        .subscribeOn(Schedulers.trampoline())
        .map {
          val (text, event) = it
          mapOf(
            "android_id" to metadata["id"]!!,
            "event_type" to event.handledBy,
            "event" to event::class.simpleName.toString(),
            "text" to text,
            "connection" to MetadataUtils.getConnection(this),
            "time" to event.time.toString(),
          )
        }
        .flatMapSingle { eventMap -> repository.send(eventMap) }
        .subscribe(
          { Log.d(TAG, "Sent successfully ${it.response()}") },
          this::onError
        )
    )
    context = WeakReference(this)
  }

  override fun onCreate() {
    metadata = MetadataCollector(applicationContext).collect()
    disposable.add(
      repository.sendMetadata(metadata)
        .subscribeOn(Schedulers.trampoline())
        .subscribe(
          { Log.d(TAG, "Metadata sent successfully. ${it.response()}") },
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
    if (!isEventValid(event)) {
      return
    }

    EventHandler.forEvent(event).ifMatches { matchedEvents.onNext(it) }
  }

  private fun isEventValid(event: AccessibilityEvent): Boolean {
    return event.contentDescription != null && event.text.isNotEmpty()
  }

  override fun onInterrupt() {
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