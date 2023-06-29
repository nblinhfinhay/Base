package com.nblinh.base.api

import com.google.gson.annotations.SerializedName

open class BaseResponse<T>(
    @field:SerializedName("status")
    var status: Int? = null,

    @field:SerializedName("error_code")
    var errorCode: String? = null,

    @field:SerializedName("message")
    var message: String? = null,

    @field:SerializedName("data")
    var data: T? = null

) {
    open fun isSuccess(): Boolean {
        return status == 0
    }
}