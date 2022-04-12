package ru.netology.nmedia

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 0L,
            author = "Author name",
            content = "Lorem Ipsum",
            published = "10.04.2022",
            likes = 1999
        )

        binding.render(post)

        binding.likeButton.setOnClickListener {
            post.likedByMe = !post.likedByMe
            if (post.likedByMe) post.likes++ else post.likes--
            binding.likeButton.setImageResource(getLikeIconResId(post.likedByMe))
            binding.likesCount.text = likesConverter(post.likes) //
        }

        binding.repostButton.setOnClickListener {
            binding.repostCount.text = (++post.share).toString()
        }
    }

    private fun likesConverter(like: Int): String {

        val likesToString = like.toString()
        val firstNumber: String
        val secondNumber: String

        if (like < 1000) {
            return likesToString
        }
        if(like % 1000 == 0){
            firstNumber = likesToString.substring(0, 1)
            return firstNumber + "K"
        }

        if (like >= 1_000_000) {
            firstNumber = likesToString.substring(0, 1)
            secondNumber = likesToString.substring(1, 2)
            return firstNumber + secondNumber + "M"
        }
        if (like >= 10_000) {
            firstNumber = likesToString.substring(0, 1)
            secondNumber = likesToString.substring(1, 2)
            return firstNumber + secondNumber + "K"
        }
        return if (like >= 1000) {
            firstNumber = likesToString.substring(0, 1)
            secondNumber = likesToString.substring(1, 2)
            return firstNumber + "," + secondNumber + "K"
        } else ""//
    }

    private fun ActivityMainBinding.render(post: Post) {
        authorName.text = post.author
        postDate.text = post.published
        text.text = post.content
        likeButton.setImageResource(getLikeIconResId(post.likedByMe))
    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp
}






