package com.hatz.githubconnector.retrofit

import com.hatz.githubconnector.dto.github.GithubRepoResponse
import com.hatz.githubconnector.dto.github.GithubUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("users/{username}")
    fun getGithubUser(@Path("username") username: String): Call<GithubUserResponse>

    @GET("users/{username}/repos")
    fun getRepos(@Path("username") username: String): Call<List<GithubRepoResponse>>

}
