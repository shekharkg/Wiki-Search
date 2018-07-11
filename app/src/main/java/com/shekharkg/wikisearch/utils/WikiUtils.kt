package com.shekharkg.wikisearch.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.view.LayoutInflater
import com.shekharkg.wikisearch.R

/**
 * Created by shekhar on 7/8/18.
 */
object WikiUtils {

  fun isNetworkConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
  }

  fun progressDialog(context: Context): Dialog {
    val dialog = Dialog(context)
    val inflate = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
    dialog.setContentView(inflate)
    dialog.setCancelable(false)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    return dialog
  }


}
