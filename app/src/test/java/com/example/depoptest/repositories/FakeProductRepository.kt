package com.example.depoptest.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.depoptest.data.local.model.Product
import com.example.depoptest.data.remote.response.ProductsResponse
import com.example.depoptest.data.remote.response.model.Meta
import com.example.depoptest.util.Resource

class FakeProductRepository(private val shouldReturnNetworkError: Boolean) : ProductsRepository {

    private val products = mutableListOf<Product>()

    private val observableProducts = MutableLiveData<List<Product>>(products)

    private fun refreshLiveData() {
        observableProducts.postValue(products)
    }

    override suspend fun insertProducts(vararg product: Product) {
        this.products.addAll(product)
        refreshLiveData()
    }

    override fun getAllProducts(): LiveData<List<Product>> {
        return observableProducts
    }

    override suspend fun getLatestPopularProducts(offsetId: Int): Resource<ProductsResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error("Network error")
        } else {
            Resource.success(
                ProductsResponse(
                    Meta(
                        true,
                        "fake_offset_id",
                        10
                    ),
                    listOf()
                )
            )
        }
    }

}