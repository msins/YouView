package edu.fer.ztel.youview.events

import edu.fer.ztel.youview.Event
import edu.fer.ztel.youview.R
import edu.fer.ztel.youview.utils.Strings.get

class AccessibilityTurnedOff(override val handledBy: String) : Event() {
  override val textResources: List<String> = listOf(get(R.string.accessibility_turned_on))
}