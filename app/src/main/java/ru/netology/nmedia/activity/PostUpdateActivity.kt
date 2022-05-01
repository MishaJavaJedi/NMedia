package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityPostUpdateBinding

class PostUpdateActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityPostUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.ok.setOnClickListener {
            val intent = Intent()
            val text = binding.edit.text
            if (text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }


    object ResultContract : ActivityResultContract<Unit, String?>() {

        override fun createIntent(context: Context, input: Unit) =
            Intent(context, PostUpdateActivity::class.java)

        override fun parseResult(resultCode: Int, intent: Intent?) =
            if (resultCode == Activity.RESULT_OK) {
                intent?.getStringExtra(Intent.EXTRA_TEXT)
            } else null
    }
}



