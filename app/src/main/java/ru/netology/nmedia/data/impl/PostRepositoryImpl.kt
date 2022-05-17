package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.PostEntity
import ru.netology.nmedia.db.toEntity
import ru.netology.nmedia.db.toModel
import ru.netology.nmedia.repository.PostRepository


class PostRepositoryImpl(
    private val dao: PostDao
) : PostRepository {


override val data = dao.getAll().map { entities ->
    entities.map { it.toModel() }
}

    override fun save(post: Post) {
            if (post.id == PostRepository.NEW_POST_ID) dao.insert(post.toEntity())
            else dao.updateContentById(post.id, post.content)
    }


    override fun like(postId: Long) {
        dao.likeById(postId)
    }

    override fun share(postId: Long) {

    }


    override fun delete(postId: Long) {
        dao.removeById(postId)
    }
}