package com.hatz.githubconnector.controller

import com.hatz.githubconnector.dto.UserResponse
import com.hatz.githubconnector.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("github_connector/v1/users")
class UserController(val userService: UserService) {

    /**
     * GET controller for a single user
     *
     * @param username - the user to get
     */
    @GetMapping("{username}")
    @ResponseStatus(HttpStatus.OK)
    fun getGithubUserInformation(
        @PathVariable username: String,
    ): UserResponse {
        return userService.getUserInformation(username)
    }
}
