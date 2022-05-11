package ru.netology.nmedia.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.databinding.PostShowFragmentBinding


class PostShowFragment : Fragment() {

    private val args by navArgs<PostUpdateFragmentArgs>() //TODO

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostShowFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        val postInfo = "This is initialized post with it actual content " + args.initialContent
        binding.tempText.text =  postInfo

//        binding.ok.setOnClickListener {
//            onOkButtonClicked(binding)
//        }
    }.root

    private fun onOkButtonClicked(binding: PostShowFragmentBinding) {
//        val text = binding.edit.text
//        if (!text.isNullOrBlank()) {
//            val resultBundle = Bundle(1)
//            resultBundle.putString(RESULT_KEY, text.toString())
//            setFragmentResult(REQUEST_KEY, resultBundle)
//        }
        findNavController().popBackStack()
    }

    companion object {
        const val REQUEST_KEY = "requestShowKey"
        const val RESULT_KEY = "showedContent"
    }
}



