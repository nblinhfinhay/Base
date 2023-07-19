package com.nblinh.base.base.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBind(vhData: T?)

    open fun onBind(vhData: T?, payloads: List<Any>) {}
}