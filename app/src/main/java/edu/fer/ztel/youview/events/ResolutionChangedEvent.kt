package edu.fer.ztel.youview.events

import edu.fer.ztel.youview.Event
import edu.fer.ztel.youview.R
import edu.fer.ztel.youview.utils.Strings.get

class ResolutionChangedEvent(override val handledBy: String) : Event() {
  override val textResources = listOf(
    get(R.string.to2160p),
    get(R.string.to1440p),
    get(R.string.to1080p),
    get(R.string.to720p),
    get(R.string.to480p),
    get(R.string.to360p),
    get(R.string.to240p),
    get(R.string.to144p)
  )
}