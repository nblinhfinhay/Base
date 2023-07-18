package com.nblinh.base.presentation

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nblinh.base.R
import com.nblinh.base.base.BaseActivity
import kotlinx.coroutines.launch
import timber.log.Timber

class APIActivity : BaseActivity() {

    lateinit var btnStart: Button
    lateinit var btnStop: Button

    private val viewModel: APIViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)
        btnStop.isEnabled = false

        btnStart.setOnClickListener {
            startCallAPI()
        }
        btnStop.setOnClickListener {
            stopCallAPI()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginFlow.collect { loginState ->
                    if (loginState.loading) {
                        Timber.d("LOGIN show Loading")
                        return@collect
                    }
                    Timber.d("LOGIN hide Loading")
                    when (loginState.success) {
                        true -> Timber.d("LOGIN Login Success")
                        else -> Timber.d("LOGIN Login Fail")
                    }
                }
            }
        }
    }

    private fun startCallAPI() {
        viewModel.login()
    }

    private fun stopCallAPI() {

    }
}

