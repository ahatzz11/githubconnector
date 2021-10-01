package com.hatz.githubconnector.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.hatz.githubconnector.dto.github.GithubRepoResponse

data class RepoResponse(
    @JsonProperty("name")
    val name: String,

    @JsonProperty("url")
    val url: String
)

/**
 * Map a github user repo list to our own RepoResponse
 */
fun fromGithubRepoInformation(githubRepo: GithubRepoResponse) =
    RepoResponse(
        name = githubRepo.name,
        url = githubRepo.url
    )


