package com.nblinh.base.api

class APIException : Exception {

    companion object {
        const val UNKNOWN_ERROR = "UNKNOWN_ERROR"
        const val NETWORK_ERROR = "NETWORK_ERROR"
    }

    var errorCode: String? = null

    constructor() : super()

    constructor(errorCode: String?) : super() {
        this.errorCode = errorCode
    }

    constructor(errorCode: String?, message: String?) : super(message) {
        this.errorCode = errorCode
    }

    constructor(message: String?, t: Throwable) : super(message, t)

}