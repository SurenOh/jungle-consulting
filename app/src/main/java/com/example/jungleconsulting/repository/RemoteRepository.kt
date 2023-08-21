package com.example.jungleconsulting.repository

import com.example.jungleconsulting.remote.services.RepositoryService
import com.example.jungleconsulting.remote.services.UserService

class RemoteRepository(private val user: UserService, private val repositories: RepositoryService, ) {
    suspend fun getServiceUsers() = user.getUsers("https://api.github.com/users")
    suspend fun getServiceRepositories(login: String) = repositories.getRepositories("https://api.github.com/users/$login/repos")
}