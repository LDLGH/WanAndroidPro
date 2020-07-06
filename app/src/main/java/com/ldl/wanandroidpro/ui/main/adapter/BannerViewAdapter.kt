package com.ldl.wanandroidpro.ui.main.adapter

import android.view.View
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.core.model.main.BannerData
import com.zhpan.bannerview.BaseBannerAdapter

/**
 * 作者：LDL 创建时间：2020/5/26
 * 类说明：
 */
class BannerViewAdapter : BaseBannerAdapter<BannerData, BannerViewHolder>() {

    override fun getLayoutId(viewType: Int): Int = R.layout.layout_banner

    override fun createViewHolder(itemView: View?, viewType: Int): BannerViewHolder {
        return BannerViewHolder(itemView!!)
    }

    override fun onBind(
        holder: BannerViewHolder?,
        data: BannerData?,
        position: Int,
        pageSize: Int
    ) {
        holder?.bindData(data, position, pageSize)
    }
}