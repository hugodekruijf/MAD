package com.example.level4task1.database

import android.content.Context
import com.androidcourse.shoppinglistkotlin.model.Product

class ProductRepository(context: Context) {
    private val productDao: ProductDao

    init {
        val database =
            ShoppingListRoomDatabase.getDatabase(
                context
            )
        productDao = database!!.productDao()
    }

    suspend fun getAllProducts(): List<Product> = productDao.getAllProducts()

    suspend fun insertProduct(product: Product) = productDao.insertProduct(product)

    suspend fun deleteProduct(product: Product) = productDao.deleteProduct(product)

    suspend fun deleteAllProducts() = productDao.deleteAllProducts()

}