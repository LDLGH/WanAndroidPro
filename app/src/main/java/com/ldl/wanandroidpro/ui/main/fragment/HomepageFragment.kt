package com.ldl.wanandroidpro.ui.main.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ConvertUtils
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.base.fragment.BaseRootFragment
import com.ldl.wanandroidpro.core.model.main.BannerData
import com.ldl.wanandroidpro.databinding.FragmentHomepageBinding
import com.ldl.wanandroidpro.ui.main.activity.WebViewActivity
import com.ldl.wanandroidpro.ui.main.adapter.BannerViewAdapter
import com.ldl.wanandroidpro.ui.main.adapter.BannerViewHolder
import com.ldl.wanandroidpro.ui.main.adapter.HomepageAdapter
import com.ldl.wanandroidpro.ui.main.adapter.MenuAdapter
import com.ldl.wanandroidpro.viewmodel.main.HomepageViewModel
import com.ldl.wanandroidpro.viewmodel.main.HomepageViewModel.Companion.SHOW_ERROR
import com.ldl.wanandroidpro.viewmodel.main.HomepageViewModel.Companion.SHOW_LOADING
import com.ldl.wanandroidpro.viewmodel.main.HomepageViewModel.Companion.SHOW_NORMAL
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.PageStyle
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.android.synthetic.main.fragment_homepage.*

/**
 * 作者：LDL 创建时间：2020/5/25
 * 类说明：
 */
class HomepageFragment : BaseRootFragment<FragmentHomepageBinding>() {

    private val viewModel by viewModels<HomepageViewModel>()
    private lateinit var mAdapter: HomepageAdapter
    private var mBanner: BannerViewPager<BannerData, BannerViewHolder>? = null

    override fun onResume() {
        super.onResume()
        mBanner?.startLoop()
    }

    override fun onPause() {
        super.onPause()
        mBanner?.stopLoop()
    }

    override fun getLayoutId(): Int = R.layout.fragment_homepage

    override fun initView() {
        binding.viewModel = viewModel
        initLoadingStatusView(swipeRefreshLayout)
        initRecyclerView()
        initHead()
        initBanner()
    }

    private fun initRecyclerView() {
        mAdapter = HomepageAdapter(viewModel.homepageMultiData)
        recyclerView.layoutManager = LinearLayoutManager(mActivity)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = mAdapter
    }

    @SuppressLint("InflateParams")
    private fun initHead() {
        val headView = LayoutInflater.from(mActivity).inflate(R.layout.head_banner, null)
        mBanner = headView.findViewById(R.id.banner_view)
        val rvMenu = headView.findViewById<RecyclerView>(R.id.rv_menu)
        rvMenu.setHasFixedSize(true)
        rvMenu.layoutManager = GridLayoutManager(mActivity, 5)
        val menuAdapter = MenuAdapter(viewModel.getMenuList())
        rvMenu.adapter = menuAdapter
        menuAdapter.setOnItemClickListener { _, _, position ->

        }
        mAdapter.addHeaderView(headView)
    }

    private fun initBanner() {
        mBanner?.apply {
            setPageStyle(PageStyle.MULTI_PAGE_OVERLAP)
                .setPageMargin(ConvertUtils.dp2px(20f))
                .setRevealWidth(ConvertUtils.dp2px(0f))
                .setScrollDuration(500)
                .setIndicatorSlideMode(IndicatorSlideMode.WORM)
                .setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                .setAdapter(BannerViewAdapter())
                .setOnPageClickListener {
                    viewModel.bannerData?.apply {
                        val bannerData = this[it]
                        WebViewActivity.start(bannerData.title, bannerData.url)
                    }
                }
                .create()
        }
    }

    override fun initData() {
        viewModel.getAllData()
        observe()
    }

    override fun reload() {
        viewModel.getAllData()
    }

    private fun observe() {
        viewModel.showLoading.observe(this, Observer {
            when (it) {
                SHOW_LOADING -> {
                    showLoading()
                }
                SHOW_NORMAL -> {
                    showNormal()
                    mBanner?.refreshData(viewModel.bannerData)
                    mAdapter.setList(viewModel.homepageMultiData)
                }
                SHOW_ERROR -> {
                    showError()
                }
                else -> {
                    showNormal()
                }
            }
        })
        viewModel.refreshing.observe(this, Observer {
            if (it) {
                mAdapter.setList(viewModel.homepageMultiData)
            }
        })
        viewModel.eventMsg.observe(this, Observer {
            mAdapter.setList(viewModel.homepageMultiData)
        })
    }

}