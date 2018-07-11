package com.shekharkg.wikisearch.dao

import com.google.gson.Gson
import com.shekharkg.wikisearch.utils.AppPreference
import com.shekharkg.wikisearch.utils.CustomList

/**
 * Created by shekhar on 7/9/18.
 */
object SuggestionData {

  @Synchronized
  fun getSuggestions(): List<Page> {
    val cachedData = AppPreference.getPreference(AppPreference.CACHED_DATA)

    return if (cachedData.isNullOrEmpty()) {
      ArrayList()
    } else {
      return Gson().fromJson(cachedData, CustomList(Page::class.java))
    }
  }

  @Synchronized
  fun saveSuggestions(pages: MutableList<Page>) {
    val cachedData = AppPreference.getPreference(AppPreference.CACHED_DATA)

    if (!cachedData.isNullOrEmpty()) {
      val cachedPages: List<Page> = Gson().fromJson(cachedData, CustomList(Page::class.java))
      pages.addAll(cachedPages)
    }

    AppPreference.setPreference(AppPreference.CACHED_DATA, Gson().toJson(pages))
  }

}
