package com.nblinh.base.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.nblinh.base.R
import com.nblinh.base.base.BaseActivity

class NewActivity : BaseActivity() {

    private lateinit var tvDemo: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        tvDemo = findViewById(R.id.tvDemo)
        findViewById<View>(R.id.btnTheme1).setOnClickListener {
            applyTheme(R.style.Theme_Light)

        }
        findViewById<View>(R.id.btnTheme2).setOnClickListener {
            applyTheme(R.style.Theme_Dark)
        }

        findViewById<View>(R.id.btnVietNam).setOnClickListener {
            setLanguage("vi")
        }
        findViewById<View>(R.id.btnEnglish).setOnClickListener {
            setLanguage("en")
        }
    }
}