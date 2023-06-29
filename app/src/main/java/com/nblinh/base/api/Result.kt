package com.nblinh.base.api

sealed class Result<out T> {
    data class Success<out T>(val data: T?) : Result<T>()
    data class ServerError(val errorCode: String?, val message: String?) : Result<Nothing>()
    object NetWorkError : Result<Nothing>()
    object UnknownError : Result<Nothing>()
}