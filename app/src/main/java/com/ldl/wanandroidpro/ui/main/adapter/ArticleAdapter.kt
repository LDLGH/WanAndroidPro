package com.ldl.wanandroidpro.ui.main.adapter

import com.blankj.utilcode.util.ObjectUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.core.model.main.FeedArticleData
import com.ldl.wanandroidpro.databinding.ItemArticleBinding

/**
 * 作者：LDL 创建时间：2020/5/22
 * 类说明：
 */
class ArticleAdapter(list: ArrayList<FeedArticleData>) :
    BaseQuickAdapter<FeedArticleData, BaseDataBindingHolder<ItemArticleBinding>>(
        R.layout.item_article,
        list
    ),
    LoadMoreModule {

    override fun convert(holder: BaseDataBindingHolder<ItemArticleBinding>, item: FeedArticleData) {
        holder.dataBinding?.apply {
            if (ObjectUtils.isEmpty(item.author)) {
                item.author = "Android"
            }
            article = item
            executePendingBindings()
            tvZan.setCompoundDrawablesWithIntrinsicBounds(
                if (item.collect) R.drawable.ic_star_blue_24dp else R.drawable.ic_star_gray_24dp,
                0,
                0,
                0
            )
        }

    }
}