package com.shekharkg.wikisearch.utils

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by shekhar on 7/9/18.
 */
class CustomList<T>(wrapped: Class<T>) : ParameterizedType {

  private val wrapped: Class<*>

  init {
    this.wrapped = wrapped
  }

  override fun getActualTypeArguments(): Array<Type> {
    return arrayOf(wrapped)
  }

  override fun getRawType(): Type {
    return List::class.java
  }

  override fun getOwnerType(): Type? {
    return null
  }
}
