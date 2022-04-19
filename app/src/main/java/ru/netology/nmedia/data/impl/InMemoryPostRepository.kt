package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.repository.PostRepository

class InMemoryPostRepository : PostRepository {

    private val posts get() = checkNotNull(data.value) { "Data value should not be null" }

    override val data = MutableLiveData(
        List(1000) { index ->
            Post(
                id = index + 1L,
                author = "Netology",
                content = "Lorem Ipsum $index",
                published = "10.04.2022",
                share = 997,
                likes = 999
            )
        }
    )

    override fun like(postId: Long) {
        data.value = posts.map {
            if (it.id == postId) it.copy(likedByMe = !it.likedByMe)
            else it
        }.map { if (it.id == postId && it.likedByMe) it.copy(likes = it.likes + 1) else it }
            .map { if (it.id == postId && !it.likedByMe) it.copy(likes = it.likes - 1) else it }
    }

    override fun share(postId: Long) {
        data.value = posts.map { if (it.id == postId) it.copy(share = it.share + 1) else it }
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

