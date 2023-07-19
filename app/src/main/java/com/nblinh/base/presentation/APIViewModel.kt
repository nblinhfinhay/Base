package com.nblinh.base.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nblinh.base.base.api.RetrofitClient
import com.nblinh.base.base.api.invokeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class APIViewModel : ViewModel() {

    private val _loginFlow = MutableSharedFlow<BaseState<Boolean>>()
    val loginFlow: SharedFlow<BaseState<Boolean>> = _loginFlow

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            _loginFlow.emit(BaseState(loading = true))
            val isLoginSuccess = RetrofitClient.apiInterface.login().invokeApi {
                it.data == true
            }
            _loginFlow.emit(BaseState(success = isLoginSuccess))
        }
    }
}