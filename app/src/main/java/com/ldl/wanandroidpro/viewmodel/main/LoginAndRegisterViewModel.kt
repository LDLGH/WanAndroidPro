package com.ldl.wanandroidpro.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.rxLifeScope
import com.blankj.utilcode.util.ToastUtils
import com.ldl.wanandroidpro.base.viewmodel.BaseViewModel
import com.ldl.wanandroidpro.core.DataManager
import com.ldl.wanandroidpro.core.model.event.EventMsg
import com.ldl.wanandroidpro.util.RxBusManager
import com.ldl.wanandroidpro.viewmodel.main.repository.LoginAndRegisterRepository

/**
 * 作者：LDL 创建时间：2020/5/27
 * 类说明：
 */
class LoginAndRegisterViewModel : BaseViewModel() {

    private val mRepository by lazy { LoginAndRegisterRepository() }

    var isLogin = MutableLiveData<Boolean>()

    var isRegisterAndLogin = MutableLiveData<Boolean>()

    var isLoading = MutableLiveData<Boolean>()

    fun login(username: String?, password: String?) {
        rxLifeScope.launch({
            val loginData = mRepository.login(username, password)
            DataManager.setAccount(loginData.username)
            DataManager.setPassword(loginData.password)
            DataManager.setLoginStatus(true)
            isLogin.value = true
            RxBusManager.post(EventMsg(EventMsg.LOGIN, "login"))
        }, {
            isLogin.value = false
            ToastUtils.showShort(it.message)
        }, {
            isLoading.value = true
        }, {
            isLoading.value = false
        })
    }

    fun registerAndLogin(username: String?, password: String?) {
        rxLifeScope.launch({
            mRepository.register(username, password)
            val loginData = mRepository.login(username, password)
            DataManager.setAccount(loginData.username)
            DataManager.setPassword(loginData.password)
            DataManager.setLoginStatus(true)
            isRegisterAndLogin.value = true
            RxBusManager.post(EventMsg(EventMsg.LOGIN, "login"))
        }, {
            isRegisterAndLogin.value = false
            ToastUtils.showShort(it.message)
        }, {
            isLoading.value = true
        }, {
            isLoading.value = false
        })
    }

}