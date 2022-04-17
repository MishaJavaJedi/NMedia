package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.repository.PostRepository

class InMemoryPostRepository : PostRepository {
    override val data = MutableLiveData(
        Post(
            id = 0L,
            author = "Author name",
            content = "Lorem Ipsum",
            published = "10.04.2022",
            share = 1299,
            likes = 1999
        )
    )

    override fun like() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        val likedPost = currentPost.copy(
            likes = currentPost.likes,
            likedByMe = !currentPost.likedByMe
        )
        if (likedPost.likedByMe) likedPost.likes++ else likedPost.likes--

        data.value = likedPost
    }

    override fun share() {

        val post = checkNotNull(data.value) {
            "Data value should not be null"
        }
        val sharedPost = post.copy(
            share = post.share + 1
        )
        data.value = sharedPost
    }

    fun converter(count: Int): String {

        val countToString = count.toString()
        val firstNumber: String
        val secondNumber: String

        if (count < 1000) {
            return countToString
        }
        if (count % 1000 == 0 && count < 1_000_000) {
            firstNumber = countToString.substring(0, 1)
            return firstNumber + "K"
        }

        if (count >= 1_000_000) {
            firstNumber = countToString.substring(0, 1)
            secondNumber = countToString.substring(1, 2)
            return firstNumber + secondNumber + "M"
        }
        if (count >= 10_000) {
            firstNumber = countToString.substring(0, 1)
            secondNumber = countToString.substring(1, 2)
            return firstNumber + secondNumber + "K"
        }
        return if (count >= 1000) {
            firstNumber = countToString.substring(0, 1)
            secondNumber = countToString.substring(1, 2)
            return firstNumber + "," + secondNumber + "K"
        } else ""
    }
}
