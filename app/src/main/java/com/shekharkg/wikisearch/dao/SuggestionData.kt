package com.shekharkg.wikisearch.dao

import com.google.gson.Gson
import com.shekharkg.wikisearch.utils.AppPreference
import com.shekharkg.wikisearch.utils.CustomList

/**
 * Created by shekhar on 7/9/18.
 */
object SuggestionData {

  fun getSuggestions(): List<Page> {
    val cachedData = AppPreference.getPreference(AppPreference.GET_CACHED_DATA)
    return if (cachedData.isNullOrEmpty()) {
      ArrayList()
    } else {
      return Gson().fromJson(cachedData, CustomList(Page::class.java))
    }
  }

}
