package ru.netology.nmedia

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.activity.PostContentActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel)


        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

//        binding.closeButton.setOnClickListener {
//            binding.cancelEditGroup.visibility = View.GONE
//            binding.content.text.clear()
//            viewModel.clearCurrentPost()
//        }

        binding.fab.setOnClickListener {
           viewModel.onAddClicked()
            }



//        viewModel.currentPost.observe(this) { currentPost ->
//            binding.content.setText(currentPost?.content)
//            if (binding.content.text.toString() != "") {
//                binding.cancelEditText.text = currentPost?.content.toString()
//                binding.cancelEditGroup.visibility = View.VISIBLE
//            }
//        }

        viewModel.sharePostContent.observe(this){postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,postContent)
                type = "text/plain"
            }
            val shareIntent =
                Intent.createChooser(intent,getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        val postContentActivityLauncher =
        registerForActivityResult(PostContentActivity.ResultContract){
            postContent ->
            val temp = postContent
            postContent?:return@registerForActivityResult
            viewModel.onSaveButtonClicked(postContent)
        }

        viewModel.navigateToPostContentScreenEvent.observe(this){
            postContentActivityLauncher.launch()
        }
    }
}









