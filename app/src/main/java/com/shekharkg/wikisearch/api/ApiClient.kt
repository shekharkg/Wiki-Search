package com.shekharkg.wikisearch.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by shekhar on 7/9/18.
 */
class ApiClient {

  private val BASE_URL = "https://en.wikipedia.org//w/"
  private var retrofit: Retrofit? = null


  private val gson = GsonBuilder()
      .setLenient()
      .create()


  private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
      .readTimeout(30, TimeUnit.SECONDS)
      .connectTimeout(30, TimeUnit.SECONDS)

  private fun getClient(): Retrofit {
    if (retrofit == null) {
      retrofit = Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create(gson))
          .client(httpClient.build())
          .build()
    }
    return retrofit!!
  }

  fun getApiInterface(): ApiInterface = ApiClient().getClient().create(ApiInterface::class.java)
}