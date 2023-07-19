package com.nblinh.base.base.api

interface IConverter<S, D> {
    fun convert(source: S): D
}