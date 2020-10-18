package com.abhilekh.assignmentapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abhilekh.assignmentapplication.model.AnimalApiService
import com.abhilekh.assignmentapplication.model.ApiResponse
import com.abhilekh.assignmentapplication.model.Hit
import com.mindorks.framework.mvvm.utils.Resource
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ListViewModel(application: Application) : AndroidViewModel(application) {


    private val animals = MutableLiveData<Resource<List<Hit>>>()
    private val disposable = CompositeDisposable()
    private val apiService = AnimalApiService()

    fun refresh() {
        Log.d("mvvm","viewmodel-> refresh")
        animals.postValue(Resource.loading(null))
        fetchAnimals()
    }

    init {
        Log.d("mvvm","viewmodel-> init")
        animals.postValue(Resource.loading(null))
        fetchAnimals()
        Log.d("Krishna","added log")
    }


    private fun  fetchAnimals() {
        Log.d("mvvm","viewmodel-> api called")
        getSingleObserver()?.let {
            apiService. fetchAnimals()
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
                animals.postValue(Resource.success(value.hits))
            }

            override fun onError(e: Throwable) {
                Thread.sleep(2000)
                animals.postValue(Resource.error("Something went wrong!",null))
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed)
            disposable.dispose()
    }

    // method to get list
    fun getAnimals(): LiveData<Resource<List<Hit>>> {
        return animals
    }
}



