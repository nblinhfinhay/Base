package com.nblinh.base.model.user

import com.nblinh.base.api.IConverter

class UserInfoDataToUserInfo : IConverter<UserInfoData, UserInfo> {
    override fun convert(source: UserInfoData): UserInfo {
        return UserInfo(
            userName = source.name ?: "",
            fullName = source.fullName ?: "",
            gender = when (source.gender) {
                0 -> Gender.FEMALE
                1 -> Gender.MALE
                else -> Gender.OTHER
            },
            contact = UserContact(
                phone = source.phone ?: "",
                address = source.address ?: "",
                city = source.city ?: "",
            )
        )
    }
}