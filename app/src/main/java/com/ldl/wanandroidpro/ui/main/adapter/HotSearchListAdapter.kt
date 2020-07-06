package com.ldl.wanandroidpro.ui.main.adapter

import android.graphics.Typeface
import com.blankj.utilcode.util.ColorUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.core.model.main.TopSearchData
import com.ldl.wanandroidpro.databinding.ItemHotSearchListBinding

/**
 * 作者：LDL 创建时间：2020/1/8
 * 类说明：
 */
class HotSearchListAdapter(topSearchDataList: ArrayList<TopSearchData>) :
    BaseQuickAdapter<TopSearchData, BaseDataBindingHolder<ItemHotSearchListBinding>>(
        R.layout.item_hot_search_list, topSearchDataList
    ) {

    override fun convert(
        holder: BaseDataBindingHolder<ItemHotSearchListBinding>,
        item: TopSearchData
    ) {
        holder.dataBinding?.apply {
            topSearchData = item
            executePendingBindings()
            ranking = holder.adapterPosition.toString()
            tvName.typeface =
                if (holder.adapterPosition < 4) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        }
        holder.setTextColor(
            R.id.tv_ranking,
            ColorUtils.getColor(
                if (holder.adapterPosition < 4)
                    R.color.colorAccent
                else R.color.colorTextGray
            )
        )
        holder.setVisible(R.id.tv_hot, holder.adapterPosition < 4)
    }
}