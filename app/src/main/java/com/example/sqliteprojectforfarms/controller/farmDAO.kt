package com.example.sqliteprojectforfarms.controller

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.sqliteprojectforfarms.Farm
import com.example.sqliteprojectforfarms.databaseSQLite

class FarmDAO(context: Context) {

    var db : databaseSQLite

    init {
        this.db = databaseSQLite(context)
    }
    fun createFarm(farm: Farm){
        val insertionTransaction = db.writableDatabase
        var farmContent = ContentValues().apply {
            put("name", farm.name)
            put("record", farm.record)
            put("price", farm.price.toString())
            put("latitude", farm.latitude.toString())
            put("longitude", farm.longitude.toString())
        }
        val insertionResult = insertionTransaction?.insert("farm", null, farmContent)
        Log.i("databaseResponses", "Insertion: "+insertionResult)
    }

    fun getAllFarms() : ArrayList<Farm>{
        val readTransaction = db.readableDatabase
        val cursor = readTransaction.rawQuery("select * from Farm", null)
        val getFarmsArray = ArrayList<Farm>()

        with(cursor){
            while(moveToNext()){
                val name = getString(getColumnIndexOrThrow("name"))
                val record = getString(getColumnIndexOrThrow("record"))
                val price = getFloat(getColumnIndexOrThrow("price"))
                val longitude = getFloat(getColumnIndexOrThrow("longitude"))
                val latitude = getFloat(getColumnIndexOrThrow("latitude"))

                val farm = Farm(name, record, price, longitude, latitude)
                getFarmsArray.add(farm)
                Log.i("Test", "Name: "+name+ " - Record: "+record+ " - Price: "+price+ " - Longitude: " +longitude+ " - Latitude: " +latitude)
            }
        }
        cursor.close()

        return getFarmsArray
    }

    fun updateFarm(farm: Farm){

    }

    fun deleteFarm(farm: Farm) {
//        Toast.makeText(context, "Farm that is going to be deleted: "+farm.name, Toast.LENGTH_LONG).show()
        Log.i("databaseResponse", "Farm that is going to be deleted: "+farm.name)
    }


}