package com.ldl.wanandroidpro.ui.main.activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.*
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.editorActionEvents
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.base.activity.BaseActivity
import com.ldl.wanandroidpro.core.dao.HistoryData
import com.ldl.wanandroidpro.databinding.ActivitySearchBinding
import com.ldl.wanandroidpro.ui.main.adapter.HotSearchListAdapter
import com.ldl.wanandroidpro.viewmodel.main.SearchViewModel
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.activity_search.*
import java.util.concurrent.TimeUnit

/**
 * 作者：LDL 创建时间：2020/5/28
 * 类说明：
 */
class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    private val viewModel by viewModels<SearchViewModel>()

    private lateinit var mAdapter: HotSearchListAdapter

    private var mFlHistory: TagFlowLayout? = null
    private var mClHistory: ConstraintLayout? = null

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun onViewCreated() {
        super.onViewCreated()
        binding.viewModel = viewModel
        initEditText()
        initRecyclerView()
        initHead()
    }

    override fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun initData() {
        viewModel.getTopSearchData()
        viewModel.loadAllHistoryData()
        observe()
    }

    private fun initEditText() {
        addSubscribe(
            et_search.editorActionEvents().subscribe {
                if (it.actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val s = et_search.text.toString()
                    if (ObjectUtils.isEmpty(s)) {
                        return@subscribe
                    }
                    viewModel.addHistoryData(s)
                    KeyboardUtils.hideSoftInput(this)
                    startSearchListActivity(key = s)
                    return@subscribe
                }
                return@subscribe
            }

        )

    }

    @SuppressLint("InflateParams")
    private fun initHead() {
        val headView = LayoutInflater.from(this).inflate(R.layout.layout_search_head, null)
        mAdapter.addHeaderView(headView)
        mClHistory = headView.findViewById(R.id.cl_history)
        val ivDel = headView.findViewById<ImageView>(R.id.iv_del)
        mFlHistory = headView.findViewById(R.id.fl_history)
        addSubscribe(ivDel.clicks()
            .throttleFirst(2, TimeUnit.SECONDS)
            .subscribe {
                viewModel.clearHistoryData()
                mClHistory?.visibility = View.GONE
            }
        )
    }

    private fun initRecyclerView() {
        rv_search.setHasFixedSize(true)
        rv_search.layoutManager = LinearLayoutManager(this)
        mAdapter = HotSearchListAdapter(viewModel.topSearchDataList)
        rv_search.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            startSearchListActivity(viewModel.topSearchDataList[position].name)
        }
    }

    private fun setTagFlowLayout(historyDataList: List<HistoryData>) {
        mFlHistory?.adapter = object : TagAdapter<HistoryData>(historyDataList) {
            override fun getView(parent: FlowLayout?, position: Int, t: HistoryData?): View {
                val tv = TextView(parent?.context)
                tv.text = t?.key
                tv.setTextColor(ColorUtils.getColor(R.color.colorTextBlack))
                tv.textSize = 12f
                tv.setPadding(
                    ConvertUtils.dp2px(10f),
                    ConvertUtils.dp2px(4f),
                    ConvertUtils.dp2px(10f),
                    ConvertUtils.dp2px(4f)
                )
                tv.setBackgroundResource(R.drawable.selector_search_fl_bg)
                return tv
            }
        }
        mFlHistory?.setOnTagClickListener { _, position, _ ->
            startSearchListActivity(historyDataList[position].key)
            return@setOnTagClickListener true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                ActivityUtils.finishActivity(this, true)
                return true
            }
            R.id.action_search -> {
                val key = et_search.text.toString()
                if (ObjectUtils.isNotEmpty(key)) {
                    viewModel.addHistoryData(et_search.text.toString())
                    KeyboardUtils.hideSoftInput(this)
                    startSearchListActivity(et_search.text.toString())
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startSearchListActivity(key: String) {
        SearchListActivity.start(key)
    }

    private fun observe() {
        viewModel.historyData.observe(this, Observer {
            if (ObjectUtils.isNotEmpty(it)) {
                mClHistory?.visibility = View.VISIBLE
                setTagFlowLayout(it)
            }
        })
        viewModel.topSearchData.observe(this, Observer {
            mAdapter.setList(viewModel.topSearchDataList)
        })
    }
}