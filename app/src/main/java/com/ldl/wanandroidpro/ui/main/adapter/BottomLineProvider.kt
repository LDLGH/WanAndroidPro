package com.ldl.wanandroidpro.ui.main.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.core.model.main.HomepageMultiData
import com.ldl.wanandroidpro.databinding.ItemBottomLineBinding

/**
 * 作者：LDL 创建时间：2020/1/13
 * 类说明：
 */
class BottomLineProvider : BaseItemProvider<HomepageMultiData>() {

    override val itemViewType: Int = HomepageMultiData.BOTTOM_LINE

    override val layoutId: Int = R.layout.item_bottom_line

    override fun onViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemBottomLineBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: HomepageMultiData) {
        val binding = helper.getBinding<ItemBottomLineBinding>()
        binding?.also {
            binding.executePendingBindings()
        }
    }
}