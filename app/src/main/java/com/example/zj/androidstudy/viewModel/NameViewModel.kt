package com.example.zj.androidstudy.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created on 4/28/21.
 *
 */
class NameViewModel : ViewModel() {
    private val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>().also {
            loadName()
        }
    }

    fun getName(): LiveData<String> {
        return currentName
    }

    fun setName(name: String) {
        currentName.value = name
    }

    private fun loadName() {
        // Do an asynchronous operation to fetch name
    }
}