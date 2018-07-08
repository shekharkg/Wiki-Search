package com.shekharkg.wikisearch.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.shekharkg.wikisearch.R
import com.shekharkg.wikisearch.fragments.NoInternetFragment
import com.shekharkg.wikisearch.fragments.NoResultFoundFragment
import com.shekharkg.wikisearch.utils.WikiUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

  private val fragmentManager = supportFragmentManager
  private var toast: Toast? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)
    setSupportActionBar(toolbar)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {

    menuInflater.inflate(R.menu.menu, menu)

    val searchItem = menu.findItem(R.id.search_wiki)
    val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
    searchView.setOnQueryTextListener(searchViewQueryListener)

    return super.onCreateOptionsMenu(menu)
  }

  private var searchViewQueryListener = object : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(s: String): Boolean {
      toast?.setText("Submitted: $s")
      toast?.show()
      return false
    }

    override fun onQueryTextChange(s: String): Boolean {
      toast?.setText("Typing: $s")
      toast?.show()
      return false
    }
  }


  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item!!.itemId == R.id.search_wiki) {
      super.onSearchRequested()
      return true
    }

    return super.onOptionsItemSelected(item)
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
}
