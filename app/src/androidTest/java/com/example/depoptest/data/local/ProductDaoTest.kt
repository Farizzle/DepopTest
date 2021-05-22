package com.example.depoptest.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.depoptest.data.local.model.Product
import com.example.depoptest.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
class ProductDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: ProductDatabase

    @Inject
    @Named("test_dao")
    lateinit var dao: ProductDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertProduct() = runBlockingTest {
        val product = Product(
            active_status = "status",
            address = "address",
            brand_id = 0,
            categories = listOf(),
            country = "country",
            created_date = "testdate",
            description = "description",
            hand_delivery = false,
            id = 1,
            international_shipping_cost = "0.00",
            national_shipping_cost = "0.00",
            pictures_data = listOf(),
            price_amount = 0f,
            price_currency = "GBP",
            pub_date = "pubdate",
            purchase_via_paypal = false,
            status = "active",
            user_id = 15
        )
        dao.upsert(product)
        val allProducts = dao.getAllProducts(false).getOrAwaitValue()
        assertThat(allProducts).contains(product)
    }

    @Test
    fun updateProduct() = runBlockingTest {
        val product = Product(
            active_status = "status",
            address = "address",
            brand_id = 0,
            categories = listOf(),
            country = "country",
            created_date = "testdate",
            description = "description",
            hand_delivery = false,
            id = 1,
            international_shipping_cost = "0.00",
            national_shipping_cost = "0.00",
            pictures_data = listOf(),
            price_amount = 0f,
            price_currency = "GBP",
            pub_date = "pubdate",
            purchase_via_paypal = false,
            status = "active",
            user_id = 15
        )
        dao.upsert(product)
        dao.upsert(product.copy(price_currency = "USD"))
        val allProducts = dao.getAllProducts(false).getOrAwaitValue()
        assertThat(allProducts.first().price_currency).isEqualTo("USD")
    }

}