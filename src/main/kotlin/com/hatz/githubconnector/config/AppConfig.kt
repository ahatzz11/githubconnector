package com.hatz.githubconnector.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.hatz.githubconnector.retrofit.GithubApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
class AppConfig {

    @Bean
    fun objectMapper(): ObjectMapper {

        return ObjectMapper()
            .registerModule(JavaTimeModule())
            .registerModule(KotlinModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    @Bean
    fun githubApi(): GithubApi {

        val httpLoggingInterceptor = HttpLoggingInterceptor()

        val interceptor: HttpLoggingInterceptor =
            httpLoggingInterceptor.apply { httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS };

        val client: OkHttpClient = OkHttpClient.Builder()
            // disabled logger
             .addInterceptor(interceptor)
            .build();


        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper()))
            .build()
            .create(GithubApi::class.java)
    }
}
