package edu.fer.ztel.youview.utils

import android.content.Context

class MetadataCollector(private val context: Context) {

  fun collect(): Map<String, String> {
    return mapOf(
      "android_id" to MetadataUtils.getAndroidId(context),
      "version" to MetadataUtils.getAndroidVersion(),
      "api" to MetadataUtils.getSdkVersion(),
      "dimensions" to MetadataUtils.getDimensions(),
      "yt_version" to MetadataUtils.getYoutubeVersion(context),
      "device" to MetadataUtils.getDevice(),
      "model" to MetadataUtils.getModel()
    )
  }
}