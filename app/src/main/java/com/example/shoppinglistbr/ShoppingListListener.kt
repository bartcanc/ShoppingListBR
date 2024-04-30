package com.example.shoppinglistbr

interface ShoppingListListener {
    fun onProductClick(productPosition: Int)
    fun onProductLongClick(productPosition: Int)
}
