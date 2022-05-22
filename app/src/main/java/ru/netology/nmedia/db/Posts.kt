package ru.netology.nmedia.db

import ru.netology.nmedia.data.Post

internal fun PostEntity.toModel() = Post(
    id = id,
    author = author,
    content = content,
    share = share,
    videoUrl = videoUrl,
    published = published,
    likes = likes,
    likedByMe = likedByMe

)

internal fun Post.toEntity() = PostEntity(
    id = id,
    author = author,
    share = share,
    videoUrl = videoUrl.toString(),
    content = content,
    published = published,
    likes = likes,
    likedByMe = likedByMe
)