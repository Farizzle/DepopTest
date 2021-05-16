package com.example.depoptest.repositories

import androidx.lifecycle.LiveData
import com.example.depoptest.data.local.ProductDao
import com.example.depoptest.data.local.model.Product
import com.example.depoptest.data.remote.DepopAPI
import com.example.depoptest.data.remote.response.ProductsResponse
import com.example.depoptest.data.remote.response.model.asDatabaseModel
import com.example.depoptest.util.Resource
import java.lang.Exception
import javax.inject.Inject

class DefaultProductsRepository @Inject constructor(
    private val dao: ProductDao,
    private val depopAPI: DepopAPI
) : ProductsRepository {

    override suspend fun insertProducts(vararg product: Product) {
        dao.upsert(*product)
    }

    override fun getAllProducts(): LiveData<List<Product>> = dao.getAllProducts()

    override suspend fun getLatestPopularProducts(offsetId: Int): Resource<ProductsResponse> {
        return try {
            val response = depopAPI.getPopularProducts(offsetId)
            if (response.isSuccessful) {
                response.body()?.let { productsResponse ->
                    return@let Resource.success(productsResponse)
                } ?: Resource.error("Unknown error - ${response.message()}", null)
            } else {
                return Resource.error("Unknown error - ${response.message()}", null)
            }
        } catch (e: Exception) {
            Resource.error("Error occured - ${e.message}", null)
        }
    }

}