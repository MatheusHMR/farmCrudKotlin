package com.example.sqliteprojectforfarms

import android.util.Log

class Farm(record: String, name: String, price: Float, latitude: Float, longitude: Float) {
    var record: String = record
    var name: String = name
    var price: Float = price
    var latitude: Float = latitude
    var longitude: Float = longitude

    init {
        Log.i("Test", "Successfully created the object")
    }


//    Visual implementation for listView
    override fun toString(): String {
        return "Register: $record | Name: $name"
    }

    fun toString2(): String {
        return "Farm data: Record: $record - Name: $name - Price: $price - Latitude: $latitude - Longitude: $longitude"
    }

}


//package com.example.sqliteprojectforfarms
//
//import android.util.Log
//
//class Farm (record : String, name : String, price : Float, latitude : Float, longitude : Float) {
//    var record : String;
//    var name : String;
//    var price : Float;
//    var latitude : Float;
//    var longitude : Float;
//
//    init {
//        this.name = name;
//        this.record = record
//        this.longitude = longitude;
//        this.latitude = latitude;
//        this.price = price;
//        Log.i("Test", "Successfully created the object")
//    }
//
//    override fun toString(): String {
//        return("Farm data:" +
//                "Register: " + this.record + " - Name: " + this.name + " - Price: " + this.price +
//                " - Latitude: " + this.latitude + " - Longitude: " + this.longitude)
//    }
//
//}