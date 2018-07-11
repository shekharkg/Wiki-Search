package com.shekharkg.wikisearch.api

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler

/**
 * Created by shekhar on 7/11/18.
 */
object NetworkClient {

  private val BASE_URL = "https://en.wikipedia.org//w/api.php?action=query&format=json&prop=pageimages%7Cpageterms&generator=prefixsearch&redirects=1&formatversion=2&piprop=thumbnail&pithumbsize=50&pilimit=10&wbptterms=description&gpslimit=30&gpssearch="


  private val client = AsyncHttpClient(true, 80, 443)
  private val SOCKET_TIMEOUT = 20000


  @Synchronized
  fun getMethod(callBack: CallBack, query: String) {
    client.setTimeout(SOCKET_TIMEOUT)
    client.get(BASE_URL + query, responseHandler(callBack))
  }


  private fun responseHandler(callBack: CallBack): AsyncHttpResponseHandler {
    return object : AsyncHttpResponseHandler() {
      override fun onSuccess(statusCode: Int, headers: Array<cz.msebera.android.httpclient.Header>, responseBody: ByteArray) {
        try {
          callBack.successResponse<Any>(String(responseBody), statusCode)
        } catch (e: Exception) {
          e.printStackTrace()
          onFailure(statusCode, headers, responseBody, null)
        }

      }

      override fun onFailure(statusCode: Int, headers: Array<cz.msebera.android.httpclient.Header>, responseBody: ByteArray, error: Throwable?) {
        try {
          callBack.failureResponse(String(responseBody), statusCode)
        } catch (e: Exception) {
          e.printStackTrace()
          callBack.failureResponse("Something went wrong! Please try again.", statusCode)
        }

      }

    }
  }


}
