package com.example.readme.ui.community

data class CommunityItem(
    val imageResId: Int,
    val location: String,
    val title: String,
    val currentMembers: Int,
    val totalMembers: Int,
    val tags: List<String>
)
