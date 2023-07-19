package com.nblinh.base.base.api

import org.json.JSONObject
import retrofit2.Call
import java.io.IOException

fun <T, API : BaseResponse<T>, R> Call<API>.invokeApi(block: (API) -> R): R {
    try {
        val response = this.execute()
        if (response.isSuccessful) {
            val body: API = response.body() ?: throw APIException(APIException.UNKNOWN_ERROR)
            if (body.isSuccess()) {
                return block(body)
            } else {
                throw APIException(body.errorCode, body.message)
            }
        } else {
            val errorBody = response.errorBody()?.string()
            if (!errorBody.isNullOrEmpty()) {
                val errorJson = JSONObject(errorBody)
                throw APIException(
                    errorJson.getString(BaseResponseKey.ERROR_CODE_KEY),
                    errorJson.getString(BaseResponseKey.STATUS_KEY)
                )
            }

            throw APIException(response.errorBody().toString(), response.message())
        }
    } catch (e: Exception) {
        if (e is IOException) {
            throw APIException(APIException.NETWORK_ERROR, e)
        } else {
            throw APIException(APIException.UNKNOWN_ERROR)
        }
    }
}