package com.shekharkg.wikisearch.api

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by shekhar on 7/9/18.
 */
interface ApiInterface {

//  https://en.wikipedia.org//w/
  // api.php
  // ?action=query
  // &format=json
  // &prop=pageimages%7Cpageterms
  // &generator=prefixsearch
  // &redirects=1
  // &formatversion=2
  // &piprop=thumbnail
  // &pithumbsize=50
  // &pilimit=10
  // &wbptterms=description
  // &gpslimit=10
  // &gpssearch=Sachin+T

  @GET("api.php")
  fun searchWiki(
      @Query("action") action: String = "query",
      @Query("format") format: String = "json",
      @Query("prop") pageimages: String = "pageimages|pageterms",
      @Query("generator") generator: String = "prefixsearch",
      @Query("redirects") redirects: Int = 1,
      @Query("formatversion") formatversion: Int = 2,
      @Query("piprop") piprop: String = "thumbnail",
      @Query("pithumbsize") pithumbsize: Int = 50,
      @Query("pilimit") pilimit: Int = 10,
      @Query("wbptterms") wbptterms: String = "description",
      @Query("gpslimit") gpslimit: Int = 30,
      @Query("gpssearch") gpssearch: String
  ): Call<JSONObject>

}