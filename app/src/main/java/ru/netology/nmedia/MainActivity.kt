package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.activity.PostContentActivity
import ru.netology.nmedia.activity.PostUpdateActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
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

        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }

        viewModel.sharePostContent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }


        val postContentActivityLauncher =
            registerForActivityResult(PostContentActivity.ResultContract) { postContent ->
                postContent ?: return@registerForActivityResult
                viewModel.onSaveButtonClicked(postContent)
            }

        viewModel.navigateToPostContentScreenEvent.observe(this) {
            postContentActivityLauncher.launch()
        }


        //Play


        viewModel.playVideoScreenEvent.observe(this) { videoUrl ->
            val url = viewModel.videoUrl.value
            val urlParce = Uri.parse("https://www.youtube.com/watch?v=WhWc3b3KhnY")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)

//                .apply {
//                action = Intent.ACTION_VIEW
//                putExtra(Intent.EXTRA_TEXT, url)
//                intent.setDataAndType(urlParce, "video/mp4")


//                (Intent.ACTION_VIEW,
//                Uri.parse("http://www.youtube.com/watch?v=" + key))
            //type = "video/mp4"
        }
        //startActivity(intent)
//            val shareIntent =
//                Intent.createChooser(intent, getString(R.string.chooser_share_post))
//            startActivity(shareIntent)

//            val intent = Intent()
//            intent.action = Intent.ACTION_VIEW
//            intent.setDataAndType(Uri.parse("http://www.yourvideo.mp4"), "video/mp4")


        //Update
        val postUpdateContentActivityLauncher =
            registerForActivityResult(PostUpdateActivity.ResultContract) { postContent ->
                postContent ?: return@registerForActivityResult
                val updatedPost = viewModel.updatePost.value

                if (updatedPost != null) {
                    viewModel.onActivitySaveClicked(updatedPost, postContent)
                }
            }

        viewModel.navigateToPostUpdateScreenEvent.observe(this) {
            postUpdateContentActivityLauncher.launch()
        }
    }
}










