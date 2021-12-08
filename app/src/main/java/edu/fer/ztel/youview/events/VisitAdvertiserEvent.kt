package edu.fer.ztel.youview.events

import edu.fer.ztel.youview.Event
import edu.fer.ztel.youview.R
import edu.fer.ztel.youview.utils.Strings.get

class VisitAdvertiserEvent(override val handledBy: String) : Event() {
  override val textResources: List<String> = listOf(get(R.string.click_ad))
}