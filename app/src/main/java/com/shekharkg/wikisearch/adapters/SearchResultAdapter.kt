package com.shekharkg.wikisearch.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shekharkg.wikisearch.R
import com.shekharkg.wikisearch.dao.Page
import com.shekharkg.wikisearch.viewholders.SearchResultViewHolder

/**
 * Created by shekhar on 7/12/18.
 */
class SearchResultAdapter(private var pages: List<Page>) : RecyclerView.Adapter<SearchResultViewHolder>() {

  private var clickListener: SearchItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, null, false)

    return SearchResultViewHolder(view)
  }

  override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
    holder.bindPage(pages[position])

    holder.itemView.setOnClickListener {
      if (clickListener != null)
        clickListener!!.onItemClicked(position)
    }
  }

  override fun getItemCount(): Int {
    return pages.size
  }

  fun setOnItemClickListener(listener: SearchItemClickListener) {
    this.clickListener = listener
  }

  interface SearchItemClickListener {
    fun onItemClicked(position: Int)
  }
}
