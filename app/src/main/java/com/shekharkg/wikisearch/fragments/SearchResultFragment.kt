package com.shekharkg.wikisearch.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.shekharkg.wikisearch.R
import com.shekharkg.wikisearch.activities.WebViewActivity
import com.shekharkg.wikisearch.adapters.SearchResultAdapter
import com.shekharkg.wikisearch.dao.Page
import com.shekharkg.wikisearch.utils.CustomList
import android.support.v7.widget.DividerItemDecoration


/**
 * Created by shekhar on 7/8/18.
 */
class SearchResultFragment : Fragment(), SearchResultAdapter.SearchItemClickListener {

  private var pages: List<Page> = ArrayList()
  private var recyclerView: RecyclerView? = null


  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?, savedInstanceState: Bundle?): View? {

    val pagesString = arguments!!.getString("PAGES")
    if (pagesString != null)
      pages = Gson().fromJson(pagesString, CustomList(Page::class.java))

    val view = inflater.inflate(R.layout.fragment_search_result, container, false)

    recyclerView = view.findViewById(R.id.recyclerView)

    return view.rootView
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    recyclerView!!.layoutManager = LinearLayoutManager(context)
    recyclerView!!.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))

    val adapter = SearchResultAdapter(pages)
    adapter.setOnItemClickListener(this)
    recyclerView!!.adapter = adapter
  }

  override fun onItemClicked(position: Int) {
    val intent = Intent(context, WebViewActivity::class.java)
    intent.putExtra("PAGE", Gson().toJson(pages[position]))
    startActivity(intent)
  }


}
