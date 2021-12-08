package edu.fer.ztel.youview.events

import edu.fer.ztel.youview.Event
import edu.fer.ztel.youview.R
import edu.fer.ztel.youview.utils.Strings.get


class FastForwardEvent(override val handledBy: String) : Event() {
  override val textResources = listOf(get(R.string.fast_forward))
}