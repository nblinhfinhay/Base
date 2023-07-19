package com.nblinh.base.data.remote.api

import com.nblinh.base.base.api.BaseResponse
import com.nblinh.base.model.user.UserInfoData
import retrofit2.Call
import retrofit2.http.GET

interface AuthService {

    @GET("/login")
    fun login(
    ): Call<BaseResponse<Boolean>>

    @GET("/userInfo")
    fun getUserInfo(
    ): Call<BaseResponse<UserInfoData>>
}