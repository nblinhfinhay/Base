package com.nblinh.base.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}


class AppViewModel : ViewModel() {

    private var _theme = MutableLiveData<Event<Int>>()
    val theme: LiveData<Event<Int>>
        get() = _theme

    fun setTheme(themeRes: Int) {
        _theme.value = Event(themeRes)
    }

}