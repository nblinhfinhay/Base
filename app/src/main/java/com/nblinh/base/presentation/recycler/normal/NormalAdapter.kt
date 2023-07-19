package com.nblinh.base.presentation.recycler.normal

import android.content.Context
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.nblinh.base.R
import com.nblinh.base.base.view.BaseRecyclerViewAdapter
import com.nblinh.base.base.view.BaseViewHolder

data class DemoItem(
    var content: String
)

class NormalAdapter(context: Context, val itemCallBack: IDemoItem) :
    BaseRecyclerViewAdapter(context) {

    override fun getLayoutResource(layoutResourceID: Int): Int = R.layout.item_demo

    override fun onCreateVH(view: View, viewType: Int): BaseViewHolder<*> = NormalVH(view)

    private inner class NormalVH(itemView: View) : BaseViewHolder<DemoItem>(itemView) {

        private var tvContent: TextView
        private var ivbEdit: ImageButton
        private var ivbDelete: ImageButton

        init {
            tvContent = itemView.findViewById(R.id.tvContent)
            ivbEdit = itemView.findViewById(R.id.ivbEdit)
            ivbDelete = itemView.findViewById(R.id.ivbDelete)
            itemView.setOnClickListener {
                itemCallBack.onClick(getItemDataAtPosition(bindingAdapterPosition) as DemoItem)
            }
            ivbEdit.setOnClickListener {
                itemCallBack.onEdit(
                    getItemDataAtPosition(bindingAdapterPosition) as DemoItem,
                    bindingAdapterPosition
                )
            }

            ivbDelete.setOnClickListener {
                itemCallBack.onDelete(
                    getItemDataAtPosition(bindingAdapterPosition) as DemoItem,
                    bindingAdapterPosition
                )
            }
        }

        override fun onBind(vhData: DemoItem?) {
            if (vhData != null) {
                tvContent.text = vhData.content
                ivbEdit.isEnabled = true
                ivbDelete.isEnabled = true
            } else {
                tvContent.text = ""
                ivbEdit.isEnabled = false
                ivbDelete.isEnabled = false
            }
        }
    }
}

interface IDemoItem {

    fun onClick(item: DemoItem)

    fun onEdit(item: DemoItem, position: Int)

    fun onDelete(item: DemoItem, position: Int)
}