package ru.netology.nmedia.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var share: Int = 0,
    var videoUrl: String? = "",
    var likes: Int = 999,
    val likedByMe: Boolean = false
) : Parcelable

