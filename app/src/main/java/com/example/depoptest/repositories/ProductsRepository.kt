package com.example.depoptest.repositories

import androidx.lifecycle.LiveData
import com.example.depoptest.data.local.model.Product
import com.example.depoptest.data.remote.response.ProductsResponse
import com.example.depoptest.util.Resource

interface ProductsRepository {
    suspend fun insertProducts(vararg product: Product)
    fun getAllProducts() : LiveData<List<Product>>
    suspend fun getLatestPopularProducts(offsetId: Int) : Resource<ProductsResponse>
}