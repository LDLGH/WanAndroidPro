package com.ldl.wanandroidpro.ui.main.activity

import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.base.activity.BaseActivity
import com.ldl.wanandroidpro.databinding.ActivityArticleBinding
import com.ldl.wanandroidpro.ui.main.adapter.ArticleAdapter
import com.ldl.wanandroidpro.viewmodel.main.ArticleViewModel
import kotlinx.android.synthetic.main.activity_article.*
import kotlinx.android.synthetic.main.common_toolbar.*

class ArticleActivity : BaseActivity<ActivityArticleBinding>() {

    private val viewModel by viewModels<ArticleViewModel>()
    private lateinit var mAdapter: ArticleAdapter

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                ActivityUtils.finishActivity(this, true)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_article

    override fun onViewCreated() {
        super.onViewCreated()
        binding.viewModel = viewModel

        initSwipeRefreshLayout()
        initRecyclerView()
        initLoadMore()
        observe()
    }

    override fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.menu_article)
    }

    private fun initRecyclerView() {
        rvArticle.layoutManager = LinearLayoutManager(this)
        mAdapter = ArticleAdapter(viewModel.feedArticleDataList)
        rvArticle.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            val feedArticleData = viewModel.feedArticleDataList[position]
            WebViewActivity.start(feedArticleData.title, feedArticleData.link)
        }
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.measure(0, 0)
    }

    private fun initLoadMore() {
        mAdapter.loadMoreModule.apply {
            isEnableLoadMoreIfNotFullPage = false
            setOnLoadMoreListener {
                viewModel.onLoadMore()
            }
        }
    }

    override fun initData() {
        viewModel.onRefresh()
    }

    private fun observe() {
        viewModel.isRefresh.observe(this, Observer {
            if (it) {
                mAdapter.loadMoreModule.isEnableLoadMore = true
                mAdapter.setList(viewModel.feedArticleDataList)
            } else {
                mAdapter.loadMoreModule.loadMoreComplete()
            }
        })
    }

}
