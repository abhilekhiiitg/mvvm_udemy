package com.abhilekh.assignmentapplication.model


data class Animal(val name: String?)

data class ApiResponse(val total: Int?, val totalHits: Int?, val hits: List<Hit>)


data class Hit(
    val id: Int?,
    val pageURL: String?,
    val type: String?,
    val tags: String?,
    val previewURL: String?,
    val previewWidth: Int?,
    val previewHeight: Int?,
    val webformatWidth: Int?,
    val imageWidth: Int?,
    val imageHeight: Int?,
    val imageSize: Int?,
    val views: Int?,
    val userId: Int?,
    val comments: Int?,
    val likes: Int?,
    val favorites: Int?,
    val downloads: Int?,
    val webformatURL: String?,
    val largeImageURL: String?,
    val userImageURL: String?
)
