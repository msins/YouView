package edu.fer.ztel.youview.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Context.TELEPHONY_SERVICE
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings.Secure
import android.telephony.TelephonyManager
import android.telephony.TelephonyManager.*
import edu.fer.ztel.youview.utils.ConnectionType.*

enum class ConnectionType {
  OFFLINE, WIFI, NETWORK, VPN
}

class MetadataUtils {
  companion object {
    private fun getConnectionType(context: Context): ConnectionType {
      val cm = context.getSystemService(CONNECTIVITY_SERVICE) as? ConnectivityManager
      cm?.run {
        cm.getNetworkCapabilities(cm.activeNetwork)?.run {
          if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return WIFI
          }
          if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            return NETWORK
          }
          if (hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
            return VPN
          }
        }
      }

      return OFFLINE
    }

    fun getConnection(context: Context): String {
      return when (getConnectionType(context)) {
        WIFI -> "WiFi"
        OFFLINE -> "Offline"
        NETWORK -> {
          val tm = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
          try { // in case user has not given permission to read phone state
            when (tm.dataNetworkType) {
              NETWORK_TYPE_GPRS,
              NETWORK_TYPE_EDGE,
              NETWORK_TYPE_CDMA,
              NETWORK_TYPE_1xRTT,
              NETWORK_TYPE_IDEN,
              NETWORK_TYPE_GSM -> "2G"
              NETWORK_TYPE_UMTS,
              NETWORK_TYPE_EVDO_0,
              NETWORK_TYPE_EVDO_A,
              NETWORK_TYPE_HSDPA,
              NETWORK_TYPE_HSUPA,
              NETWORK_TYPE_HSPA,
              NETWORK_TYPE_EVDO_B,
              NETWORK_TYPE_EHRPD,
              NETWORK_TYPE_HSPAP,
              NETWORK_TYPE_TD_SCDMA -> "3G"
              NETWORK_TYPE_LTE,
              NETWORK_TYPE_IWLAN -> "4G"
              NETWORK_TYPE_NR -> "5G"
              else -> "Unknown"
            }
          } catch (e: SecurityException) {
            return "Unknown"
          }
        }
        VPN -> "VPN"
      }
    }

    fun getAndroidVersion(): String {
      return when (Build.VERSION.SDK_INT) {
        26, 27 -> "Oreo"
        28 -> "Pie"
        29 -> "Q"
        30 -> "11"
        31 -> "12"
        else -> "SDK ${Build.VERSION.SDK_INT}"
      }
    }

    fun getDimensions(): String {
      val metrics = Resources.getSystem().displayMetrics
      return "width: ${metrics.widthPixels}, height: ${metrics.heightPixels}"
    }

    fun getYoutubeVersion(context: Context): String {
      val packageInfo = context.packageManager.getPackageInfo("com.google.android.youtube", 0)
      return packageInfo.versionName
    }

    @SuppressLint("HardwareIds")
    fun getAndroidId(context: Context): String {
      return Secure.getString(context.contentResolver, Secure.ANDROID_ID) ?: "Unknown"
    }

    fun getDevice(): String = Build.DEVICE

    fun getModel(): String = Build.MODEL

    fun getSdkVersion(): String = Build.VERSION.SDK_INT.toString()
  }
}