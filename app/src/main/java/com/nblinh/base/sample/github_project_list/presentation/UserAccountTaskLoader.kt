package com.nblinh.base.sample.github_project_list.presentation

import android.content.Context
import androidx.loader.content.AsyncTaskLoader
import com.nblinh.base.api.BaseResponse
import com.nblinh.base.api.Result
import com.nblinh.base.api.RetrofitClient
import com.nblinh.base.api.invokeApi
import com.nblinh.base.sample.github_project_list.domain.model.github_project.GithubProject
import com.nblinh.base.sample.github_project_list.repository.model.github_project.GithubProjectData
import com.nblinh.base.sample.github_project_list.repository.model.github_project.GithubProjectDataToGithubProject
import retrofit2.Call


class UserAccountTaskLoader(
    context: Context,
    private val param: String
) :
    AsyncTaskLoader<List<GithubProject>>(context) {
    override fun loadInBackground(): List<GithubProject> {
        var listItem: List<GithubProject> = listOf()

        val call = RetrofitClient.apiInterface.getGithubProjectList(param)
        val result = call.invokeApi()
        when (result) {
            Result.NetWorkError -> {}
            is Result.ServerError -> {}
            is Result.Success -> {
//                val list =    result.data?.let {
//                    GithubProjectDataToGithubProject().convert(result.data)
//                } ?: GithubProject()
//                return listOf(list)
                return emptyList()
            }

            Result.UnknownError -> {}
        }
        return emptyList()
    }
}