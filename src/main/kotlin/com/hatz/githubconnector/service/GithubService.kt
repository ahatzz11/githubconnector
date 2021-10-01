package com.hatz.githubconnector.service

import com.hatz.githubconnector.dto.github.GithubRepoResponse
import com.hatz.githubconnector.dto.github.GithubUserResponse
import com.hatz.githubconnector.exception.UserNotFoundException
import com.hatz.githubconnector.retrofit.GithubApi
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import retrofit2.Response

/**
 * Grabs information from the github api
 */
@Service
class GithubService(
    private val githubApi: GithubApi
) {

    /**
     * Pulls user data from the github api
     *
     * @param username - the username to get from github
     * @throws UserNotFoundException - when a user isn't found
     */
    fun getGithubUserInformation(username: String): GithubUserResponse {
        val response: Response<GithubUserResponse> = githubApi.getGithubUser(username).execute()

        // handle user not found
        if (response.code() == HttpStatus.NOT_FOUND.value()) {
            throw UserNotFoundException(username)
        }

        // future: add other error handling
        // if (!response.isSuccessful) {

        // return response body
        // todo improve nullability
        return response.body()!!
    }

    /**
     * Pulls a users repo data from the github api.
     *
     * This call currently is always called _after_ getGithubUserInformation, so handling of the username
     * not existing is probably overkill. If aysnc calls are ever added, each should have their own error handling
     *
     * @param username - the username to get from github
     */
    fun getGithubUserRepos(username: String): List<GithubRepoResponse> {
        val response: Response<List<GithubRepoResponse>> = githubApi.getRepos(username).execute()

        // future: add other error handling
        // if (!response.isSuccessful) {

        // return response body
        // todo improve nullability
        return response.body()!!
    }
}
