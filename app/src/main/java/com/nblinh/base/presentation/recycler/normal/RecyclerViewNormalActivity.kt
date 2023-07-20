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
                    item.content = edittext.text.toString()
                    adapter.updateItemAndNotify(position, item, item.content)
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
                adapter.removeItemsTopIndexAndNotify(3)
            }
            btnRemoveMultiMiddle.setOnClickListener {
                adapter.removeItemsAndNotify(2, 3)
            }
            btnRemoveMultiLast.setOnClickListener {
                adapter.removeItemsFromIndexAndNotify(3)
            }

            btnShowAllSelected.setOnClickListener {
                Toast.makeText(
                    this@RecyclerViewNormalActivity,
                    adapter.showAllSelected(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            btnClearAllSelected.setOnClickListener {
                adapter.removeItemAndNotify(DemoItem(content = "ahihi"))
                while (adapter.checkedHashMap.isNotEmpty()) {
                    val key = adapter.checkedHashMap.keys.first()
                    adapter.checkedHashMap[key]?.let {
                        adapter.removeItemAndNotify(it)
                    }
                    adapter.checkedHashMap.remove(key)
                }
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

