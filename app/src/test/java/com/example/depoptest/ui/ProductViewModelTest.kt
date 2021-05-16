package com.example.depoptest.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.depoptest.MainCoroutineRule
import com.example.depoptest.data.local.model.Product
import com.example.depoptest.getOrAwaitValueTest
import com.example.depoptest.repositories.FakeProductRepository
import com.example.depoptest.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class ProductViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ProductViewModel

    @Before
    fun setup() {
        viewModel = ProductViewModel(FakeProductRepository(shouldReturnNetworkError = false))
    }

    @Test
    fun `insert product items, returns true`() {
        val fakeProduct = mock(Product::class.java)
        viewModel.saveProducts(fakeProduct)
        val value = viewModel.allProducts.getOrAwaitValueTest()
        assertThat(value).contains(fakeProduct)
    }

    @Test
    fun `assign product updates viewModel, returns true`() {
        val fakeProduct = mock(Product::class.java)
        viewModel.assignCurrentProduct(fakeProduct)
        val value = viewModel.currentProduct.getOrAwaitValueTest()
        assertThat(value).isEqualTo(fakeProduct)
    }

    @Test
    fun `valid network fetch for latest products provides success, returns true`() = runBlockingTest {
        viewModel = ProductViewModel(FakeProductRepository(shouldReturnNetworkError = false))
        viewModel.getLatestProducts()
        val value = viewModel.productsStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()!!.status).isEqualTo(Resource.Status.SUCCESS)
    }

    @Test
    fun `invalid network fetch for latest products provides error, returns true`() = runBlockingTest {
        viewModel = ProductViewModel(FakeProductRepository(shouldReturnNetworkError = true))
        viewModel.getLatestProducts()
        val value = viewModel.productsStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()!!.status).isEqualTo(Resource.Status.ERROR)
    }



}