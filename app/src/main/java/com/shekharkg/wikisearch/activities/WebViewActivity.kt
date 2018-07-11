package com.shekharkg.wikisearch.activities

import android.net.http.SslError
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.google.gson.Gson
import com.shekharkg.wikisearch.R
import com.shekharkg.wikisearch.api.CallBack
import com.shekharkg.wikisearch.api.NetworkClient
import com.shekharkg.wikisearch.dao.Page
import com.shekharkg.wikisearch.utils.AppPreference
import com.shekharkg.wikisearch.utils.WikiUtils
import kotlinx.android.synthetic.main.activity_web.*
import org.json.JSONObject


/**
 * Created by shekhar on 7/12/18.
 */
class WebViewActivity : AppCompatActivity(), CallBack {

  private var page: Page? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_web)
    setSupportActionBar(toolbar)

    supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    val pageString = intent.getStringExtra("PAGE")
    if (pageString == null || pageString.length < 10) {
      showErrorAndExit("Page not found")
      return
    }

    page = Gson().fromJson(pageString, Page::class.java)
    if (page?.pageid == null) {
      showErrorAndExit("Page not found")
      return
    }

    titleTV.text = page!!.title

    webView.webViewClient = MyWebViewClient()
    webView.settings.javaScriptEnabled = true
    webView.webChromeClient = object : WebChromeClient() {
      override fun onProgressChanged(view: WebView, progress: Int) {
        if (progress == 100) {
          progressBar.visibility = View.GONE
        } else {
          progressBar.progress = progress
        }
      }
    }

    webView


    val fullUrl = AppPreference.getPreference(page!!.pageid!!)
    if (fullUrl.isNullOrEmpty())
      getFullUrl()
    else
      loadUrl(fullUrl!!)
  }

  private fun loadUrl(url: String) {
    webView.loadUrl(url)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item!!.itemId == android.R.id.home) {
      finish()
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  private fun getFullUrl() {
    if (WikiUtils.isNetworkConnected(this)) {
      NetworkClient.getMethod(NetworkClient.PAGE_BASE_URL + page!!.pageid!!, this)
    } else
      showErrorAndExit("No Internet Connection!!!")
  }

  private fun showErrorAndExit(error: String) {
    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    finish()
  }

  override fun <T> successResponse(responseObject: T, statusCode: Int) {
//    Log.e("SUCCESS", responseObject as String)

    val jsonObject = JSONObject(responseObject as String)
    if (jsonObject.has("query")
        && jsonObject.getJSONObject("query").has("pages")
        && jsonObject.getJSONObject("query").getJSONObject("pages").has(page!!.pageid!!)
        && jsonObject.getJSONObject("query").getJSONObject("pages")
            .getJSONObject(page!!.pageid!!).has("fullurl")) {

      val fullUrl = jsonObject.getJSONObject("query").getJSONObject("pages")
          .getJSONObject(page!!.pageid!!).getString("fullurl")

      if (!fullUrl.isNullOrEmpty()) {
        AppPreference.setPreference(page!!.pageid!!, fullUrl)
        loadUrl(fullUrl)
      } else
        showErrorAndExit("Page not found")
    } else
      showErrorAndExit("Page not found")
  }

  override fun failureResponse(jsonResponse: String, statusCode: Int) {
//    Log.e("FAILURE", jsonResponse)
    showErrorAndExit(jsonResponse)
  }

  private inner class MyWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
      view.loadUrl(url)
      return true
    }


    override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
      val builder = AlertDialog.Builder(this@WebViewActivity)
      var message = "SSL Certificate error."
      when (error.primaryError) {
        SslError.SSL_UNTRUSTED -> message = "The certificate authority is not trusted."
        SslError.SSL_EXPIRED -> message = "The certificate has expired."
        SslError.SSL_IDMISMATCH -> message = "The certificate Hostname mismatch."
        SslError.SSL_NOTYETVALID -> message = "The certificate is not yet valid."
      }
      message += " Do you want to continue anyway?"

      builder.setTitle("SSL Certificate Error")
      builder.setMessage(message)
      builder.setPositiveButton("continue") { dialog, which -> handler.proceed() }
      builder.setNegativeButton("cancel") { dialog, which -> handler.cancel() }

      try {
        val dialog = builder.create()
        dialog.show()
      } catch (ignored: Exception) {
      }

    }
  }


}
