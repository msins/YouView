package edu.fer.ztel.youview.events

import edu.fer.ztel.youview.Event
import edu.fer.ztel.youview.R
import edu.fer.ztel.youview.utils.Strings.get

class ReplayEvent(override val handledBy: String) : Event() {
  override val textResources: List<String> = listOf(get(R.string.replay))
}