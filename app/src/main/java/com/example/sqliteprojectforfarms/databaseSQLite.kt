package com.example.sqliteprojectforfarms

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class databaseSQLite(context: Context): SQLiteOpenHelper(context, "myIFTMDatabase", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val tableName = "Farm"
        val farmName = "farm_name"
        val farmRegister = "farm_register"
        val farmValue = "far_value"
        val farmLatitude = "farm_latitude"
        val farmLongitude = "farm_longitude"
        val SQL_creation =
                    "CREATE TABLE ${tableName} (" +
                    "${farmRegister} INTEGER PRIMARY KEY," +
                    "${farmName} TEXT," +
                    "${email} TEXT, " +
                            "${}"
        db.execSQL(SQL_criacao)
    }

}