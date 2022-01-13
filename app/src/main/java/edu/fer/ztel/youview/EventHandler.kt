package edu.fer.ztel.youview

import android.view.accessibility.AccessibilityEvent
import edu.fer.ztel.youview.event_handlers.*

abstract class EventHandler(private val accessibilityEvent: AccessibilityEvent) {
  abstract val eventType: String

  abstract val trackedEvents: List<Event>

  private fun anyMatch(): MatchedEvent? {
    for (event in trackedEvents) {
      val match = event.findMatchOrNull(accessibilityEvent)
      if (match != null) {
        return MatchedEvent(match, event)
      }
    }

    return null
  }

  fun ifMatches(onEventMatched: (MatchedEvent) -> Unit) {
    val event = anyMatch() ?: return
    onEventMatched(event)
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