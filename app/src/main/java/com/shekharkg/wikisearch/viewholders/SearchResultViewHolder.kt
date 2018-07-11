package com.shekharkg.wikisearch.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.shekharkg.wikisearch.R

import com.shekharkg.wikisearch.dao.Page
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_search_result.view.*

/**
 * Created by shekhar on 7/12/18.
 */
class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  fun bindPage(page: Page) {
    itemView.titleTV.text = page.title
    itemView.descTV.text = page.description

    itemView.imageLoader.visibility = View.VISIBLE
    Picasso.get().load(page.thumbnailUrl)
        .placeholder(R.drawable.white_image)
        .into(itemView.resultIV, object : com.squareup.picasso.Callback {
          override fun onSuccess() {
            itemView.imageLoader.visibility = View.GONE
          }

          override fun onError(ex: Exception) {
            itemView.imageLoader.visibility = View.GONE
            itemView.resultIV.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_broken_image))
          }
        })
  }
}
