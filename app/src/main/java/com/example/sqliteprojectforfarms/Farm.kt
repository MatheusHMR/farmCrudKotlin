package com.example.sqliteprojectforfarms

import android.util.Log

class Farm( name: String, record: String, price: Float, longitude: Float, latitude: Float) {
    var name: String = name
    var record: String = record
    var price: Float = price
    var longitude: Float = longitude
    var latitude: Float = latitude

    init {
        Log.i("Test", "Successfully created the object")
    }


//    Visual implementation for listView
    override fun toString(): String {
        return "Record: $record | Name: $name"
    }

    fun toString2(): String {
        return "Farm data: Record: $record - Name: $name - Price: $price - Latitude: $latitude - Longitude: $longitude"
    }

}
