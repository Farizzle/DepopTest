package com.example.depoptest.data.local.model

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ProductTest {

    lateinit var product: Product

    @Before
    fun setup() {
        product = Product(
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
    }

    @Test
    fun `product with GBP gives £ currency symbol for productCost, returns true`() {
        val inputCurrencyString = "GBP"
        val inputCurrencyValue = 10.00f
        val outputCurrencySymbol = "£"
        val editedProduct = product.copy(price_amount = inputCurrencyValue, price_currency = inputCurrencyString)
        val expectedProductCostString = "$outputCurrencySymbol${inputCurrencyValue}"
        Truth.assertThat(editedProduct.productCost).isEqualTo(expectedProductCostString)
    }

    @Test
    fun `product with USD gives $ currency symbol for internationalShipping, returns true`() {
        val inputCurrencyString = "USD"
        val inputCurrencyValue = 10.00f
        val outputCurrencySymbol = "$"
        val editedProduct = product.copy(international_shipping_cost = inputCurrencyValue.toString(), price_currency = inputCurrencyString)
        val expectedProductCostString = "$outputCurrencySymbol${inputCurrencyValue}"
        Truth.assertThat(editedProduct.internationalShippingCost).isEqualTo(expectedProductCostString)
    }

    @Test
    fun `product with EUR gives € currency symbol for nationalShipping, returns true`() {
        val inputCurrencyString = "EUR"
        val inputCurrencyValue = 10.00f
        val outputCurrencySymbol = "€"
        val editedProduct = product.copy(national_shipping_cost = inputCurrencyValue.toString(), price_currency = inputCurrencyString)
        val expectedProductCostString = "$outputCurrencySymbol${inputCurrencyValue}"
        Truth.assertThat(editedProduct.nationalShippingCost).isEqualTo(expectedProductCostString)
    }



}