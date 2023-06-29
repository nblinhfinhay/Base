package com.nblinh.base.api

import org.json.JSONObject
import retrofit2.Call
import java.io.IOException
import java.lang.Exception

fun <T, R : BaseResponse<T>> Call<R>.invokeApi(): Result<T> {
    try {
        val response = this.execute()
        if (response.isSuccessful) {
            val body: R = response.body() ?: return Result.UnknownError
            return if (body.isSuccess()) {
                Result.Success(body.data)
            } else {
                Result.ServerError(body.errorCode, body.message)
            }
        } else {
            val errorBody = response.errorBody()?.string()
            if (errorBody.isNullOrEmpty().not()) {
                val errorJson = JSONObject(errorBody!!)
                return Result.ServerError(
                    errorJson.getString("error_code"),
                    errorJson.getString("message")
                )
            }
            return Result.ServerError(response.errorBody().toString(), response.message())
        }
    } catch (e: Exception) {
        return if(e is IOException) {
            Result.NetWorkError
        } else {
            Result.UnknownError
        }
    }
}