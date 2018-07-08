package com.shekharkg.wikisearch.activities

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.shekharkg.wikisearch.R
import com.shekharkg.wikisearch.api.ApiClient
import com.shekharkg.wikisearch.fragments.NoInternetFragment
import com.shekharkg.wikisearch.fragments.NoResultFoundFragment
import com.shekharkg.wikisearch.utils.WikiUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), Callback<JSONObject> {

  private val fragmentManager = supportFragmentManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setSupportActionBar(toolbar)
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

  override fun onResume() {
    super.onResume()

    if (!WikiUtils.isNetworkConnected(this))
      addFragment(NoInternetFragment())
    else
      addFragment(NoResultFoundFragment())
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
      val selectedSuggestionRowId = intent.dataString
      Toast.makeText(this, "ELSE: selected search suggestion " + selectedSuggestionRowId!!,
          Toast.LENGTH_SHORT).show()
    }
  }

  private fun searchWiki(query: String) {
    Toast.makeText(this, query, Toast.LENGTH_SHORT).show()

    if (WikiUtils.isNetworkConnected(this))
      ApiClient().getApiInterface()
          .searchWiki(gpssearch = query).enqueue(this)
    else
      addFragment(NoInternetFragment())
  }

  override fun onResponse(call: Call<JSONObject>?, response: Response<JSONObject>?) {
    Log.e("SUCCESS", response!!.body().toString())
  }

  override fun onFailure(call: Call<JSONObject>?, t: Throwable?) {
    Log.e("FAILURE", "")
  }

}
