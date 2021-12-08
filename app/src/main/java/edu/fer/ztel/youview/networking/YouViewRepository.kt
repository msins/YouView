package edu.fer.ztel.youview.networking

import edu.fer.ztel.youview.Metadata
import io.reactivex.rxjava3.core.Single
import retrofit2.adapter.rxjava3.Result

class YouViewRepository(private val service: YouViewService) {
  fun send(eventMap: Map<String, String>): Single<Result<Unit>> {
    return service.sendEvent(eventMap)
  }

  fun sendMetadata(metadata: Metadata): Single<Result<Unit>> {
    return service.sendMetadata(metadata)
  }
}