package com.shekharkg.wikisearch.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by shekhar on 7/8/18.
 */
object WikiUtils {

  fun isNetworkConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
  }

}
