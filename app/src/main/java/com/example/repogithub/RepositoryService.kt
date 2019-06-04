package com.example.repogithub

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RepositoryService {

    @GET("/repos/{repo}")
    fun getRepository(@Path("repo", encoded = true) repo : String) : Call<Repository>

}