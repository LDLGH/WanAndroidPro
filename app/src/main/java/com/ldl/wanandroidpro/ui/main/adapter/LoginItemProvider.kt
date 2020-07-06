package com.ldl.wanandroidpro.ui.main.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.ActivityUtils
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.core.model.main.HomepageMultiData
import com.ldl.wanandroidpro.databinding.ItemLoginBinding
import com.ldl.wanandroidpro.ui.main.activity.LoginAndRegisterActivity


/**
 * 作者：LDL 创建时间：2020/1/7
 * 类说明：
 */
class LoginItemProvider : BaseItemProvider<HomepageMultiData>() {

    init {
        addChildClickViewIds(R.id.tv_login)
    }

    override val itemViewType: Int
        get() = HomepageMultiData.LOGIN

    override val layoutId: Int
        get() = R.layout.item_login

    override fun onViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemLoginBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: HomepageMultiData) {
        val binding = helper.getBinding<ItemLoginBinding>()
        binding?.also {
            binding.executePendingBindings()
        }
    }

    override fun onChildClick(
        helper: BaseViewHolder,
        view: View,
        data: HomepageMultiData,
        position: Int
    ) {
        ActivityUtils.startActivity(LoginAndRegisterActivity::class.java)
    }
}