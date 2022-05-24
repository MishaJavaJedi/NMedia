package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.PostShowFragmentBinding


class PostShowFragment : Fragment() {

    private val args by navArgs<PostShowFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostShowFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        val tempPost: Post = args.initialContent!!

        render(binding, tempPost)

        Toast.makeText(activity, "You are on Post's Fragment!", Toast.LENGTH_LONG).show();
    }.root

    private fun render(binding: PostShowFragmentBinding, tempPost: Post) {
        binding.run {
            tempAuthorName.text = tempPost.author
            tempPostDate.text = tempPost.published
            tempText.text = tempPost.content
            tempLikeButton.isChecked = tempPost.likedByMe
            tempURL.text = tempPost.videoUrl
            tempLikeButton.text = tempPost.likes.toString()
            tempRepostButton.text = tempPost.share.toString()

            tempLikeButton.isClickable = false
            tempRepostButton.isClickable = false
        }
    }

    companion object {
        const val REQUEST_KEY = "requestShowKey"
        const val RESULT_KEY = "showedContent"
    }
}



