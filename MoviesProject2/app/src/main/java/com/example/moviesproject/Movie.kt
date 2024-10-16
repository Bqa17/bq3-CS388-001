package com.example.moviesproject

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//
//class Movie {
//    @JvmField
//    @SerializedName("original_title")
//    var title: String? = null
//
//    @SerializedName("poster_path")
//    var posterImageUrl: String? = null
//    @JvmField
//    @SerializedName("overview")
//    var description: String? = null}




@android.support.annotation.Keep
@Serializable
data class SearchNewsResponse(
    @SerialName("response")
    val response: BaseResponse?
)

@android.support.annotation.Keep
@Serializable
data class BaseResponse(
    @SerialName("results")
    val docs: List<Movie>?
)

@android.support.annotation.Keep
@Serializable
data class Movie(
    @SerialName("original_title")
    val title: String?,
    @SerialName("overview")
    val description: String?,
    @SerialName("poster_path")
    val multimedia: MultiMedia?,
) : java.io.Serializable

@android.support.annotation.Keep
@Serializable
data class HeadLine(
    @SerialName("main")
    val main: String
) : java.io.Serializable

@android.support.annotation.Keep
@Serializable
data class Byline(
    @SerialName("original")
    val original: String? = null
) : java.io.Serializable

@android.support.annotation.Keep
@Serializable
data class MultiMedia(
    @SerialName("poster_path")
    val url: String?
) : java.io.Serializable



