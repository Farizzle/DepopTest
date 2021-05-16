package com.example.depoptest.data.remote.response.model

import java.io.Serializable

data class PicturesData(
    val formats: Formats,
    val id: Int
) : Serializable {
    val smallThumbnail: String
        get() = formats.P2.url
    val largeThumbnail: String
        get() = formats.P8.url
}