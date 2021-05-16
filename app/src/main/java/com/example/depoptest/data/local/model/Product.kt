package com.example.depoptest.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.depoptest.data.remote.response.model.PicturesData
import java.io.Serializable

@Entity
data class Product(
    val active_status: String,
    val address: String,
    val brand_id: Int,
    val categories: List<Int>,
    val country: String,
    val created_date: String,
    val description: String,
    val hand_delivery: Boolean,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val international_shipping_cost: String? = "0.00",
    val national_shipping_cost: String? = "0.00",
    val pictures_data: List<PicturesData>,
    val price_amount: Float,
    val price_currency: String,
    val pub_date: String,
    val purchase_via_paypal: Boolean,
    val status: String,
    val user_id: Int,
) : Serializable {

    val smallThumbNail: String
        get() = pictures_data.first().smallThumbnail

    val productCost: String
        get() = when (price_currency) {
            "GBP" -> "£${price_amount}"
            "USD" -> "$${price_amount}"
            "EUR" -> "€${price_amount}"
            else -> "$price_amount"
        }

    val internationalShippingCost: String?
        get() = when (price_currency) {
            "GBP" -> "£${international_shipping_cost}"
            "USD" -> "$${international_shipping_cost}"
            "EUR" -> "€${international_shipping_cost}"
            else -> "$international_shipping_cost"
        }

    val nationalShippingCost: String?
        get() = when (price_currency) {
            "GBP" -> "£${national_shipping_cost}"
            "USD" -> "$${national_shipping_cost}"
            "EUR" -> "€${national_shipping_cost}"
            else -> "$national_shipping_cost"
        }


}