package com.hatz.githubconnector.dto.github

import com.fasterxml.jackson.annotation.JsonProperty

data class GithubRepoResponse(

    @JsonProperty("name")
    val name: String,

    @JsonProperty("html_url")
    val url: String
)
