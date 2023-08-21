package com.example.jungleconsulting.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jungleconsulting.datasource.LocalDataStore
import com.example.jungleconsulting.datasource.RemoteDataStore
import com.example.jungleconsulting.model.RepositoryModel
import com.example.jungleconsulting.model.UserDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppViewModel(
    private val localDataStore: LocalDataStore,
    private val remoteDataStore: RemoteDataStore
) : ViewModel() {
    val users = mutableStateListOf<UserDataModel>()
    val repositories = mutableStateListOf<RepositoryModel>()

    fun checkConnection(connection: Boolean) {
        if (connection) getFirstUsersFromService() else getUsersFromDb()
    }

    private fun getUsersFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val resultList = getLocalUsers()
            updateUsers(resultList)
        }
    }

    private fun getRepositoriesFromDb(login: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val repositoriesNew = getLocalRepositories(login)
            updateRepositories(repositoriesNew)
        }
    }

    fun getFirstRepositoriesFromService(login: String, connection: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!connection) {
                getRepositoriesFromDb(login)
            } else {
                getRepositoriesFromDb(login)
                val repositoriesNew = getServiceRepositories(login)
                repositories.clear()
                updateRepositories(repositoriesNew)
                saveRepositoriesToDb(repositoriesNew)
            }
        }
    }

    private fun getFirstUsersFromService() {
        viewModelScope.launch(Dispatchers.IO) {
            getUsersFromDb()
            val models = getServiceUsers()
            users.clear()
            updateUsers(models)
            saveUsersToDb(models)
        }
    }

    fun getUsersFromService() {
        viewModelScope.launch(Dispatchers.IO) {
            val models = getServiceUsers()
            updateUsers(models)
            saveUsersToDb(models)
        }
    }

    fun getRepositoriesFromService(login: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val repositoriesNew = getServiceRepositories(login)
            updateRepositories(repositoriesNew)
            saveRepositoriesToDb(repositoriesNew)
        }
    }

    private fun saveUsersToDb(models: List<UserDataModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            localDataStore.saveUsers(models)
        }
    }

    private fun saveRepositoriesToDb(repositoriesNew: List<RepositoryModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            localDataStore.saveRepositories(repositoriesNew)
        }
    }

    private suspend fun getLocalUsers() = localDataStore.getUsers()
    private suspend fun getServiceUsers() = remoteDataStore.getNextUsers()
    private suspend fun getServiceRepositories(login: String) = remoteDataStore.getRepositories(login)
    private suspend fun getLocalRepositories(login: String) = localDataStore.getRepositories(login)
    private fun updateUsers(list: List<UserDataModel>) {
        users.addAll(list)
    }
    private fun updateRepositories(repositoriesNew: List<RepositoryModel>) {
        repositories.addAll(repositoriesNew)
    }
}