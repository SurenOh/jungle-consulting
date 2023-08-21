package com.example.jungleconsulting.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jungleconsulting.db.repository.RepositoryDao
import com.example.jungleconsulting.db.repository.RepositoryEntity
import com.example.jungleconsulting.db.user.UserDao
import com.example.jungleconsulting.db.user.UserDataEntity

@Database(entities = [UserDataEntity::class, RepositoryEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getRepositories(): RepositoryDao
}