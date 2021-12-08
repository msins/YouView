package edu.fer.ztel.youview.event_handlers

import android.view.accessibility.AccessibilityEvent
import edu.fer.ztel.youview.Event
import edu.fer.ztel.youview.EventHandler

class TypeViewFocused(event: AccessibilityEvent) : EventHandler(event) {
  override val eventType: String = "TYPE_VIEW_FOCUSED"
  override val trackedEvents: List<Event> = listOf()
}