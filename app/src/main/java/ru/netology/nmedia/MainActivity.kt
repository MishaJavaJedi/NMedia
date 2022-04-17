package ru.netology.nmedia

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel = PostViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            binding.render(post)
        }

        binding.repostButton.setOnClickListener {
            viewModel.share()
        }

        binding.likeButton.setOnClickListener {
            viewModel.onLikeClicked()
        }
    }

    private fun toStringConverter(count: Int): String {
        return InMemoryPostRepository().converter(count)
    }

    private fun ActivityMainBinding.render(post: Post) {
        authorName.text = post.author
        postDate.text = post.published
        text.text = post.content
        repostCount.text = toStringConverter(post.share)
        likesCount.text = toStringConverter(post.likes)

        likeButton.setImageResource(getLikeIconResId(post.likedByMe))
    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp
}






