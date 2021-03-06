package edu.fer.ztel.youview.networking

import edu.fer.ztel.youview.Metadata
import io.reactivex.rxjava3.core.Single
import retrofit2.adapter.rxjava3.Result
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface YouViewService {

  @FormUrlEncoded
  @POST("eventLog.php")
  fun sendEvent(@FieldMap eventDetails: Map<String, String>): Single<Result<Unit>>

//    batch send not supported by backend
//    @POST
//    fun sendEvents(@Body events: List<Event>): Single<Result<Unit>>

  @FormUrlEncoded
  @POST("deviceLog.php")
  fun sendMetadata(@FieldMap metadata: Metadata): Single<Result<Unit>>
}