package com.shekharkg.wikisearch.api

/**
 * Created by shekhar on 7/11/18.
 */
interface CallBack {

  fun <T> successResponse(responseObject: T, statusCode: Int)

  fun failureResponse(jsonResponse: String, statusCode: Int)

}
