package com.example.jungleconsulting.db.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRepository(repositoryEntity: RepositoryEntity)

    @Query("SELECT * FROM RepositoryEntity WHERE login = :login")
    suspend fun getRepository(login: String): List<RepositoryEntity>
}