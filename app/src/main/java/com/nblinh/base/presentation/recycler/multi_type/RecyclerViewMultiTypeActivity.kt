package com.nblinh.base.presentation.recycler.multi_type

import android.os.Bundle
import com.nblinh.base.base.BaseActivity
import com.nblinh.base.databinding.ActivityRecyclerViewMultiTypeBinding
import com.nblinh.base.presentation.recycler.normal.DemoItem

class RecyclerViewMultiTypeActivity : BaseActivity() {

    private lateinit var binding: ActivityRecyclerViewMultiTypeBinding

    private lateinit var adapter: MultiTypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewMultiTypeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        adapter = MultiTypeAdapter(this)
        binding.rvDemo.adapter = adapter
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

