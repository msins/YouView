package edu.fer.ztel.youview

import android.view.accessibility.AccessibilityEvent
import edu.fer.ztel.youview.event_handlers.*

abstract class EventHandler(private val accessibilityEvent: AccessibilityEvent) {
  abstract val eventType: String

  /**
   * It might be weird why there are multiple event handlers, each with it's own tracked event list.
   *
   * Problem is that accessibility events could be published with different types ex.
   * Event with the text "Pause video" could appear when the pause button is focused or clicked,
   * since we are interested only in the one caused by a click, we can't have a global
   * list of tracked events.
   *
   * Could be implemented with some filters but this is easier.
   */
  abstract val trackedEvents: List<Event>

  private fun anyMatch(): Pair<String, Event>? {
    for (event in trackedEvents) {
      val match = event.findMatchOrNull(accessibilityEvent)
      if (match != null) {
        return Pair(match, event)
      }
    }

    return null
  }


  fun ifValid(onValidEvent: (Pair<String, Event>) -> Unit) {
    val event = anyMatch() ?: return
    onValidEvent(event)
  }

  companion object {
    fun forEvent(event: AccessibilityEvent): EventHandler {
      return when (event.eventType) {
        AccessibilityEvent.TYPE_VIEW_CLICKED -> {
          TypeViewClicked(event)
        }
        AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
          TypeViewFocused(event)
        }
        AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED -> {
          TypeViewAccessibilityFocused(event)
        }
        AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
          TypeWindowStateChanged(event)
        }
        else -> {
          EmptyHandler(event)
        }
      }
    }
  }
}