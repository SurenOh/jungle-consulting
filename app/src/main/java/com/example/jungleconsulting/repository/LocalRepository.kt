package com.example.jungleconsulting.repository

import com.example.jungleconsulting.db.AppDb
import com.example.jungleconsulting.db.repository.RepositoryEntity
import com.example.jungleconsulting.db.user.UserDataEntity

class LocalRepository(db: AppDb) {
    private val userDao = db.getUserDao()
    private val repositoryDao = db.getRepositories()

    suspend fun getUsers() = userDao.getUsers()
    suspend fun saveUsers(list: List<UserDataEntity>) {
        list.forEach { userDao.saveUser(it) }
    }

    suspend fun getRepositories(login: String) = repositoryDao.getRepository(login)
    suspend fun saveRepositories(list: List<RepositoryEntity>) {
        list.forEach { repositoryDao.saveRepository(it) }
    }
}