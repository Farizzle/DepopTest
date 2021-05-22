package com.example.depoptest.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.depoptest.data.local.model.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun upsert(vararg product: Product)

    @Query("SELECT * FROM product WHERE (isFavourite == :favourites)")
    fun getAllProducts(favourites: Boolean): LiveData<List<Product>>

    @Update
    suspend fun update(product: Product)

}