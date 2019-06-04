package com.example.repogithub

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitHubInitializer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun repositoryService() = retrofit.create(RepositoryService::class.java)
}
