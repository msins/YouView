package edu.fer.ztel.youview

import java.time.LocalDateTime

abstract class Event {
  abstract val textResources: List<String>
  abstract val handledBy: String
  val time: LocalDateTime = LocalDateTime.now()
}
