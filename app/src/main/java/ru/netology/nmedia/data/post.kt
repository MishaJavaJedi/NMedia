package ru.netology.nmedia.data

import kotlinx.serialization.Serializable


@Serializable
data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var share: Int = 0,
    var videoUrl: String? = "",
    var likes: Int = 0,
    val likedByMe: Boolean = false
)

