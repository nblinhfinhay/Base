package com.nblinh.base.presentation

import retrofit2.Retrofit

abstract class RetrofitFactory() {
    abstract fun <T> create(service: Class<T>, vararg params: Any?) : Retrofit.Builder?
}