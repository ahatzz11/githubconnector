package com.hatz.githubconnector.dto.github

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class GithubUserResponse(
    @JsonProperty("login")
    val login: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("avatar_url")
    val avatarUrl: String,

    @JsonProperty("location")
    val location: String?,

    @JsonProperty("email")
    val email: String?,

    @JsonProperty("html_url")
    val htmlUrl: String,

    @JsonProperty("created_at")
    val createdAt: Instant
)

