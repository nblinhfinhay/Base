package com.nblinh.base.presentation

data class BaseState<T>(
    val loading: Boolean = false,
    val success: T? = null,
    val error: Int? = null
)