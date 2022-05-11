package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.impl.FilePostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.data.impl.SharedPrefsPostRepository
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel(application: Application) : AndroidViewModel(application),
    PostInteractionListener {
    private val repository: PostRepository = FilePostRepository(application)

    val data by repository::data

    private val currentPost = MutableLiveData<Post?>(null)
    val navigateToPostContentScreenEvent = SingleLiveEvent<String>()
    val sharePostContent = SingleLiveEvent<String>()

    val navigateToPostUpdateScreenEvent = SingleLiveEvent<String?>()
    val updatePost = MutableLiveData<Post>(null)

    val playVideoScreenEvent = SingleLiveEvent<String>()
    val videoUrl = MutableLiveData<String>()

    val navigateToShowPost = SingleLiveEvent<String?>()

    override fun onPlayClicked(post: Post) {
        videoUrl.value = post.videoUrl.toString()
        playVideoScreenEvent.call()
    }

    override fun onShowPostClicked(post: Post) {
        navigateToShowPost.value = post.content
    }

    fun onActivitySaveClicked(post: Post, content: String) {
        val updatedPost = post.copy(content = content)
        repository.save(updatedPost)
    }

    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return

        val post = currentPost.value?.copy(content = content)
            ?: Post(
                id = PostRepository.NEW_POST_ID,
                author = "Me",
                content = content,
                published = "Today",
                videoUrl = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
            )
        repository.save(post)
        currentPost.value = null
    }

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onShareClicked(post: Post) {
        sharePostContent.value = post.content
        repository.share(post.id)
    }

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)

    override fun onEditClicked(post: Post) {
        updatePost.value = post
        navigateToPostUpdateScreenEvent.value = post.content
    }

    fun onAddClicked() {
        navigateToPostContentScreenEvent.call()
    }
}