package ru.netology.nmedia

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.data.impl.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val adapter = PostsAdapter(viewModel::onLikeClicked,viewModel::share)
        binding.postRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }



//        authorName.text = post.author
//        postDate.text = post.published
//        text.text = post.content
//        repostCount.text = toStringConverter(post.share)
//        likesCount.text = toStringConverter(post.likes)
//        likeButton.setImageResource(getLikeIconResId(post.likedByMe))
    }









