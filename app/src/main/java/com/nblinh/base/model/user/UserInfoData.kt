package com.nblinh.base.model.user

import com.google.gson.annotations.SerializedName

class UserInfoData {
    @SerializedName("user_name")
    val name: String? = null

    @SerializedName("full_name")
    val fullName: String? = null

    @SerializedName("address")
    val address: String? = null

    @SerializedName("phone")
    val phone: String? = null

    @SerializedName("city")
    val city: String? = null

    @SerializedName("gender")
    val gender: Int? = null
}