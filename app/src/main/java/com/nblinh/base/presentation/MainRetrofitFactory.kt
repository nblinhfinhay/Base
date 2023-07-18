package com.nblinh.base.presentation

import retrofit2.Retrofit

class MainRetrofitFactory : RetrofitFactory() {
    override fun <T> create(service: Class<T>, vararg params: Any?): Retrofit.Builder? {
        return null
    }

}