package com.android.avanatest.products.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
data class Product @JvmOverloads constructor(var id: String? = null, @SerializedName("title") var name: String? = null, var description: String? = null,
                                             @SerializedName("image") var imageUrl: String? = null, var price: Double? = null,
                                             var type: String = "PRODUCT") : Parcelable {
    var priceWithCurrency: String = String.format("$ %.2f", price)
}


@Parcelize
data class Cart(var cartItems: ArrayList<CartItem> = arrayListOf(), var date: Date = Date()) : Parcelable {
    private fun getTotalPrice(): Double {
        var totalPrice = 0.0
        cartItems.forEach {
            totalPrice += it.price
        }
        return totalPrice
    }

    fun getPriceWithCurrency(): String {
        return String.format("$ %.2f", getTotalPrice())
    }
}

@Parcelize
data class CartItem(var product: Product, var quantity: Int) : Parcelable {
    val price: Double = product.price!! * quantity
    val priceWithCurrency: String = String.format("$ %.2f", price)
}

