package com.nblinh.base

import android.util.Log
import timber.log.Timber

class ReleaseTree : Timber.Tree() {
    override fun log(
        priority: Int, tag: String?,
        message: String, t: Throwable?
    ) {
        if (priority != Log.ERROR && priority != Log.WARN) return
        //log to your Log server
    }
}