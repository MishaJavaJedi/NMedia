package ru.netology.nmedia.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import ru.netology.nmedia.MainActivity
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel : ViewModel(), PostInteractionListener {
    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    val currentPost = MutableLiveData<Post?>(null)
    val navigateToPostContentScreenEvent = SingleLiveEvent<Unit>()
    val sharePostContent = SingleLiveEvent<String>()

    val navigateToPostUpdateScreenEvent = SingleLiveEvent<Unit>()
    val updatePost = MutableLiveData<Post>(null)


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
                published = "Today"
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
        navigateToPostUpdateScreenEvent.call()
    }

    fun onAddClicked() {
        navigateToPostContentScreenEvent.call()
    }
}