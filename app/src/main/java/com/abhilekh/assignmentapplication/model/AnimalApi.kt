package com.abhilekh.assignmentapplication.model

import io.reactivex.Single
import retrofit2.http.GET


interface AnimalApi {

    @GET("?key=16240877-f820046deedb6864c1fa503d2&q=yellow+flowers&image_type=photo&pretty=true")
    fun getAnimals(): Single<ApiResponse>
}