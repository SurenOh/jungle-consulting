package com.example.jungleconsulting.remote.services

import com.example.jungleconsulting.remote.dto.RepositoryDto
import retrofit2.http.GET
import retrofit2.http.Url

interface RepositoryService {

    @GET
    suspend fun getRepositories(@Url url: String): List<RepositoryDto>
}