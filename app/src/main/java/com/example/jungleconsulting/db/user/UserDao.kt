package com.example.jungleconsulting.db.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(userDataEntity: UserDataEntity)

    @Query("SELECT * FROM UserDataEntity")
    suspend fun getUsers(): List<UserDataEntity>
}