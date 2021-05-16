package com.example.depoptest.data.remote.response.model

import java.io.Serializable

data class Picture(
    val height: Int,
    val url: String,
    val width: Int
) : Serializable