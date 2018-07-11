package com.shekharkg.wikisearch.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.shekharkg.wikisearch.R

/**
 * Created by shekhar on 7/8/18.
 */
class SearchNowFragment : Fragment(), View.OnClickListener {

  private var listener: SearchOnClickListener? = null
  private var imageView: ImageView? = null

  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_search_now, container, false)
    imageView = view.findViewById(R.id.imageView)
    imageView!!.setOnClickListener(this)
    return view.rootView
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)

    if (context is SearchOnClickListener)
      listener = context
  }

  override fun onClick(view: View?) {
    if (view == imageView && listener != null)
      listener!!.onSearchNowClicked()
  }

  interface SearchOnClickListener {
    fun onSearchNowClicked()
  }

}
