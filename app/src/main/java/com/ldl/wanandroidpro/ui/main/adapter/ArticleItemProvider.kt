package com.ldl.wanandroidpro.ui.main.adapter

import android.graphics.Color
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.GsonUtils
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.core.model.main.FeedArticleData
import com.ldl.wanandroidpro.core.model.main.HomepageMultiData
import com.ldl.wanandroidpro.databinding.ItemMainContentBinding
import com.ldl.wanandroidpro.ui.main.activity.ArticleActivity
import com.ldl.wanandroidpro.ui.main.activity.WebViewActivity
import com.ldl.wanandroidpro.util.CustomDividerItemDecoration

/**
 * 作者：LDL 创建时间：2020/5/25
 * 类说明：
 */
class ArticleItemProvider : BaseItemProvider<HomepageMultiData>() {
    init {
        addChildClickViewIds(R.id.tv_more)
    }

    override val itemViewType: Int
        get() = HomepageMultiData.ARTICLE
    override val layoutId: Int
        get() = R.layout.item_main_content

    override fun onViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemMainContentBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: HomepageMultiData) {
        val binding = helper.getBinding<ItemMainContentBinding>()
        binding?.also {
            binding.data = item
            binding.executePendingBindings()
        }

        val rvContent = helper.getView<RecyclerView>(R.id.rv_content)
        rvContent.layoutManager =
            LinearLayoutManager(rvContent.context, LinearLayoutManager.HORIZONTAL, false)
        rvContent.setHasFixedSize(true)
        if (rvContent.itemDecorationCount < 1) {
            rvContent.addItemDecoration(
                CustomDividerItemDecoration(
                    rvContent.context,
                    CustomDividerItemDecoration.VERTICAL_LIST,
                    ConvertUtils.dp2px(10f),
                    Color.TRANSPARENT
                )
            )
        }
        val articleData =
            GsonUtils.fromJson<ArrayList<FeedArticleData>>(
                item.data,
                GsonUtils.getListType(FeedArticleData::class.java)
            )
        val articleListAdapter = ArticleListAdapter(articleData)
        rvContent.adapter = articleListAdapter
        rvContent.scrollToPosition(0)
        articleListAdapter.setOnItemClickListener { _, _, position ->
            WebViewActivity.start(articleData[position].title, articleData[position].link)
        }
    }

    override fun onChildClick(
        helper: BaseViewHolder,
        view: View,
        data: HomepageMultiData,
        position: Int
    ) {
        ActivityUtils.startActivity(ArticleActivity::class.java)
    }
}