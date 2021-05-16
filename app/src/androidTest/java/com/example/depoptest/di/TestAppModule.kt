package com.example.depoptest.di

import android.content.Context
import androidx.room.Room
import com.example.depoptest.data.local.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDatabase(
        @ApplicationContext app: Context
    ) = Room.inMemoryDatabaseBuilder(
        app,
        ProductDatabase::class.java
    ).allowMainThreadQueries().build()

    @Provides
    @Named("test_dao")
    fun provideShoppingDao(
        @Named("test_db") database: ProductDatabase
    ) = database.productDao()

}