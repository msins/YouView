package edu.fer.ztel.youview

import android.view.accessibility.AccessibilityEvent
import java.time.LocalDateTime

abstract class Event {
  abstract val textResources: List<String>
  abstract val handledBy: String
  val time: LocalDateTime = LocalDateTime.now()

  /**
   * Override to compare something other than text.
   */
  fun findMatchOrNull(accessibilityEvent: AccessibilityEvent): String? {
    val accessibilityTexts = accessibilityEvent.text.map { it.toString().lowercase() }
    return textResources.find { accessibilityTexts.contains(it) }
  }
}
