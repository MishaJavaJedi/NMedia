package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

        binding.closeButton.setOnClickListener {
            binding.cancelEditGroup.visibility = View.GONE
            binding.content.text.clear()
        }

        binding.save.setOnClickListener {
            with(binding.content) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)

                binding.cancelEditGroup.visibility = View.GONE
                clearFocus()
                hideKeyboard()
            }
        }


        viewModel.currentPost.observe(this) { currentPost ->
            binding.content.setText(currentPost?.content)
            if (binding.content.text.toString() != "") {
                binding.cancelEditText.text = currentPost?.content.toString()
                binding.cancelEditGroup.visibility = View.VISIBLE
            }
        }
    }
}









