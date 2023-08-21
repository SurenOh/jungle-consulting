package com.example.jungleconsulting.datasource

import com.example.jungleconsulting.db.repository.RepositoryEntity
import com.example.jungleconsulting.mapper.EntityMapper
import com.example.jungleconsulting.model.RepositoryModel
import com.example.jungleconsulting.model.UserDataModel
import com.example.jungleconsulting.repository.LocalRepository

class LocalDataStore(private val repository: LocalRepository, private val mapper: EntityMapper) {
    suspend fun getUsers() = mapper.mapListEntityToModels(repository.getUsers())
    suspend fun saveUsers(list: List<UserDataModel>) = repository.saveUsers(mapper.mapListModelsToEntity(list))

    suspend fun getRepositories(login: String) = repository.getRepositories(login).map { RepositoryModel(it.name, it.login) }
    suspend fun saveRepositories(list: List<RepositoryModel>) = repository.saveRepositories(list.map { RepositoryEntity(it.name, it.login) })
}