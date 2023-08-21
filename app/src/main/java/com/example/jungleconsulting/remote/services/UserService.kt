package com.example.jungleconsulting.remote.services

import com.example.jungleconsulting.remote.dto.UserDataDto
import retrofit2.http.GET
import retrofit2.http.Url

interface UserService {

    @GET
    suspend fun getUsers(@Url url: String): List<UserDataDto>
}