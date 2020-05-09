package com.abhilekh.assignmentapplication.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class AnimalApiService {

    private val BASE_URL = "https://pixabay.com/api/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(AnimalApi::class.java)


    fun  fetchAnimals(): Single<ApiResponse> {
        return api.getAnimals()
    }
}