package com.example.shoppinglistbr.data



object Products {
    val list: MutableList<Product> = ArrayList()
    fun addProduct(product: Product) {
        list.add(product)
    }


    fun updateProduct(oldProduct: Product?, newProduct: Product) {
//perform the update operation only if the old task is not null
        oldProduct?.let { old ->
//find the index of the old task in the list
// indexOf will be -1 if the old task is not in the list
            val indexOfOld = list.indexOf(old)
            if (indexOfOld != -1) { // check if the old task is in the list
//replace the old task with the new task
                list[indexOfOld] = newProduct
            }
        }
    }
}