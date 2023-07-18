package com.nblinh.base.api

import com.google.gson.annotations.SerializedName

object BaseResponseKey {
    const val STATUS_KEY = "status"
    const val ERROR_CODE_KEY = "error_code"
    const val MESSAGE_KEY = "message"
    const val DATA_KEY = "data"
}

open class BaseResponse<T>(
    @field:SerializedName(BaseResponseKey.STATUS_KEY)
    var status: Int? = null,

    @field:SerializedName(BaseResponseKey.ERROR_CODE_KEY)
    var errorCode: String? = null,

    @field:SerializedName(BaseResponseKey.MESSAGE_KEY)
    var message: String? = null,

    @field:SerializedName(BaseResponseKey.DATA_KEY)
    var data: T? = null

) {
    open fun isSuccess(): Boolean {
        return status == 0
    }
}