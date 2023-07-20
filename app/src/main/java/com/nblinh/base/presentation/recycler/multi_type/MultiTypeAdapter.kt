package com.nblinh.base.presentation.recycler.multi_type

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import com.nblinh.base.R
import com.nblinh.base.base.view.BaseRecyclerViewAdapter
import com.nblinh.base.base.view.BaseViewHolder
import com.nblinh.base.presentation.recycler.normal.DemoItem

object Types {
    const val HEADER = 1
    const val ITEM = 2
    const val BANNER = 3
}

class MultiTypeAdapter(context: Context) : BaseRecyclerViewAdapter(context) {

    override fun getItemViewType(position: Int): Int {
        return if (mDataSet.isNotEmpty()) {
            when (position) {
                0 -> Types.HEADER
                3 -> if (mDataSet.size < 4) Types.ITEM else Types.BANNER
                else -> Types.ITEM
            }
        } else {
            super.getItemViewType(position)
        }
    }

    override fun getItemCount(): Int {
        return if (mDataSet.isEmpty()) 0 else if (mDataSet.size < 4) mDataSet.size + 1 else mDataSet.size + 2
    }

    override fun getItemDataAtPosition(position: Int): Any? {
        return if (mDataSet.isNotEmpty()) {
            when (position) {
                0 -> null
                else -> if (mDataSet.size < 4) {
                    mDataSet[position - 1]
                } else if (position == 3) {
                    null
                } else if(position < 3){
                    mDataSet[position - 1]
                } else {
                    mDataSet[position - 2]
                }
            }
        } else {
            super.getItemDataAtPosition(position)
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            Types.HEADER -> R.layout.item_header
            Types.BANNER -> R.layout.item_banner
            else -> R.layout.item_demo
        }
    }

    override fun onCreateVH(view: View, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            Types.HEADER -> BannerVH(view)
            Types.BANNER -> HeaderVH(view)
            else -> NormalVH(view)
        }
    }

    private class HeaderVH(itemView: View) : BaseViewHolder<Any>(itemView) {
        override fun onBind(vhData: Any?) {
        }
    }

    private class BannerVH(itemView: View) : BaseViewHolder<Any>(itemView) {
        override fun onBind(vhData: Any?) {
            (itemView as TextView).text = "This is Header Title"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private inner class NormalVH(itemView: View) : BaseViewHolder<DemoItem>(itemView) {

        private var tvContent: TextView
        private var ivbEdit: ImageButton
        private var ivbDelete: ImageButton
        private var cbSelect: CheckBox

        init {
            tvContent = itemView.findViewById(R.id.tvContent)
            ivbEdit = itemView.findViewById(R.id.ivbEdit)
            ivbDelete = itemView.findViewById(R.id.ivbDelete)
            cbSelect = itemView.findViewById(R.id.cbSelect)
        }

        override fun onBind(vhData: DemoItem?) {
            if (vhData != null) {
                tvContent.text = vhData.content
                ivbEdit.isEnabled = true
                ivbDelete.isEnabled = true
                cbSelect.visibility =
                    if (vhData.selectAble) View.VISIBLE else View.GONE
                cbSelect.isChecked = vhData.isSelected
            } else {
                tvContent.text = ""
                ivbEdit.isEnabled = false
                ivbDelete.isEnabled = false
                cbSelect.visibility = View.GONE
            }
        }

        override fun onBind(vhData: DemoItem?, payloads: List<Any>) {
            payloads.firstOrNull()?.let {
                if (it is String) {
                    tvContent.text = it
                }
            }
        }
    }
}