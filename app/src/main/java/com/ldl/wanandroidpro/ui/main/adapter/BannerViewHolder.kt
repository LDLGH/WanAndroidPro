package com.ldl.wanandroidpro.ui.main.adapter

import android.view.View
import android.widget.ImageView
import com.blankj.utilcode.util.ConvertUtils
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.core.model.main.BannerData
import com.ldl.wanandroidpro.util.GlideApp
import com.zhpan.bannerview.BaseViewHolder
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * 作者：LDL 创建时间：2020/1/2
 * 类说明：
 */
class BannerViewHolder(itemView: View) : BaseViewHolder<BannerData>(itemView) {

    override fun bindData(data: BannerData?, position: Int, pageSize: Int) {
        val imageView = itemView.findViewById<ImageView>(R.id.iv_banner)
        GlideApp.with(imageView!!.context)
            .load(data?.imagePath)
            .transform(
                CenterCrop(), RoundedCornersTransformation(
                    ConvertUtils.dp2px(10f), 0,
                    RoundedCornersTransformation.CornerType.ALL
                )
            )
            .into(imageView)
    }
}