package com.example.jungleconsulting.db.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDataEntity(
    @PrimaryKey
    val login: String,
    val image: String
)
