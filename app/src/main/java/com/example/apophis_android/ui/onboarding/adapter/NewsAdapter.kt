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

class NewsAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val commentList: MutableList<CommentNews> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_comment_news -> {
                val view = layoutInflater.inflate(R.layout.item_comment_news, parent, false)
                NewsViewHolder(view)
            }
            else ->
                throw IllegalArgumentException("ViewType [$viewType] is unexpected")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsViewHolder) {
            holder.bind(commentList)
        }
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.iv_news_user_image)
        private val time = itemView.findViewById<TextView>(R.id.tv_news_comment_time)
        private val name = itemView.findViewById<TextView>(R.id.tv_news_user_name)
        private val comment = itemView.findViewById<TextView>(R.id.tv_news_comment)

        fun bind(commentlist: MutableList<CommentNews>) {
            /*commentlist.forEach {
                image.setImageResource()
                time.text = it.time
                name.text = it.name
                comment.text = it.comment
            }*/
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