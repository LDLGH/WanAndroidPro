package com.ldl.wanandroidpro.ui.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.core.model.main.MenuData
import com.ldl.wanandroidpro.databinding.ItemMenuBinding

/**
 * 作者：LDL 创建时间：2020/1/6
 * 类说明：
 */
class MenuAdapter(data: ArrayList<MenuData>) :
    BaseQuickAdapter<MenuData, BaseDataBindingHolder<ItemMenuBinding>>(R.layout.item_menu, data) {

    override fun convert(holder: BaseDataBindingHolder<ItemMenuBinding>, item: MenuData) {
        holder.dataBinding?.apply {
            data = item
            executePendingBindings()
        }
    }
}