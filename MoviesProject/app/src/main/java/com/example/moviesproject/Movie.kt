package com.example.moviesproject

import com.google.gson.annotations.SerializedName


class Movie {
    @JvmField
    @SerializedName("original_title")
    var title: String? = null

    @SerializedName("poster_path")
    var posterImageUrl: String? = null
    @JvmField
    @SerializedName("overview")
    var description: String? = null
}
