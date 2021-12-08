package edu.fer.ztel.youview.event_handlers

import android.view.accessibility.AccessibilityEvent
import edu.fer.ztel.youview.Event
import edu.fer.ztel.youview.EventHandler

class TypeViewAccessibilityFocused(event: AccessibilityEvent) : EventHandler(event) {
  override val eventType: String = "TYPE_VIEW_ACCESSIBILITY_FOCUSED"
  override val trackedEvents: List<Event> = listOf()
}