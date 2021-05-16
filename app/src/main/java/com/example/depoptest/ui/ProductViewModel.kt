package com.example.depoptest.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ApplicationProvider
import com.example.depoptest.data.local.model.Product
import com.example.depoptest.data.remote.response.ProductsResponse
import com.example.depoptest.data.remote.response.model.asDatabaseModel
import com.example.depoptest.repositories.ProductsRepository
import com.example.depoptest.util.Event
import com.example.depoptest.util.Resource
import com.example.depoptest.util.hasInternetConnection
import kotlinx.coroutines.launch
import java.io.IOException

typealias ProductsEvent = Event<Resource<ProductsResponse>>

class ProductViewModel @ViewModelInject constructor(
    val repository: ProductsRepository,
    val app: Application = ApplicationProvider.getApplicationContext()
) : AndroidViewModel(app) {

    val allProducts = repository.getAllProducts()

    private val _productsStatus = MutableLiveData<ProductsEvent>()
    val productsStatus: LiveData<ProductsEvent>
        get() = _productsStatus

    private var _currentProduct = MutableLiveData<Product?>()
    val currentProduct: LiveData<Product?>
    get() = _currentProduct

    init {
        getLatestProducts()
    }

    fun saveProducts(vararg products: Product) = viewModelScope.launch {
        repository.insertProducts(*products)
    }

    fun getLatestProducts(offsetId: Int = 0) = viewModelScope.launch {
        safeLatestProductsCall(offsetId)
    }

    private suspend fun safeLatestProductsCall(offsetId: Int) {
        _productsStatus.value = Event(Resource.loading(null))
        try {
            if (hasInternetConnection(app)){
                val response = repository.getLatestPopularProducts(offsetId)
                response.data?.let { safeResponse ->
                    saveProducts(*safeResponse.objects.asDatabaseModel())
                }
                _productsStatus.value = Event(response)
            } else {
                _productsStatus.postValue(Event(Resource.error("No internet connection")))
                _productsStatus.postValue(Event(Resource.error("No internet connection")))
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> _productsStatus.postValue(Event(Resource.error("Network Failure")))
                else -> _productsStatus.postValue(Event(Resource.error("Conversion Failure")))
            }
        }
    }

    fun assignCurrentProduct(product: Product) {
        _currentProduct.value = product
    }

    fun removeCurrentProduct(){
        _currentProduct.value = null
    }


}