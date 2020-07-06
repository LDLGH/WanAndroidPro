package com.ldl.wanandroidpro.viewmodel.main.repository

import com.ldl.wanandroidpro.core.api.Apis
import com.ldl.wanandroidpro.core.model.main.LoginData
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse

/**
 * 作者：LDL 创建时间：2020/5/27
 * 类说明：
 */
class LoginAndRegisterRepository {

    suspend fun login(username: String?, password: String?): LoginData =
        RxHttp.postForm(Apis.getLoginData())
            .add("username", username)
            .add("password", password)
            .toResponse<LoginData>()
            .await()

    suspend fun register(username: String?, password: String?): LoginData =
        RxHttp.postForm(Apis.getRegisterData())
            .add("username", username)
            .add("password", password)
            .add("repassword", password)
            .toResponse<LoginData>()
            .await()

}