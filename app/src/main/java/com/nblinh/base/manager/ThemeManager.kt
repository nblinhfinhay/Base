package com.nblinh.base.manager

import android.content.Context
import androidx.annotation.StyleRes

object ThemeManager {

    fun setTheme(context: Context, @StyleRes themeID: Int) {
        context.setTheme(themeID)
    }
}