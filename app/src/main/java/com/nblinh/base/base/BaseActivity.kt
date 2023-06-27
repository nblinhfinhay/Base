package com.nblinh.base.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.nblinh.base.sharedPreferences.PreferenceManager

open class BaseActivity : AppCompatActivity() {
    //open lateinit var appViewModel: AppViewModel
    private var currentTheme: Int? = null

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        PreferenceManager(applicationContext).getCurrentTheme().let {
            currentTheme = it
            setTheme(it)
        }
        super.onCreate(savedInstanceState)
//        appViewModel = ViewModelProvider(this)[AppViewModel::class.java]
//        appViewModel.theme.observe(this) { event ->
//            event?.getContentIfNotHandled()?.let { theme ->
//                Log.d("CHANGE THEME", "CHANGE")
//                ThemeManager.setTheme(this, theme)
//                recreate()
//            }
//        }
    }

    override fun onResume() {
        super.onResume()
        with(PreferenceManager(this).getCurrentTheme()) {
            if (currentTheme != this) {
                currentTheme = this
                applyTheme(this)
            }
        }
    }

    protected fun applyTheme(@StyleRes themeID: Int) {
        PreferenceManager(applicationContext).setCurrentTheme(themeID)
        setTheme(themeID)
        recreate()
    }

    protected fun setLanguage(language: String) {
        val appLocale: LocaleListCompat =
            LocaleListCompat.forLanguageTags(language)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}