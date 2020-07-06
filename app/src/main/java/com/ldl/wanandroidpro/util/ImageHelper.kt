package com.ldl.wanandroidpro.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.blankj.utilcode.util.ConvertUtils
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.ldl.wanandroidpro.R
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


/**
 * 作者：LDL 创建时间：2020/5/26
 * 类说明：
 */
object ImageHelper {

    private val images: ArrayList<Int> by lazy { ArrayList<Int>() }

    init {
        images.add(R.drawable.bg_1)
        images.add(R.drawable.bg_2)
        images.add(R.drawable.bg_3)
        images.add(R.drawable.bg_4)
        images.add(R.drawable.bg_5)
        images.add(R.drawable.bg_6)
        images.add(R.drawable.bg_7)
        images.add(R.drawable.bg_8)
    }


    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImageRounded(imageView: ImageView, url: String?) {
        val random = (0 until images.size).random()
        GlideApp.with(imageView.context)
            .load(images[random])
            .transform(
                CenterCrop(), RoundedCornersTransformation(
                    ConvertUtils.dp2px(5f), 0,
                    RoundedCornersTransformation.CornerType.ALL
                )
            )
            .into(imageView)
    }

    @BindingAdapter("android:src")
    @JvmStatic
    fun loadImageSrc(imageView: ImageView, @DrawableRes resId: Int) {
        imageView.setImageResource(resId)
    }

}