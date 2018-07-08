package com.shekharkg.wikisearch.utils

import android.app.SearchManager
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.google.gson.Gson
import com.shekharkg.wikisearch.dao.SuggestionData

/**
 * Created by shekhar on 7/9/18.
 */
class WikiSearchContentProvider : ContentProvider() {

  override fun onCreate(): Boolean {
    return true
  }

  override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?,
                     sortOrder: String?): Cursor? {

    return when (uriMatcher.match(uri)) {
      1 -> {
        val query = uri.lastPathSegment.toLowerCase()
        getSearchResultsCursor(query)
      }
      else -> null
    }
  }

  private fun getSearchResultsCursor(query: String?): MatrixCursor {
    var searchString = query
    val searchResults = MatrixCursor(matrixCursorColumns)
    var counter = 0
    val mRow = arrayOfNulls<Any>(4)
    if (searchString != null) {
      searchString = searchString.toLowerCase()

      for (page in SuggestionData.getSuggestions()) {
        if (page.title!!.toLowerCase().contains(searchString)) {
          mRow[0] = "" + counter++
          mRow[1] = page.title
          mRow[2] = page.description
          mRow[3] = Gson().toJson(page)

          searchResults.addRow(mRow)
        }
      }
    }
    return searchResults
  }

  override fun getType(uri: Uri): String? {
    return null
  }

  override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
    return null
  }

  override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
    return 0
  }

  override fun update(uri: Uri, contentValues: ContentValues?, s: String?, strings: Array<String>?): Int {
    return 0
  }

  companion object {

    private const val STORES = "stores/" + SearchManager.SUGGEST_URI_PATH_QUERY + "/*"

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
      uriMatcher.addURI("com.shekharkg.wikisearch", STORES, 1)
    }

    private val matrixCursorColumns = arrayOf("_id", SearchManager.SUGGEST_COLUMN_TEXT_1,
        SearchManager.SUGGEST_COLUMN_TEXT_2, SearchManager.SUGGEST_COLUMN_INTENT_DATA)
  }
}
