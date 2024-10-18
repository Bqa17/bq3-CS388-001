package com.codepath.moviesproject
import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class BaseResponse(
    @SerialName("results")
    val result: List<Movie>?
)

@Keep
@Serializable
data class Movie(
    @SerialName("overview")
    val overview: String?,
    @SerialName("popularity")
    val popularity: Float?,
    @SerialName("original_title")
    val title: String?,
    @SerialName("poster_path")
    val poster: String?,
    @SerialName("backdrop_path")
    val backdrop: String?,
    @SerialName("vote_average")
    val rating: Float?,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("original_language")
    val language: String?

) : java.io.Serializable {
    val posterUrl: String = "https://image.tmdb.org/t/p/w500/${poster}"
    val backdropUrl: String = "https://image.tmdb.org/t/p/w500/${backdrop}"
}
