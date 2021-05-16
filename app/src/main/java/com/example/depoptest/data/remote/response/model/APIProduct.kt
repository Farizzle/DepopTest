package com.example.depoptest.data.remote.response.model

import com.example.depoptest.data.local.model.Product

data class APIProduct(
    val active_status: String,
    val address: String,
    val brand_id: Int,
    val categories: List<Int>,
    val country: String,
    val created_date: String,
    val description: String,
    val hand_delivery: Boolean,
    val id: Int,
    val international_shipping_cost: String?,
    val national_shipping_cost: String?,
    val pictures_data: List<PicturesData>,
    val price_amount: Float,
    val price_currency: String,
    val pub_date: String,
    val purchase_via_paypal: Boolean,
    val status: String,
    val user_id: Int,
)

fun List<APIProduct>.asDatabaseModel(): Array<Product> {
    return map {
        Product(
            active_status = it.active_status,
            address = it.address,
            brand_id = it.brand_id,
            categories = it.categories,
            country = it.country,
            created_date = it.created_date,
            description = it.description,
            hand_delivery = it.hand_delivery,
            id = it.id,
            international_shipping_cost = it.international_shipping_cost ?: "0.00",
            national_shipping_cost = it.national_shipping_cost ?: "0.00",
            pictures_data = it.pictures_data,
            price_amount = it.price_amount,
            price_currency = it.price_currency,
            pub_date = it.pub_date,
            purchase_via_paypal = it.purchase_via_paypal,
            status = it.status,
            user_id = it.user_id
        )
    }.toTypedArray()
}
