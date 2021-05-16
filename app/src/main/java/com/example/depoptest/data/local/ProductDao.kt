package com.example.depoptest.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.depoptest.data.local.model.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(vararg product: Product)

    @Query("SELECT * FROM product")
    fun getAllProducts(): LiveData<List<Product>>

}