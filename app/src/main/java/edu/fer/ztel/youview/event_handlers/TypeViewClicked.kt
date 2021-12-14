package edu.fer.ztel.youview.event_handlers

import android.view.accessibility.AccessibilityEvent
import edu.fer.ztel.youview.Event
import edu.fer.ztel.youview.EventHandler
import edu.fer.ztel.youview.events.*

class TypeViewClicked(event: AccessibilityEvent) : EventHandler(event) {
  override val eventType: String = "TYPE_VIEW_CLICKED"
  override val trackedEvents: List<Event> = listOf(
    PlayEvent(eventType),
    PauseEvent(eventType),
    FullscreenOffEvent(eventType),
    FullscreenOnEvent(eventType),
    NextVideoEvent(eventType),
    PreviousVideoEvent(eventType),
    RewindEvent(eventType),
    FastForwardEvent(eventType),
    SkipAdsEvent(eventType),
    VisitAdvertiserEvent(eventType),
    ReplayEvent(eventType)
  )
}