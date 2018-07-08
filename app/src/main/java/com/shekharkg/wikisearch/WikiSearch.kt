package com.shekharkg.wikisearch

import android.app.Application
import com.shekharkg.wikisearch.utils.AppPreference

/**
 * Created by shekhar on 7/8/18.
 * Tricog Health Services Pvt Ltd © 2018 | All rights reserved
 */
class WikiSearch: Application() {

  override fun onCreate() {
    super.onCreate()
    AppPreference().initPreferences(this)
  }

}