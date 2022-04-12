package ru.netology.nmedia.data

data class Post(
    val id:Long,
    val author:String,
    val content:String,
    val published:String,
    var share:Int = 0,
    var likes:Int = 0,
    var likedByMe:Boolean = false
)
