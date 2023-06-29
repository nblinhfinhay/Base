package com.nblinh.base.sample.github_project_list.repository.api

import retrofit2.Retrofit

class MainRetrofitFactory : RetrofitFactory() {
    override fun <T> create(service: Class<T>, vararg params: Any?): Retrofit.Builder? {
        return null
    }

}