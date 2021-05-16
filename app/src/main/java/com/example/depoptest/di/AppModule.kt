package com.example.depoptest.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.depoptest.R
import com.example.depoptest.adapters.ProductAdapter
import com.example.depoptest.data.local.ProductDao
import com.example.depoptest.data.local.ProductDatabase
import com.example.depoptest.data.remote.DepopAPI
import com.example.depoptest.repositories.DefaultProductsRepository
import com.example.depoptest.repositories.ProductsRepository
import com.example.depoptest.util.Constants.BASE_URL
import com.example.depoptest.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideProductDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            ProductDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideProductDao(database: ProductDatabase) =
        database.productDao()

    @Singleton
    @Provides
    fun provideDepopApi() =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(DepopAPI::class.java)

    @Singleton
    @Provides
    fun prodiveDefaultProductRepository(
        dao: ProductDao,
        api: DepopAPI
    ) = DefaultProductsRepository(dao, api) as ProductsRepository

    @Singleton
    @Provides
    fun provideGlideInstance(@ApplicationContext app: Context) =
        Glide.with(app).setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
        )

}