package com.shekharkg.wikisearch.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

/**
 * Created by shekhar on 7/8/18.
 * Tricog Health Services Pvt Ltd © 2018 | All rights reserved
 */


public class AppPreference {
  private var sharedPreference: SharedPreferences? = null
  private val MSG_ILLEGAL_ARGS = "The argument should be the application context!"
  private val VALUE_NOT_SET: String? = null


  fun initPreferences(applicationContext: Context) {
    if (applicationContext is Activity) {
      throw IllegalArgumentException(MSG_ILLEGAL_ARGS)
    } else if (sharedPreference == null) {
      sharedPreference = applicationContext.getSharedPreferences(
          "WikiSearchData", Context.MODE_PRIVATE)
    }
  }

  @Synchronized
  fun getPreference(preference: String): String? {
    return if (sharedPreference == null) null else sharedPreference!!.getString(preference, VALUE_NOT_SET)
  }

  @Synchronized
  fun setPreference(preference: String, value: String) {
    if (sharedPreference == null)
      return
    sharedPreference!!.edit().putString(preference, value).apply()
  }

  @Synchronized
  fun removePreference(preference: String) {
    if (sharedPreference == null)
      return
    sharedPreference!!.edit().remove(preference).apply()
  }

  @Synchronized
  fun clearAllPreference() {
    if (sharedPreference == null)
      return
    sharedPreference!!.edit().clear().apply()
  }
}