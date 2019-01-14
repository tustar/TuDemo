package com.tustar.jetpack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tustar.jetpack.reddit.repository.RedditPostRepository
import com.tustar.jetpack.reddit.ui.RedditActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        withDatabase.setOnClickListener {
            show(RedditPostRepository.Type.DB)
        }
        networkOnly.setOnClickListener {
            show(RedditPostRepository.Type.IN_MEMORY_BY_ITEM)
        }
        networkOnlyWithPageKeys.setOnClickListener {
            show(RedditPostRepository.Type.IN_MEMORY_BY_PAGE)
        }
    }

    private fun show(type: RedditPostRepository.Type) {
        val intent = RedditActivity.intentFor(this, type)
        startActivity(intent)
    }
}
