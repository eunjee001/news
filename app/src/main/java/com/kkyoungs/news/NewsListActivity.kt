package com.kkyoungs.news

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkyoungs.news.databinding.ActivityNewsListBinding
import com.kkyoungs.news.listener.OnNewsClickListener
import com.kkyoungs.news.viewModel.NewsListViewModel
import com.kkyoungs.news.viewModel.NewsListViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject


@ExperimentalCoroutinesApi
class NewsListActivity : AppCompatActivity() {
    private val newsListViewModelFactory : NewsListViewModelFactory by inject()
    private val adapter by lazy { NewsListAdapter(this, listOf()) }
    private val viewModel : NewsListViewModel by viewModels {
        newsListViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityNewsListBinding>(this, R.layout.activity_news_list)
            .apply { lifecycleOwner = this@NewsListActivity }

        val newList = mutableListOf<String>()
        viewModel.newsList.observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })
        viewModel.progress.observe(this, Observer {
            binding.layoutRefresh.isRefreshing = it
        })

        binding.rvNewsList.layoutManager = LinearLayoutManager(this)
        binding.rvNewsList.adapter = adapter

        adapter.setOnNewsClickListener(object : OnNewsClickListener {
            override fun onNewsClick(position: Int) {
                val intent = Intent(applicationContext, NewsViewActivity::class.java)
                intent.putExtra("url", adapter.items[position].url)
                startActivity(intent)
            }
        })

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.updateNewsData()
        }
    }
    // 뒤로가기를 두 번 누르면 종료처리
    private var tempTime: Long = 0
    override fun onBackPressed() {
        super.onBackPressed()
        val currentTime = System.currentTimeMillis()
        if (currentTime-tempTime < 1000){
            finish()
        }
        tempTime = currentTime
        Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
    }
}