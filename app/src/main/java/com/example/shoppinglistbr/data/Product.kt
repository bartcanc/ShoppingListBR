package com.example.shoppinglistbr.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    val title: String,
    val category: String,
    val count: Int,
) : Parcelable
