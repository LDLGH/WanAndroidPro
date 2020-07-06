package com.ldl.wanandroidpro.ui.main.adapter

import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.ObjectUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.core.model.main.FeedArticleData
import com.ldl.wanandroidpro.databinding.ItemArticleListBinding

/**
 * 作者：LDL 创建时间：2020/1/2
 * 类说明：
 */
class ArticleListAdapter(datas: ArrayList<FeedArticleData>) :
    BaseQuickAdapter<FeedArticleData, BaseViewHolder>(
        R.layout.item_article_list,
        datas
    ) {

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemArticleListBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: FeedArticleData) {
        holder.getBinding<ItemArticleListBinding>()?.apply {
            if (ObjectUtils.isEmpty(item.author)) {
                item.author = "Android"
            }
            article = item
            executePendingBindings()
        }
    }
}