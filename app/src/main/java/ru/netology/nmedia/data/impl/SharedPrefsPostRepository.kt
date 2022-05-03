package ru.netology.nmedia.data.impl

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.repository.PostRepository
import java.io.Serializable
import kotlin.properties.Delegates

class SharedPrefsPostRepository(
    application: Application
) : PostRepository {

    private val prefs = application.getSharedPreferences("repo", Context.MODE_PRIVATE)

    private var nextId:Long by Delegates.observable(prefs.getLong(NEXT_ID_PREFS_KEY, 0L)){
        _,_, newValue -> prefs.edit{putLong(NEXT_ID_PREFS_KEY,newValue)}
    }

    private var posts
        get() = checkNotNull(data.value) { "Data value should not be null" }
        set(value) {
            prefs.edit() {
                val serializedPosts = Json.encodeToString(value)
                putString(POSTS_PREFS_KEY, serializedPosts)
            }
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val serializedPosts = prefs.getString(POSTS_PREFS_KEY, null)
        val posts = if (serializedPosts != null) {
            Json.decodeFromString<List<Post>>(serializedPosts)
        } else emptyList()
        data = MutableLiveData(posts)
    }

    override fun like(postId: Long) {
        posts = posts.map {
            if (it.id == postId) it.copy(likedByMe = !it.likedByMe)
            else it
        }.map { if (it.id == postId && it.likedByMe) it.copy(likes = it.likes + 1) else it }
            .map { if (it.id == postId && !it.likedByMe) it.copy(likes = it.likes - 1) else it }
    }

    override fun share(postId: Long) {
        posts = posts.map { if (it.id == postId) it.copy(share = it.share + 1) else it }
    }

    override fun delete(postId: Long) {
        posts = posts.filterNot { it.id == postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post: Post) {
        posts = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }

    private fun update(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private companion object {
        //const val GENERATED_POSTS_AMOUNT = 1
        const val POSTS_PREFS_KEY = "posts"
        const val NEXT_ID_PREFS_KEY = "nextId"
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
