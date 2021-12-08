package edu.fer.ztel.youview.event_handlers

import android.view.accessibility.AccessibilityEvent
import edu.fer.ztel.youview.Event
import edu.fer.ztel.youview.EventHandler

class EmptyHandler(event: AccessibilityEvent) : EventHandler(event) {
  override val eventType: String = "don't care"
  override val trackedEvents: List<Event> = listOf()
}