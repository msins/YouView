package edu.fer.ztel.youview.event_handlers

import android.view.accessibility.AccessibilityEvent
import edu.fer.ztel.youview.Event
import edu.fer.ztel.youview.EventHandler
import edu.fer.ztel.youview.events.ResolutionChangedEvent

class TypeWindowStateChanged(event: AccessibilityEvent) : EventHandler(event) {
  override val eventType: String = "TYPE_WINDOW_STATE_CHANGED"
  override val trackedEvents: List<Event> = listOf(
    ResolutionChangedEvent(eventType)
  )
}