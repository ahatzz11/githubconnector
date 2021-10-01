package com.hatz.githubconnector.service

import com.hatz.githubconnector.dto.github.GithubRepoResponse
import com.hatz.githubconnector.dto.github.GithubUserResponse
import com.hatz.githubconnector.exception.UserNotFoundException
import com.hatz.githubconnector.retrofit.GithubApi
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContain
import retrofit2.Response
import java.time.Instant
import java.util.concurrent.TimeUnit

class GithubServiceSpec : FunSpec({

    val githubApi = mockk<GithubApi>()
    val githubService = GithubService(githubApi)

    afterTest { clearAllMocks() }

    test("happy - get github user information") {
        // given a username
        val username: String = "octocat"

        every { githubApi.getGithubUser(username).execute() } returns Response.success(
            GithubUserResponse(
                login = username,
                name = "octocat",
                avatarUrl = "https://avatars.githubusercontent.com/u/583231?v=4",
                location = "Earth",
                email = null,
                htmlUrl = "https://github.com/octocat",
                createdAt = Instant.now().minusSeconds(TimeUnit.DAYS.toSeconds(7))
            )
        )

        // when
        val githubUserResponse: GithubUserResponse = githubService.getGithubUserInformation(username)

        // then verify api call
        verify(exactly = 1) { githubApi.getGithubUser(username) }

        // validate some data
        githubUserResponse.login shouldBe username
        githubUserResponse.location shouldBe "Earth"
    }

    test("sad - github user not found, exception thrown") {
        // given a username
        val username: String = "octooctocat"

        every { githubApi.getGithubUser(username).execute() } returns
            Response.error(404, "{\"message\": \"Not Found\"}".toResponseBody())

        // when api call is made
        val exception = shouldThrow<UserNotFoundException> {
            githubService.getGithubUserInformation(username)
        }

        exception.message?.shouldContain("not found")
    }

    test("happy - get github user repos ") {
        // given a username
        val username: String = "octocat"

        every { githubApi.getRepos(username).execute() } returns Response.success(
            listOf(
                GithubRepoResponse(
                    name = "best-repo",
                    url = "https://github.com/octocat/best-repo"
                ),
                GithubRepoResponse(
                    name = "better-repo",
                    url = "https://github.com/octocat/better-repo"
                )
            )
        )

        // when
        val githubRepoResponse: List<GithubRepoResponse> = githubService.getGithubUserRepos(username)

        // then verify api call
        verify(exactly = 1) { githubApi.getRepos(username) }

        // validate some data
        githubRepoResponse.size shouldBeEqualTo 2
        githubRepoResponse[0].name shouldBe "best-repo"
    }
})
