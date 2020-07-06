package com.ldl.wanandroidpro.ui.main.adapter

import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.GsonUtils
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.core.model.main.HomepageMultiData
import com.ldl.wanandroidpro.core.model.main.TopSearchData
import com.ldl.wanandroidpro.databinding.ItemHotSearchBinding
import com.ldl.wanandroidpro.ui.main.activity.SearchListActivity
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

/**
 * 作者：LDL 创建时间：2020/1/7
 * 类说明：
 */
class HotSearchItemProvider : BaseItemProvider<HomepageMultiData>() {

    override val itemViewType: Int
        get() = HomepageMultiData.HOT_SEARCH

    override val layoutId: Int
        get() = R.layout.item_hot_search

    override fun onViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemHotSearchBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: HomepageMultiData) {
        val binding = helper.getBinding<ItemHotSearchBinding>()
        binding?.also {
            binding.data = item
            binding.executePendingBindings()
        }

        val flowLayout = helper.getView<TagFlowLayout>(R.id.flowLayout)

        val list = GsonUtils.fromJson<List<TopSearchData>>(
            item.data,
            GsonUtils.getListType(TopSearchData::class.java)
        )
        val tagAdapter = object : TagAdapter<TopSearchData>(list) {
            override fun getView(parent: FlowLayout?, position: Int, t: TopSearchData?): View {
                val tv = TextView(flowLayout.context)
                tv.text = t?.name
                tv.setTextColor(ColorUtils.getColor(R.color.colorTextBlack))
                tv.textSize = 12f
                tv.setPadding(
                    ConvertUtils.dp2px(10f),
                    ConvertUtils.dp2px(4f),
                    ConvertUtils.dp2px(10f),
                    ConvertUtils.dp2px(4f)
                )
                tv.setBackgroundResource(R.drawable.selector_search_fl_bg)
                return tv
            }
        }
        flowLayout.adapter = tagAdapter
        flowLayout.setOnTagClickListener { _, position, _ ->
            val topSearchData = list[position]
            SearchListActivity.start(topSearchData.name)
            return@setOnTagClickListener true
        }
    }
}