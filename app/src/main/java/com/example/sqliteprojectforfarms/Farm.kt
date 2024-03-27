package com.example.sqliteprojectforfarms

class Farm (register : String, name : String, price : Float, latitude : Float, longitude : Float) {
    var register : String;
    var name : String;
    var price : Float;
    var latitude : Float;
    var longitude : Float;

    init {
        this.register = register;
        this.name = name;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    override fun toString(): String {
        return("Farm data:" +
                "Register: " + this.register + " - Name: " + this.name + " - Price: " + this.price +
                " - Latitude: " + this.latitude + " - Longitude: " + this.longitude)
    }

}