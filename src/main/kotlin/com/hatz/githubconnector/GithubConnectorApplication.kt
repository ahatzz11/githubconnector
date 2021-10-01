package com.hatz.githubconnector

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GithubConnectorApplication

@SuppressWarnings("SpreadOperator")
fun main(args: Array<String>) {
	runApplication<GithubConnectorApplication>(*args)
}
