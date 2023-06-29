package com.nblinh.base.api

interface IConverter<S, D> {
    fun convert(source: S): D
}