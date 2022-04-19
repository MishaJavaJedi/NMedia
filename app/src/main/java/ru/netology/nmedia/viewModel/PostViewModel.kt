package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.repository.PostRepository

class PostViewModel : ViewModel() {
    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    fun onLikeClicked() = repository.like()
    fun share() = repository.share()

}