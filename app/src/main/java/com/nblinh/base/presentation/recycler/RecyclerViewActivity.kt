package com.nblinh.base.presentation.recycler

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.nblinh.base.R
import com.nblinh.base.base.BaseActivity
import com.nblinh.base.presentation.recycler.load_more.RecyclerViewLoadMoreActivity
import com.nblinh.base.presentation.recycler.multi_type.RecyclerViewMultiTypeActivity
import com.nblinh.base.presentation.recycler.normal.RecyclerViewNormalActivity

class RecyclerViewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        findViewById<View>(R.id.btnNormal).setOnClickListener {
            startActivity(Intent(this, RecyclerViewNormalActivity::class.java))
        }

        findViewById<View>(R.id.btnMultiType).setOnClickListener {
            startActivity(Intent(this, RecyclerViewMultiTypeActivity::class.java))
        }

        findViewById<View>(R.id.btnLoadMore).setOnClickListener {
            startActivity(Intent(this, RecyclerViewLoadMoreActivity::class.java))

        }

    }

}
