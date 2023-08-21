package com.example.jungleconsulting.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDataDto(
    val login: String,
    @SerializedName("avatar_url")
    val image: String
)