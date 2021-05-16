package com.example.depoptest.data.remote

import com.example.depoptest.data.remote.response.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DepopAPI {

    @GET("v1/products/popular/")
    suspend fun getPopularProducts(
        @Query("offset_id") offsetId: Int
    ) : Response<ProductsResponse>

}