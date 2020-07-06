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
import com.ldl.wanandroidpro.core.model.main.UsefulSiteData
import com.ldl.wanandroidpro.databinding.ItemUsefulSitesBinding
import com.ldl.wanandroidpro.ui.main.activity.WebViewActivity
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

/**
 * 作者：LDL 创建时间：2020/1/7
 * 类说明：
 */
class UsefulSitesItemProvider : BaseItemProvider<HomepageMultiData>() {

    init {
        addChildClickViewIds(R.id.tv_more)
    }

    override val itemViewType: Int
        get() = HomepageMultiData.USEFUL_SITES

    override val layoutId: Int
        get() = R.layout.item_useful_sites

    override fun onViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemUsefulSitesBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: HomepageMultiData) {

        val binding = helper.getBinding<ItemUsefulSitesBinding>()
        binding?.also {
            binding.data = item
            binding.executePendingBindings()
        }
        val flowLayout = helper.getView<TagFlowLayout>(R.id.flowLayout)

        var list = GsonUtils.fromJson<List<UsefulSiteData>>(
            item.data,
            GsonUtils.getListType(UsefulSiteData::class.java)
        )
        if (list.size > 10) {
            list = list.subList(0, 9)
        }
        val tagAdapter = object : TagAdapter<UsefulSiteData>(list) {
            override fun getView(parent: FlowLayout?, position: Int, t: UsefulSiteData?): View {
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
            val usefulSiteData = list[position]
            WebViewActivity.start(usefulSiteData.name, usefulSiteData.link)
            return@setOnTagClickListener true
        }
    }

    override fun onChildClick(
        helper: BaseViewHolder,
        view: View,
        data: HomepageMultiData,
        position: Int
    ) {
        val usefulSiteDataList = GsonUtils.fromJson<List<UsefulSiteData>>(
            data.data,
            GsonUtils.getListType(UsefulSiteData::class.java)
        )
//        val fragment = UsefulSitesDialogFragment.getInstance(usefulSiteDataList)
//        fragment.show(
//            (context as AppCompatActivity).supportFragmentManager,
//            "UsefulSitesDialogFragment"
//        )
    }
}