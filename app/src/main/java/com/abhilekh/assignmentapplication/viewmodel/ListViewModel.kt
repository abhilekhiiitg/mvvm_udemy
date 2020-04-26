package com.abhilekh.assignmentapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.abhilekh.assignmentapplication.model.AnimalApiService
import com.abhilekh.assignmentapplication.model.ApiResponse
import com.abhilekh.assignmentapplication.model.Hit
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ListViewModel(application: Application) : AndroidViewModel(application) {

    //lazy:- Create only when needed
    val animals by lazy { MutableLiveData<List<Hit>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()
    private val apiService = AnimalApiService()

    fun refresh() {
        Log.d("mvvm","viewmodel-> refresh")
        loading.value = true
        getAnimals()
    }

    init {
        Log.d("mvvm","viewmodel-> init")
        loading.value = true
        getAnimals()
    }


    private fun getAnimals() {
        Log.d("mvvm","viewmodel-> api called")
        getSingleObserver()?.let {
            apiService.getAnimals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it)
        }
    }

    private fun getSingleObserver(): SingleObserver<ApiResponse>? {
        return object : SingleObserver<ApiResponse> {
            override fun onSubscribe(d: Disposable) {
                disposable.add(d)
            }

            override fun onSuccess(value: ApiResponse) {
                animals.value = value.hits
                loadError.value = false
                loading.value = false
            }

            override fun onError(e: Throwable) {
                Thread.sleep(5000)
                animals.value = null
                loadError.value = true
                loading.value = false
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed)
            disposable.dispose()
    }
}



