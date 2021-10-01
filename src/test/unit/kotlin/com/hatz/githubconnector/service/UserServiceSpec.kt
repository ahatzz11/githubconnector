package com.hatz.githubconnector.service

import com.hatz.githubconnector.dto.UserResponse
import com.hatz.githubconnector.dto.github.GithubRepoResponse
import com.hatz.githubconnector.dto.github.GithubUserResponse
import io.kotest.core.spec.style.FunSpec
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import java.time.Instant
import java.util.concurrent.TimeUnit

class UserServiceSpec : FunSpec({

    val githubService = mockk<GithubService>()

    val userService = UserService(githubService)

    afterTest { clearAllMocks() }

    test("get user information") {
        // given a username
        val username: String = "octocat"

        every { githubService.getGithubUserInformation(username) } returns GithubUserResponse(
            login = username,
            name = "octocat",
            avatarUrl = "https://avatars.githubusercontent.com/u/583231?v=4",
            location = "Earth",
            email = null,
            htmlUrl = "https://github.com/octocat",
            createdAt = Instant.now().minusSeconds(TimeUnit.DAYS.toSeconds(7))
        )

        every { githubService.getGithubUserRepos(username) } returns listOf<GithubRepoResponse>(
            GithubRepoResponse(
                name = "best-repo",
                url = "https://github.com/octocat/best-repo"
            ),
            GithubRepoResponse(
                name = "better-repo",
                url = "https://github.com/octocat/better-repo"
            )
        )

        // when service is called
        val userResponse: UserResponse = userService.getUserInformation(username)

        // then verify both api calls are made
        verify(exactly = 1) { githubService.getGithubUserInformation(username) }
        verify(exactly = 1) { githubService.getGithubUserRepos(username) }

        // and validate some some fields
        userResponse.repos.size shouldBeEqualTo 2
        userResponse.geoLocation shouldBe "Earth"
    }
})

