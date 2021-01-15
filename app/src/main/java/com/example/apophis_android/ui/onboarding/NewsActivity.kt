package com.example.apophis_android.ui.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.CommentNews
import com.example.apophis_android.ui.onboarding.adapter.NewsAdapter
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        newsAdapter = NewsAdapter(this)
        rcv_news.adapter  = newsAdapter

        newsAdapter.addComment(CommentNews("R.id"))
    }
}