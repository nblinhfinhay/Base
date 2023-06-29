package com.nblinh.base.sample.github_project_list.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.loader.app.LoaderManager
import androidx.loader.app.LoaderManager.LoaderCallbacks
import androidx.loader.content.Loader
import com.nblinh.base.R
import com.nblinh.base.base.BaseActivity
import com.nblinh.base.sample.github_project_list.domain.model.github_project.GithubProject


class GithubProjectsActivity : BaseActivity() {
    private val LOADER_ID_USERACCOUNT = 100001


    lateinit var btnStart: Button
    lateinit var btnStop: Button

    lateinit var loaderManager: LoaderManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)
        btnStop.isEnabled = false

        btnStart.setOnClickListener {
            startCallAPI()
        }
        btnStop.setOnClickListener {
            stopCallAPI()
        }

        loaderManager = LoaderManager.getInstance(this)

    }

    private fun startCallAPI() {
        val loader = loaderManager.initLoader(
            LOADER_ID_USERACCOUNT,
            bundleOf(Pair("USER_NAME", "users")),
            loaderCallbacks
        )
        loader.forceLoad()
    }

    private fun stopCallAPI() {
        loaderManager.getLoader<List<GithubProject>>(LOADER_ID_USERACCOUNT)?.cancelLoad()
    }

    val loaderCancelListener = Loader.OnLoadCanceledListener<List<GithubProject>> {
        Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
        btnStart.isEnabled = true
        btnStop.isEnabled = false
    }


    private val loaderCallbacks = object : LoaderCallbacks<List<GithubProject>> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<GithubProject>> {
            btnStart.isEnabled = false
            btnStop.isEnabled = true
            val param = args?.getString("USER_NAME") ?: ""
            val loader = UserAccountTaskLoader(this@GithubProjectsActivity, param)
            loader.registerOnLoadCanceledListener(loaderCancelListener)
            return loader
        }

        override fun onLoaderReset(loader: Loader<List<GithubProject>>) {

        }

        override fun onLoadFinished(
            loader: Loader<List<GithubProject>>,
            data: List<GithubProject>?
        ) {
            loaderManager.destroyLoader(loader.id)
            Toast.makeText(this@GithubProjectsActivity, "Success ${data?.size ?: 0}", Toast.LENGTH_LONG).show()
            btnStart.isEnabled = true
            btnStop.isEnabled = false
        }

    }
}

