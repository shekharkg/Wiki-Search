package com.shekharkg.wikisearch

import android.app.Application
import com.shekharkg.wikisearch.utils.AppPreference

/**
 * Created by shekhar on 7/8/18.
 */
class WikiSearch: Application() {

  override fun onCreate() {
    super.onCreate()
    AppPreference().initPreferences(this)
  }

}