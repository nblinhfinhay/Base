package com.nblinh.base.sample.github_project_list.repository.api

import retrofit2.Retrofit

abstract class RetrofitFactory() {
    abstract fun <T> create(service: Class<T>, vararg params: Any?) : Retrofit.Builder?
}