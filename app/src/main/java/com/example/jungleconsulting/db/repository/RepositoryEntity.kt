package com.example.jungleconsulting.db.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepositoryEntity(
    val login: String,
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
