package ru.netology.nmedia.data.impl


import androidx.lifecycle.map
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.db.PostDao
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
        dao.share(postId)
    }


    override fun delete(postId: Long) {
        dao.removeById(postId)
    }

    companion object {
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
}

