package com.nblinh.base.sample.github_project_list.repository.api

import com.nblinh.base.api.BaseResponse
import com.nblinh.base.sample.github_project_list.repository.model.github_project.GithubProjectData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubAPI {

    @GET("/{user}")
    fun getGithubProjectList(@Path("user") user: String?): Call<BaseResponse<List<GithubProjectData>?>>
}