package com.shekharkg.wikisearch.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shekharkg.wikisearch.R

/**
 * Created by shekhar on 7/8/18.
 */
class NoInternetFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_no_internet, container, false)
  }

}
