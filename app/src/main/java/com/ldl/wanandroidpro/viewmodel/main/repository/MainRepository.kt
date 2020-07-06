package com.ldl.wanandroidpro.viewmodel.main.repository

import com.ldl.wanandroidpro.core.api.Apis
import com.ldl.wanandroidpro.core.model.main.LoginData
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse

/**
 * 作者：LDL 创建时间：2020/5/28
 * 类说明：
 */
class MainRepository {

    suspend fun logout() = RxHttp.get(Apis.logout())
        .toResponse<LoginData>()
        .await()
}