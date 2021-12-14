package edu.fer.ztel.youview.utils

import android.content.Context
import edu.fer.ztel.youview.BuildConfig
import java.time.LocalDateTime

class MetadataCollector(private val context: Context) {

  fun collect(): Map<String, String> {
    return mapOf(
      "id" to MetadataUtils.getAndroidId(context),
      "android_version" to MetadataUtils.getAndroidVersion(),
      "api" to MetadataUtils.getSdkVersion(),
      "screen_size" to MetadataUtils.getDimensions(),
      "youtube_version" to MetadataUtils.getYoutubeVersion(context),
      "device" to MetadataUtils.getDevice(),
      "model" to MetadataUtils.getModel(),
      "service_version" to BuildConfig.VERSION_NAME
    )
  }
}