package com.hatz.githubconnector.service

import com.hatz.githubconnector.dto.UserResponse
import com.hatz.githubconnector.dto.fromGithubUserInformation
import org.springframework.stereotype.Service

@Service
class UserService(
    private val githubService: GithubService
) {

    /**
     * Returns all the information for a given user
     *
     * @param username - the username to get information for
     */
    fun getUserInformation(username: String): UserResponse {

        return fromGithubUserInformation(
            githubUser = githubService.getGithubUserInformation(username),
            githubRepos = githubService.getGithubUserRepos(username)
        )
    }
}

