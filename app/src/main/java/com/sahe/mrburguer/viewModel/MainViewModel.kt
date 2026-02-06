package com.sahe.mrburguer.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sahe.mrburguer.domain.BannerModel
import com.sahe.mrburguer.repository.MainRepository

class MainViewModel: ViewModel() {
    private val repository = MainRepository()

    fun loadBanner() : LiveData<MutableList<BannerModel>>{
        return repository.loadBanner()
    }
}