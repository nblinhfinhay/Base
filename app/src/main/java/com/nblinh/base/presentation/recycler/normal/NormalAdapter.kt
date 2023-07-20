package com.nblinh.base.presentation.recycler.normal

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import com.nblinh.base.R
import com.nblinh.base.base.view.BaseRecyclerViewAdapter
import com.nblinh.base.base.view.BaseViewHolder

data class DemoItem(
    var content: String,
    var isSelected: Boolean = false,
    var selectAble: Boolean = false
)

class NormalAdapter(context: Context, val itemCallBack: IDemoItem) :
    BaseRecyclerViewAdapter(context) {

    val checkedHashMap: HashMap<Int, DemoItem> = hashMapOf()

    override fun getLayoutResource(viewType: Int): Int = R.layout.item_demo

    override fun onCreateVH(view: View, viewType: Int): BaseViewHolder<*> = NormalVH(view)

    fun showAllSelected(): String {
        var contentSelected = ""
        checkedHashMap.values.forEach { contentSelected += "${it.content}," }
        return contentSelected
    }

    fun clearAllSelected(): HashMap<Int, DemoItem> {
        mDataSet.forEach {
            (it as? DemoItem)?.let { item ->
                item.isSelected = false
                item.selectAble = false
            }
        }
        return checkedHashMap
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun selectAbleToggle() {
        mDataSet.forEach {
            (it as? DemoItem)?.let { item ->
                item.selectAble = true
            }
        }
        notifyDataSetChanged()
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
            itemView.setOnClickListener {
                itemCallBack.onClick(getItemDataAtPosition(bindingAdapterPosition) as DemoItem)
            }

            itemView.setOnLongClickListener {
                val item = getItemDataAtPosition(bindingAdapterPosition) as DemoItem
                if (item.selectAble.not()) {
                    item.isSelected = true
                    this@NormalAdapter.checkedHashMap[bindingAdapterPosition] = item
                    this@NormalAdapter.selectAbleToggle()
                }
                true
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
                this@NormalAdapter.checkedHashMap.remove(bindingAdapterPosition)
            }

            cbSelect.setOnCheckedChangeListener { _, isChecked ->
                val item = getItemDataAtPosition(bindingAdapterPosition) as DemoItem
                item.isSelected = isChecked
                if (isChecked) {
                    this@NormalAdapter.checkedHashMap[bindingAdapterPosition] = item
                } else {
                    this@NormalAdapter.checkedHashMap.remove(bindingAdapterPosition)
                }
            }
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

interface IDemoItem {

    fun onClick(item: DemoItem)

    fun onEdit(item: DemoItem, position: Int)

    fun onDelete(item: DemoItem, position: Int)
}