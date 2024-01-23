package com.kkyoungs.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kkyoungs.news.data.News
import com.kkyoungs.news.databinding.ItemNewsBinding
import com.kkyoungs.news.listener.OnNewsClickListener

class NewsListAdapter(private val mContext : Context, var items:List<News>) : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {
    private lateinit var mOnNewsClickListener: OnNewsClickListener

    fun setOnNewsClickListener(listener: OnNewsClickListener) {
        this.mOnNewsClickListener = listener
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsListAdapter.NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemNewsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.item_news,
            parent,
            false
        )

        // 뉴스 아이템 높이 설정
        val itemHeight = 0.5 // 1 = 100%
        val layoutParams = binding.card.layoutParams
        layoutParams.height = (parent.height * itemHeight).toInt()
        binding.card.layoutParams = layoutParams

        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsListAdapter.NewsViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.setOnClickListener {
            mOnNewsClickListener.onNewsClick(position)
        }
        holder.mBinding.newsTitle.text = item.title
        Glide.with(mContext)
            .load(item.image)
            .into(holder.mBinding.newsImage)
        holder.mBinding.newsDescription.text = item.description
    }

    override fun getItemCount(): Int {
        return items.size
    }
    class NewsViewHolder(binding: ItemNewsBinding):RecyclerView.ViewHolder(binding.getRoot()){
        val mBinding: ItemNewsBinding

        init {
            mBinding = binding
        }
    }
}