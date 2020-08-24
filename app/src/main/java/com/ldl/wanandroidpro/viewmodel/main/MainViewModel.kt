package com.ldl.wanandroidpro.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.rxLifeScope
import com.blankj.rxbus.RxBus
import com.ldl.wanandroidpro.base.viewmodel.BaseViewModel
import com.ldl.wanandroidpro.core.DataManager
import com.ldl.wanandroidpro.core.model.event.EventMsg
import com.ldl.wanandroidpro.util.RxBusManager
import com.ldl.wanandroidpro.viewmodel.main.repository.MainRepository

/**
 * 作者：LDL 创建时间：2020/5/20
 * 类说明：
 */
class MainViewModel(private val mRepository: MainRepository) : BaseViewModel() {

    var isLoading = MutableLiveData<Boolean>()

    var isLogout = MutableLiveData<Boolean>()

    var eventMsg = MutableLiveData<String>()

    init {
        RxBusManager.subscribe(this, object : RxBus.Callback<EventMsg>() {
            override fun onEvent(t: EventMsg?) {
                if (t?.code == EventMsg.LOGIN) {
                    eventMsg.value = t.msg
                }
            }
        })
    }

    fun logout() {
        rxLifeScope.launch({
            mRepository.logout()
            DataManager.setAccount("")
            DataManager.setPassword("")
            DataManager.setLoginStatus(false)
            RxBusManager.post(EventMsg(EventMsg.LOGIN, "logout"))
            isLogout.value = true
        }, {
            isLogout.value = false
        }, {
            isLoading.value = true
        }, {
            isLoading.value = false
        })
    }


    override fun onCleared() {
        super.onCleared()
        RxBusManager.unregister(this)
    }
}