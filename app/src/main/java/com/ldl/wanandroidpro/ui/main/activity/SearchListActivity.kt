package com.ldl.wanandroidpro.ui.main.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.base.activity.BaseActivity
import com.ldl.wanandroidpro.databinding.ActivitySearchListBinding
import com.ldl.wanandroidpro.ui.main.adapter.ArticleAdapter
import com.ldl.wanandroidpro.viewmodel.main.SearchListViewModel
import kotlinx.android.synthetic.main.activity_search_list.*
import kotlinx.android.synthetic.main.common_toolbar.*

/**
 * 作者：LDL 创建时间：2020/6/2
 * 类说明：
 */
class SearchListActivity : BaseActivity<ActivitySearchListBinding>() {

    companion object {
        private const val KEY = "key"

        fun start(key: String) {
            val bundle = Bundle()
            bundle.putString(KEY, key)
            ActivityUtils.startActivity(bundle, SearchListActivity::class.java)
        }
    }

    private val viewModel by viewModels<SearchListViewModel>()
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

    override fun getLayoutId(): Int = R.layout.activity_search_list

    override fun onViewCreated() {
        super.onViewCreated()
        binding.viewModel = viewModel
        initRecyclerView()
        initLoadMore()
    }

    override fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        intent.getStringExtra(KEY)?.also {
            supportActionBar?.title = it
            viewModel.k = it
        }
    }

    override fun initData() {
        viewModel.onRefresh()
        observe()
    }

    private fun initRecyclerView() {
        rvSearchList.layoutManager = LinearLayoutManager(this)
        mAdapter = ArticleAdapter(viewModel.feedArticleDataList)
        rvSearchList.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            val feedArticleData = viewModel.feedArticleDataList[position]
            WebViewActivity.start(feedArticleData.title, feedArticleData.link)
        }
    }

    private fun initLoadMore() {
        mAdapter.loadMoreModule?.apply {
            isEnableLoadMoreIfNotFullPage = false
            setOnLoadMoreListener {
                viewModel.onLoadMore()
            }
        }
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