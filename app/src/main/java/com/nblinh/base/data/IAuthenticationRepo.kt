package com.nblinh.base.data

import com.nblinh.base.model.user.UserInfo

interface IAuthenticationRepo {

    fun login(userName: String, password: String): Boolean

    fun logout(): Boolean

    fun getUserInfo(): UserInfo
}