package com.nblinh.base.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.nblinh.base.R
import com.nblinh.base.base.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var tvDemo: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvDemo = findViewById(R.id.tvDemo)
        findViewById<View>(R.id.btnTheme1).setOnClickListener {
            applyTheme(R.style.Theme_Light)

        }
        findViewById<View>(R.id.btnTheme2).setOnClickListener {
            applyTheme(R.style.Theme_Dark)
        }
        findViewById<View>(R.id.btnGoToNewActivity).setOnClickListener {
            startActivity(Intent(this, NewActivity::class.java))
        }
    }
}

