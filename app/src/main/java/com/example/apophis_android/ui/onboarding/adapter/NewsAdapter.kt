package com.example.apophis_android.ui.onboarding.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.CommentNews

class NewsAdapter(private val context: Context) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var commentList: MutableList<CommentNews> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_comment_news,parent,false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.iv_news_user_image)
        private val time = itemView.findViewById<TextView>(R.id.tv_news_comment_time)
        private val name = itemView.findViewById<TextView>(R.id.tv_news_user_name)
        private val comment = itemView.findViewById<TextView>(R.id.tv_news_comment)


        fun bind(commentlist: CommentNews) {
            image.setImageResource(commentlist.image)
            time.text = commentlist.time
            name.text = commentlist.name
            comment.text = commentlist.comment
        }
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    fun addComment(commentItem: CommentNews) {
        commentList.add(commentItem)
        notifyItemInserted(commentList.size)
    }
}