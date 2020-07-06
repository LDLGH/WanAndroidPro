package com.ldl.wanandroidpro.core.model.main

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

/**
 * 作者：LDL 创建时间：2020/1/6
 * 类说明：
 */
@Parcelize
data class MenuData(var name: String, @DrawableRes var url: Int) : Parcelable