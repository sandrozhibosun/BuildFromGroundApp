package com.example.buildfromgroundapp.feature.news.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.buildfromgroundapp.R
import com.example.buildfromgroundapp.databinding.ItemNewsFeedBinding
import com.example.buildfromgroundapp.feature.news.data.model.domain.Article
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class NewsFeedAdapter @Inject constructor(
    @ActivityContext private val context: Context
) :
    RecyclerView.Adapter<NewsFeedAdapter.NewsFeedItemViewHolder>() {

    private var mNewsFeedList = emptyList<Article>()
    private lateinit var itemNewsFeedBinding: ItemNewsFeedBinding
    private var onItemClick: ((Article) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setNewsFeedList(
        mNewsFeedList: List<Article>,
    ) {
        this.mNewsFeedList = mNewsFeedList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setOnItemClick(onItemClick: ((Article) -> Unit)? = null) {
        this.onItemClick = onItemClick
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedItemViewHolder {
        itemNewsFeedBinding =
            ItemNewsFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsFeedItemViewHolder(itemNewsFeedBinding, context)
    }

    override fun onBindViewHolder(holder: NewsFeedItemViewHolder, position: Int) {
        holder.bind(mNewsFeedList[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return mNewsFeedList.size
    }

    inner class NewsFeedItemViewHolder(
        private val binding: ItemNewsFeedBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article, onItemClick: ((Article) -> Unit)? = null) {
            with(binding) {
                name.text = article.title
                description.text = article.description
                Glide.with(context).load(article.urlToImage).apply(
                    RequestOptions()
                        .placeholder(R.drawable.baseline_edit_24)
                        .error(R.drawable.baseline_edit_24)
                ).into(image)
                binding.root.setOnClickListener { onItemClick?.invoke(article) }
            }
        }
    }
}

