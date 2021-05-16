package com.example.depoptest.data.local

import androidx.room.TypeConverter
import com.example.depoptest.data.remote.response.model.PicturesData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    val gson = Gson()

    @TypeConverter
    fun categoriesToString(categories: List<Int?>?): String {
        return gson.toJson(categories)
    }

    @TypeConverter
    fun jsonToCategories(json: String): List<Int?>? {
        val categoriesType = object : TypeToken<List<Int?>?>() {}.type
        return gson.fromJson(json, categoriesType)
    }

    @TypeConverter
    fun picturesDataToString(picturesData: List<PicturesData?>): String {
        return gson.toJson(picturesData)
    }

    @TypeConverter
    fun jsonToPicturesData(json: String): List<PicturesData?> {
        val picturesDataType = object : TypeToken<List<PicturesData?>?>() {}.type
        return gson.fromJson(json, picturesDataType)
    }

}