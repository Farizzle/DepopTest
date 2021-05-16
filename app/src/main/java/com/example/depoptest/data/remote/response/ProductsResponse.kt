package com.example.depoptest.data.remote.response

import com.example.depoptest.data.remote.response.model.APIProduct
import com.example.depoptest.data.remote.response.model.Meta

data class ProductsResponse(
    val meta: Meta,
    val objects: List<APIProduct>
)