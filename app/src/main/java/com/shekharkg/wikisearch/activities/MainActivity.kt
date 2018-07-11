package com.shekharkg.wikisearch.activities

import android.app.Dialog
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import com.shekharkg.wikisearch.R
import com.shekharkg.wikisearch.api.CallBack
import com.shekharkg.wikisearch.api.NetworkClient
import com.shekharkg.wikisearch.dao.Page
import com.shekharkg.wikisearch.dao.SuggestionData
import com.shekharkg.wikisearch.fragments.NoInternetFragment
import com.shekharkg.wikisearch.fragments.NoResultFoundFragment
import com.shekharkg.wikisearch.fragments.SearchNowFragment
import com.shekharkg.wikisearch.fragments.SearchResultFragment
import com.shekharkg.wikisearch.utils.WikiUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), CallBack, SearchNowFragment.SearchOnClickListener {

  private val fragmentManager = supportFragmentManager
  private var dialog: Dialog? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setSupportActionBar(toolbar)

    addFragment(SearchNowFragment())

    dialog = WikiUtils.progressDialog(this)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val inflater = menuInflater
    inflater.inflate(R.menu.menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.search_wiki -> {
        super.onSearchRequested()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onSearchNowClicked() {
    super.onSearchRequested()
  }

  private fun addFragment(fragment: Fragment) {
    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
  }

  override fun onNewIntent(intent: Intent) {
    setIntent(intent)
    handleSearch()
  }

  private fun handleSearch() {
    val intent = intent
    if (Intent.ACTION_SEARCH == intent.action) {
      val searchQuery = intent.getStringExtra(SearchManager.QUERY)
      searchWiki(searchQuery.replace(" ", "+"))
    } else if (Intent.ACTION_VIEW == intent.action) {
      val selectedPageId = intent.dataString
      Toast.makeText(this, "ELSE: selected search suggestion " + selectedPageId!!,
          Toast.LENGTH_SHORT).show()
    }
  }

  private fun searchWiki(query: String) {
    if (WikiUtils.isNetworkConnected(this)) {
      dialog!!.show()
      NetworkClient.getMethod(this, query)
    } else
      addFragment(NoInternetFragment())
  }

  override fun <T> successResponse(responseObject: T, statusCode: Int) {
    dialog!!.dismiss()
    val list = parseResponseData(responseObject as String)
    Log.e("SUCCESS", responseObject as String)

    if (list.isNotEmpty()) {
      SuggestionData.saveSuggestions(list)
      val searchResultFragment = SearchResultFragment()
      val bundle = Bundle()
      bundle.putString("PAGES", Gson().toJson(list))
      searchResultFragment.arguments = bundle
      addFragment(searchResultFragment)
    } else
      addFragment(NoResultFoundFragment())
  }

  override fun failureResponse(jsonResponse: String, statusCode: Int) {
    dialog!!.dismiss()
    Log.e("FAILURE", jsonResponse)
    Toast.makeText(this, jsonResponse, Toast.LENGTH_SHORT).show()
  }

  private fun parseResponseData(responseData: String): MutableList<Page> {
    val jsonObject = JSONObject(responseData)

    val pageList: MutableList<Page> = mutableListOf()

    if (jsonObject.getJSONObject("query") != null &&
        jsonObject.getJSONObject("query").getJSONArray("pages") != null &&
        jsonObject.getJSONObject("query").getJSONArray("pages").length() > 0) {

      val pagesArray = jsonObject.getJSONObject("query").getJSONArray("pages");

      for (i in 0..(pagesArray.length() - 1)) {
        val pageObj = pagesArray.getJSONObject(i)

        val pageId: String = pageObj.getString("pageid")
        val title: String = pageObj.getString("title")

        var thumbnail: String? = null
        var description: String? = null

        if (pageObj.has("thumbnail") && pageObj.getJSONObject("thumbnail").has("source"))
          thumbnail = pageObj.getJSONObject("thumbnail").getString("source")

        if (pageObj.has("terms") && pageObj.getJSONObject("terms").has("description")) {
          val descArray = pageObj.getJSONObject("terms").getJSONArray("description")
          description = descArray.join(", ").replace("\"", "")
        }

        pageList.add(Page(pageId, title, thumbnail, description))

      }

    }

    return pageList
  }

}
