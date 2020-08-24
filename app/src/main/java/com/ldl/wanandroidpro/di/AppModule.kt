package com.ldl.wanandroidpro.di

import com.ldl.wanandroidpro.viewmodel.main.HomepageViewModel
import com.ldl.wanandroidpro.viewmodel.main.MainViewModel
import com.ldl.wanandroidpro.viewmodel.main.repository.HomepageRepository
import com.ldl.wanandroidpro.viewmodel.main.repository.MainRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * 作者：LDL 创建时间：2020/8/5
 * 类说明：
 */
val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { HomepageViewModel(get()) }
}

val repositoryModule = module {
    factory { MainRepository() }
    factory { HomepageRepository() }
}