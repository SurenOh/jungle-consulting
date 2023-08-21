package com.example.jungleconsulting.datasource

import com.example.jungleconsulting.ImageLoader
import com.example.jungleconsulting.model.RepositoryModel
import com.example.jungleconsulting.model.UserDataModel
import com.example.jungleconsulting.remote.dto.UserDataDto
import com.example.jungleconsulting.repository.RemoteRepository

class RemoteDataStore(private val repository: RemoteRepository, private val imageLoader: ImageLoader) {
    private var currentUserOffset = 0
    private var currentRepositoryOffset = 0

    suspend fun getNextUsers(): List<UserDataModel> {
        val fullUsers = repository.getServiceUsers()
        val newOffset = currentUserOffset + OFFSET
        if (fullUsers.isEmpty()) return listOf()

        val newUsers = if (fullUsers.size > currentUserOffset && fullUsers.size >= newOffset) {
            fullUsers.subList(currentUserOffset, newOffset)
        } else if (fullUsers.size in currentUserOffset until newOffset) {
            fullUsers.subList(currentUserOffset, fullUsers.size)
        } else {
            listOf()
        }
        currentUserOffset += OFFSET
        return mapDtoListToModel(newUsers)
    }

    suspend fun getRepositories(login: String): List<RepositoryModel> {
        val fullRepositories = repository.getServiceRepositories(login)
        val newOffset = currentRepositoryOffset + OFFSET
        if (fullRepositories.isEmpty()) return listOf()

        val newRepositories = if (fullRepositories.size > currentRepositoryOffset && fullRepositories.size >= newOffset) {
            fullRepositories.subList(currentRepositoryOffset, newOffset)
        } else if (fullRepositories.size in currentRepositoryOffset until newOffset) {
            fullRepositories.subList(currentRepositoryOffset, fullRepositories.size)
        } else {
            listOf()
        }
        currentRepositoryOffset += OFFSET

        return newRepositories.map { RepositoryModel(it.name, login) }
    }

    private suspend fun mapDtoListToModel(dtoList: List<UserDataDto>) = dtoList.map {
        UserDataModel(it.login, it.image).apply {
            image = imageLoader.saveImageToCache(this) ?: ""
        }
    }

    companion object {
        const val OFFSET = 10
    }
}