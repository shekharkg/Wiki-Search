package com.shekharkg.wikisearch.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.shekharkg.wikisearch.R
import com.shekharkg.wikisearch.fragments.NoInternetFragment
import com.shekharkg.wikisearch.fragments.NoResultFoundFragment
import com.shekharkg.wikisearch.utils.WikiUtils

class MainActivity : AppCompatActivity() {

  private val fragmentManager = supportFragmentManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
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
