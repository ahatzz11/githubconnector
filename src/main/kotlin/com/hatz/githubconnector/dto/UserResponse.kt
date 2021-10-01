package com.hatz.githubconnector.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.hatz.githubconnector.dto.github.GithubRepoResponse
import com.hatz.githubconnector.dto.github.GithubUserResponse
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class UserResponse(
    @JsonProperty("user_name")
    val userName: String,

    @JsonProperty("display_name")
    val displayName: String,

    @JsonProperty("avatar")
    val avatar: String,

    @JsonProperty("geo_location")
    val geoLocation: String?,

    @JsonProperty("email")
    val email: String?,

    @JsonProperty("url")
    val url: String,

    @JsonProperty("created_at")
    val createdAt: String,

    @JsonProperty("repos")
    val repos: List<RepoResponse>
)

/**
 * Map a github user and github repo list to our own UserResponse
 */
fun fromGithubUserInformation(githubUser: GithubUserResponse, githubRepos: List<GithubRepoResponse>): UserResponse {
    return UserResponse(
        userName = githubUser.login,
        displayName = githubUser.name,
        avatar = githubUser.avatarUrl,
        geoLocation = githubUser.location,
        email = githubUser.email,
        url = githubUser.htmlUrl,
        createdAt = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone( ZoneId.of("UTC"))
            .format(githubUser.createdAt),
        repos = githubRepos.map { repo -> fromGithubRepoInformation(repo) }
    )
}

