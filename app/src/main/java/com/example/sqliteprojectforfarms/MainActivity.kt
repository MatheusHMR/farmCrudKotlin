package com.example.sqliteprojectforfarms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    lateinit var et_farm_name : EditText
    lateinit var et_farm_register : EditText
    lateinit var et_longitude : EditText
    lateinit var et_latitude : EditText
    lateinit var et_price : EditText
    lateinit var bt_create : Button
    lateinit var bt_update : Button
    lateinit var bt_delete : Button
    lateinit var lv_farms : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_farm_name = findViewById(R.id.ET_farm_name)
        et_farm_register = findViewById(R.id.ET_farm_register)
        et_longitude = findViewById(R.id.ET_longitude)
        et_latitude = findViewById(R.id.ET_latitude)
        et_price = findViewById(R.id.ET_valor)
        bt_create = findViewById(R.id.BT_create)
        bt_delete = findViewById(R.id.BT_delete)
        bt_update = findViewById(R.id.BT_update)


        bt_create.setOnClickListener{
            if(et_farm_name.text.isNotBlank() && et_farm_register.text.isNotBlank() && et)
        }
    }
}