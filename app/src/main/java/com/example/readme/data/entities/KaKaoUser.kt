package com.example.readme.data.entities

import com.google.gson.annotations.SerializedName

data class KaKaoUser(
    @SerializedName("uniqueId") val id: String,
    @SerializedName("email") val email: String
)
