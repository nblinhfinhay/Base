package com.nblinh.base.presentation.recycler.normal

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.nblinh.base.base.BaseActivity
import com.nblinh.base.databinding.ActivityRecyclerViewNormalBinding


class RecyclerViewNormalActivity : BaseActivity() {

    var alert: AlertDialog.Builder? = null

    private val itemCallBack = object : IDemoItem {
        override fun onClick(item: DemoItem) {
            Toast.makeText(this@RecyclerViewNormalActivity, item.content, Toast.LENGTH_SHORT).show()
        }

        override fun onEdit(item: DemoItem, position: Int) {
            if (alert == null) {
                alert = AlertDialog.Builder(this@RecyclerViewNormalActivity)
            }
            val edittext = EditText(this@RecyclerViewNormalActivity)
            edittext.setText(item.content)
            alert?.apply {
                setMessage("Edit Popup")
                setTitle("Enter Your Content")
                setView(edittext)
                setPositiveButton(
                    "YES"
                ) { _, _ ->
                    val updateItem = DemoItem(edittext.text.toString())
                    adapter.updateItemAndNotify(position, updateItem)
                }

                setNegativeButton(
                    "NO"
                ) { _, _ ->
                }
            }.also { it?.show() }
        }

        override fun onDelete(item: DemoItem, position: Int) {
            adapter.removeItemAndNotify(position)
        }

    }

    private val adapter: NormalAdapter = NormalAdapter(this, itemCallBack)

    private lateinit var binding: ActivityRecyclerViewNormalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewNormalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.apply {
            rvDemo.adapter = adapter
            swRefresh.setOnRefreshListener {
                initList()
                binding.swRefresh.isRefreshing = false
            }

            btnAddFirst.setOnClickListener {
                adapter.addToFirst(DemoItem("Add First"))
            }

            btnAddLast.setOnClickListener {
                adapter.addItemAndNotify(DemoItem("Add Last"))
            }

            btnAddMiddle.setOnClickListener {
                adapter.addItemAtIndexAndNotify(DemoItem("Add Middle"), 1)
            }

            btnAddMultiFirst.setOnClickListener {
                adapter.addItemsAtIndexAndNotify(
                    listOf(
                        DemoItem("Content start 1"),
                        DemoItem("Content start 2"),
                        DemoItem("Content start 3"),
                    ), 0
                )
            }

            btnAddMultiMiddle.setOnClickListener {
                adapter.addItemsAtIndexAndNotify(
                    listOf(
                        DemoItem("Content middle 1"),
                        DemoItem("Content middle 2"),
                        DemoItem("Content middle 3"),
                    ), 2
                )
            }

            btnAddMultiLast.setOnClickListener {
                adapter.addItemsAndNotify(
                    listOf(
                        DemoItem("Content last 1"),
                        DemoItem("Content last 2"),
                        DemoItem("Content last 3"),
                    )
                )
            }


            btnRemoveMultiFirst.setOnClickListener {
                adapter.notifyItemRangeRemoved(0, 3)
            }
            btnRemoveMultiMiddle.setOnClickListener {
                adapter.notifyItemRangeRemoved(1, 3)
            }
            btnRemoveMultiLast.setOnClickListener {
                adapter.notifyItemRangeRemoved(2, adapter.itemCount - 1 - 2)
            }
        }

        initList()
    }

    private fun initList() {
        adapter.reset(
            listOf(
                DemoItem("Content 1"),
                DemoItem("Content 2"),
                DemoItem("Content 3"),
                DemoItem("Content 4"),
                DemoItem("Content 5")
            )
        )
    }
}

