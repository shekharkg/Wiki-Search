package com.shekharkg.wikisearch.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.shekharkg.wikisearch.R
import com.shekharkg.wikisearch.dao.Page
import com.shekharkg.wikisearch.utils.CustomList

/**
 * Created by shekhar on 7/8/18.
 */
class SearchResultFragment : Fragment() {

  private var pages: List<Page> = ArrayList()


  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?, savedInstanceState: Bundle?): View? {

    val pagesString = arguments!!.getString("PAGES")
    if (pagesString != null)
      pages = Gson().fromJson(pagesString, CustomList(Page::class.java))


    return inflater.inflate(R.layout.fragment_search_result, container, false)
  }


}
