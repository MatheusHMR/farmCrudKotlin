package com.example.sqliteprojectforfarms.farmDAO

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.sqliteprojectforfarms.Farm
import com.example.sqliteprojectforfarms.databaseSQLite
import java.io.IOException
import java.sql.SQLException

class FarmDAO(context: Context) {

    var db: databaseSQLite

    init {
        this.db = databaseSQLite(context)
    }

    companion object {
        fun getInstance(context: Context): FarmDAO {
            return FarmDAO(context)
        }
    }

    fun createFarm(farm: Farm) {
        val insertionTransaction = db.writableDatabase
        insertionTransaction.beginTransaction()
        var farmContent = ContentValues().apply {
            put("name", farm.name)
            put("record", farm.record)
            put("price", farm.price.toString())
            put("latitude", farm.latitude.toString())
            put("longitude", farm.longitude.toString())
        }
        try {
            val insertionResult = insertionTransaction?.insert("farm", null, farmContent)
            insertionTransaction.setTransactionSuccessful()
            Log.i("databaseResponses", "Insertion: $insertionResult")
        } catch (e: SQLException) {
            Log.i("databaseResponses", "SQL Error .-.")
        } catch (e: IOException) {
            Log.i("databaseResponses", "Everything went wrong :(")
        } finally {
            insertionTransaction.endTransaction()
        }
    }

    fun createFarms(farms: ArrayList<Farm>) {
        val insertionTransaction = db.writableDatabase
        insertionTransaction.beginTransaction()
        try {
            for (farm in farms) {
                var farmContent = ContentValues().apply {
                    put("name", farm.name)
                    put("record", farm.record)
                    put("price", farm.price.toString())
                    put("latitude", farm.latitude.toString())
                    put("longitude", farm.longitude.toString())
                }
                insertionTransaction.insert("farm", null, farmContent)
            }
            insertionTransaction.setTransactionSuccessful()
        } catch (e: SQLException) {
            Log.i("databaseResponses", "SQL Error .-.")
        } catch (e: IOException) {
            Log.i("databaseResponses", "Everything went wrong :(")
        } finally {
            insertionTransaction.endTransaction()
        }
    }

    fun getAllFarms(): ArrayList<Farm> {
        val readTransaction = db.readableDatabase
        val cursor = readTransaction.rawQuery("select * from Farm", null)
        val getFarmsArray = ArrayList<Farm>()

        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow("name"))
                val record = getString(getColumnIndexOrThrow("record"))
                val price = getFloat(getColumnIndexOrThrow("price"))
                val longitude = getFloat(getColumnIndexOrThrow("longitude"))
                val latitude = getFloat(getColumnIndexOrThrow("latitude"))

                val farm = Farm(name, record, price, longitude, latitude)
                getFarmsArray.add(farm)
                Log.i(
                    "Test",
                    "Name: " + name + " - Record: " + record + " - Price: " + price + " - Longitude: " + longitude + " - Latitude: " + latitude
                )
            }
        }
        cursor.close()

        return getFarmsArray
    }

    fun updateFarmUsingFarm(oldFarm: Farm, newFarm: Farm) {
        val updateTransaction = db.writableDatabase
        updateTransaction.beginTransaction()
        val farmContent = ContentValues().apply {
            put("name", newFarm.name)
            put("record", newFarm.record)
            put("price", newFarm.price.toString())
            put("latitude", newFarm.latitude.toString())
            put("longitude", newFarm.longitude.toString())
        }
        try {
            val condition = "record LIKE '%${oldFarm.record}%'"
            val updateResult = updateTransaction.update("farm", farmContent, condition, null)
            updateTransaction.setTransactionSuccessful()
            Log.i("databaseResponses", "Update: $updateResult")
        } catch (e: SQLException) {
            Log.i("databaseResponses", "SQL Error .-.")
        } catch (e: IOException) {
            Log.i("databaseResponses", "Everything went wrong :(")
        } finally {
            updateTransaction.endTransaction()
        }
    }

    fun deleteFarms(farms: ArrayList<Farm>) {
//        Toast.makeText(context, "Farm that is going to be deleted: "+farm.name, Toast.LENGTH_LONG).show()
        val deleteTransaction = db.readableDatabase

        for (farm in farms) {
            deleteTransaction.beginTransaction()
            Log.i("databaseResponse", "Farm that is going to be deleted: " + farm.name)
            try {
                val condition = "record LIKE '%${farm.record}%'"
                deleteTransaction.delete("farm", condition, null)
                deleteTransaction.setTransactionSuccessful()
                Log.i("databaseResponse", "Farm deleted successfully!")
            }
            catch (e: SQLException) {
                Log.i("databaseResponse", "Couldn't delete the refered farm in the database!")
            } finally {
                deleteTransaction.endTransaction()
            }
        }
    }


}