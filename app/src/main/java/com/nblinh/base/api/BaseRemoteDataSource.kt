package com.nblinh.base.api

import org.json.JSONObject
import retrofit2.Call
import java.io.IOException

abstract class BaseRemoteDataSource {
    fun <T, R : BaseResponse<T>> getResult(apiCall: Call<R>): Result<T> {
        try {
            val response = apiCall.execute()
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
        } catch (e: IOException) {
            return Result.NetWorkError
        }
    }
}