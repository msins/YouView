package edu.fer.ztel.youview.utils

import androidx.annotation.StringRes
import edu.fer.ztel.youview.YouViewAccessibilityService

object Strings {
  fun get(@StringRes resource: Int): String {
    return YouViewAccessibilityService.context.get()!!.getString(resource)
  }
}