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

    fun addItemAtIndex(item: Any?, index: Int) {
        mDataSet.add(index, item)
        if (index <= mDataSet.size - 1) {
            mDataSet.add(index, item)
        } else {
            mDataSet.add(item)
        }
    }

    fun addItems(items: List<Any?>) {
        mDataSet.addAll(items)
    }

    fun addItemsAtIndex(items: List<Any?>, index: Int) {
        mDataSet.addAll(index, items)
    }

    fun removeItemAtIndex(index: Int): Any? {
        return if (index >= 0 && index <= mDataSet.size - 1) {
            mDataSet.removeAt(index)
        } else {
            null
        }
    }

    fun updateItemAtIndex(index: Int, item: Any?) {
        if (index >= 0 && index <= mDataSet.size - 1) {
            mDataSet[index] = item
        }
    }


    fun addItemAndNotify(item: Any?) {
        mDataSet.add(item)
        notifyItemInserted(mDataSet.size - 1)
    }


    fun addItemsAndNotify(items: List<Any?>) {
        val currentSize = mDataSet.size
        mDataSet.addAll(items)
        notifyItemRangeInserted(currentSize, items.size)
    }

    fun addItemAtIndexAndNotify(item: Any?, index: Int) {
        if (index <= mDataSet.size - 1) {
            mDataSet.add(index, item)
            notifyItemInserted(index)
        } else {
            addItemAndNotify(item)
        }
    }

    fun addToFirst(item: Any?) {
        addItemAtIndexAndNotify(item, 0)
    }

    fun addItemsAtIndexAndNotify(items: List<Any?>, index: Int) {
        if (index <= mDataSet.size - 1) {
            mDataSet.addAll(index, items)
            notifyItemRangeInserted(index, items.size)
        } else {
            addItemsAndNotify(items)
        }
    }

    fun removeItemAndNotify(index: Int) {
        if (index >= 0 && index <= mDataSet.size - 1) {
            mDataSet.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun removeItemsAndNotify(startPosition: Int, removeItemCount: Int) {
        if (startPosition >= 0
            && startPosition <= mDataSet.size - 1
            && removeItemCount <= mDataSet.size - 1 - startPosition
        ) {
           // mDataSet.removA
        }

    }

    fun updateItemAndNotify(index: Int, item: Any?) {
        if (index >= 0 && index <= mDataSet.size - 1) {
            mDataSet[index] = item
            notifyItemChanged(index)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reset(newItems: List<Any?>) {
        mDataSet.clear()
        addItems(newItems)
        notifyDataSetChanged()
    }

    fun clearData() = mDataSet.clear()

    @SuppressLint("NotifyDataSetChanged")
    fun clearAndNotify() {
        mDataSet.clear()
        notifyDataSetChanged()
    }
}