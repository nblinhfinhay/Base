package com.nblinh.base.sample.github_project_list.repository.model.github_project

import com.nblinh.base.sample.github_project_list.domain.model.github_project.GithubProject
import com.nblinh.base.api.IConverter

class GithubProjectDataToGithubProject : IConverter<GithubProjectData, GithubProject> {
    override fun convert(source: GithubProjectData): GithubProject {
        return GithubProject(
            id = source.id,
            projectName = source.name
        )
    }
}