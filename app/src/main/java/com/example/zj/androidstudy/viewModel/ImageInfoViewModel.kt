package com.example.zj.androidstudy.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zj.androidstudy.StudyApplication
import com.example.zj.androidstudy.model.PictureInfo
import com.example.zj.androidstudy.picture.ImageHelper
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * Created on 5/11/21.
 *
 */
class ImageInfoViewModel : ViewModel() {
    private val imageInfoList: MutableLiveData<List<PictureInfo>> by lazy {
        MutableLiveData<List<PictureInfo>>().also {
            fetchImageInfoList()
        }
    }

    fun getImageInfoList(): LiveData<List<PictureInfo>> {
        return imageInfoList
    }

    private fun fetchImageInfoList() {
        Observable.create(ObservableOnSubscribe<List<PictureInfo>> { e: ObservableEmitter<List<PictureInfo>> ->
            val imageInfo = ImageHelper.fetchPicture(StudyApplication.getContext())
            e.onNext(imageInfo)
            e.onComplete()
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { list ->
                    imageInfoList.value = list
                }
                .subscribe(Consumer { uploadImageInfo() })
    }

    private fun uploadImageInfo() {

    }
}