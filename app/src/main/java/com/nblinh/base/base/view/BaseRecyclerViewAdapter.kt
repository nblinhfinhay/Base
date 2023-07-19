package com.nblinh.base.base.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter(context: Context? = null) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var mDataSet: MutableList<Any?> = arrayListOf()
    var asyncLayoutInflater: AsyncLayoutInflater? = null

    init {
        context?.let {
            asyncLayoutInflater = AsyncLayoutInflater(it)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return onCreateVH(
            LayoutInflater.from(viewGroup.context)
                .inflate(getLayoutResource(viewType), viewGroup, false), viewType
        )
    }

    override fun getItemCount(): Int = mDataSet.size

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        (holder as BaseViewHolder<Any>).onBind(getItemDataAtPosition(position))
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(
        holder: BaseViewHolder<*>, position: Int, payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            (holder as BaseViewHolder<Any>).onBind(getItemDataAtPosition(position), payloads)
        }
    }

    abstract fun getLayoutResource(@LayoutRes layoutResourceID: Int): Int

    abstract fun onCreateVH(view: View, viewType: Int): BaseViewHolder<*>

    open fun getItemDataAtPosition(position: Int): Any? {
        return mDataSet[position]
    }

    fun addItem(item: Any?) {
        mDataSet.add(item)
    }

    fun addItemAndNotify(item: Any?) {
        mDataSet.add(item)
        notifyItemInserted(mDataSet.size - 1)
    }

    open fun addItems(items: List<Any?>) {
        mDataSet.addAll(items)
    }

    fun addItemsAndNotify(items: List<Any?>) {
        val currentSize = mDataSet.size
        mDataSet.addAll(items)
        notifyItemRangeInserted(currentSize, items.size)
    }

    fun addItemAtIndex(item: Any?, index: Int) {
        mDataSet.add(index, item)
    }

    fun addItemAtIndexAndNotify(index: Int, item: Any?) {
        mDataSet.add(index, item)
        notifyItemInserted(index)
    }

    fun addItemsAtIndex(items: List<Any?>, index: Int) {
        mDataSet.addAll(index, items)
    }

    fun removeItemAndNotify(index: Int) {
        mDataSet.removeAt(index)
        notifyItemRemoved(index)
    }

    fun updateItemAndNotify(index: Int, item: Any?) {
        mDataSet[index] = item
        notifyItemChanged(index)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reset(newItems: List<Any?>) {
        mDataSet.clear()
        addItems(newItems)
        notifyDataSetChanged()
    }

    fun clearData() = mDataSet.clear()
}