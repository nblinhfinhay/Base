package com.nblinh.base.model.user

data class UserInfo(
    val userName: String,
    val fullName: String,
    val gender: Gender,
    val contact: UserContact
)

data class UserContact(
    val phone: String,
    val address: String,
    val city: String,
)

enum class Gender { MALE, FEMALE, OTHER }