package com.example.retrofitproject

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login") val username: String,
    @SerializedName("avatar_url") val avatar: String?,
    @SerializedName("gravatar_url") val gravatar: String?,
    val name: String,
    @SerializedName("html_url") val url: String,
     val blog: String
)