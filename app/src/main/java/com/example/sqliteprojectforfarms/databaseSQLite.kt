package com.example.sqliteprojectforfarms

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class databaseSQLite(context: Context): SQLiteOpenHelper(context, "myIFTMDatabase", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val tableName = "Farm"
        val farmName = "name"
        val farmRecord = "record"
        val farmValue = "price"
        val farmLatitude = "latitude"
        val farmLongitude = "longitude"
        val SQL_creation =
            "CREATE TABLE ${tableName} (" +
                    "${farmRecord} TEXT PRIMARY KEY," +
                    "${farmName} TEXT," +
                    "${farmValue} REAL," +
                    "${farmLatitude} REAL," +
                    "${farmLongitude} REAL)"
        db.execSQL(SQL_creation)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val SQL_exclusao = "DROP TABLE IF EXISTS Farm"
        db.execSQL(SQL_exclusao)
        onCreate(db)
    }

}