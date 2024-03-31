package com.example.sqliteprojectforfarms

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.example.sqliteprojectforfarms.farmDAO.FarmDAO
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var et_farm_name: EditText
    lateinit var et_farm_record: EditText
    lateinit var et_longitude: EditText
    lateinit var et_latitude: EditText
    lateinit var et_price: EditText
    lateinit var bt_create: Button
    lateinit var bt_update: Button
    lateinit var bt_delete: Button
    lateinit var lv_farms: ListView
    val selectedFarmsFromListView = ArrayList<Farm>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_farm_name = findViewById(R.id.ET_farm_name)
        et_farm_record = findViewById(R.id.ET_farm_record)
        et_longitude = findViewById(R.id.ET_longitude)
        et_latitude = findViewById(R.id.ET_latitude)
        et_price = findViewById(R.id.ET_valor)
        bt_create = findViewById(R.id.BT_create)
        bt_delete = findViewById(R.id.BT_delete)
        bt_update = findViewById(R.id.BT_update)
        lv_farms = findViewById(R.id.LV_farms)

        bt_delete.isEnabled = false
        bt_update.isEnabled = false


//        var farmArray = ArrayList<Farm>()
        val farmDAO = FarmDAO(this)
        val farmArray = farmDAO.getAllFarms()
//        val testAdapter = CustomAdapter(this, farmArray, selectedFarmsFromListView)
        val farmAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, farmArray)
        lv_farms.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        lv_farms.adapter = farmAdapter

        lv_farms.setOnItemClickListener { _, _, position, _ ->

            //Setting the List View to be able to:
            //Perceive which items were clicked and check them
            //Fill the edit text fields with the information
            val checked = lv_farms.isItemChecked(position)
            if(checked){
                selectedFarmsFromListView.add(farmArray[position])
                lv_farms.getChildAt(position).setBackgroundColor(Color.LTGRAY)
            } else{
                selectedFarmsFromListView.remove(farmArray[position])
                lv_farms.getChildAt(position).setBackgroundColor(Color.TRANSPARENT)
            }
            try{
                displayFarmOnTextFields(selectedFarmsFromListView.last())
            } catch (e: NoSuchElementException){
                clearEditTextFields()
            }
            bt_delete.isEnabled = selectedFarmsFromListView.size >= 1 //If equal or greater than one, you can delete the element
        }

        bt_create.setOnClickListener {
            if (!validateInputs()) { //Se o input de dados foi errado, solicitar correção.
                Toast.makeText(this, "Fix the inserted data!", Toast.LENGTH_LONG).show()
                Log.i("Test", "Error in the user inputs")
            } else {
                var farm: Farm = Farm(
                    et_farm_name.text.toString(),
                    et_farm_record.text.toString(),
                    et_longitude.text.toString().toFloat(),
                    et_latitude.text.toString().toFloat(),
                    et_price.text.toString().toFloat(),
                )
//                applicationContext
                var creationResult : Boolean = false

                try {
                    farmDAO.createFarm(farm)
                    creationResult = true
                } catch(error : IOException) {
                    Toast.makeText(this, "Error while inserting into the database!", Toast.LENGTH_LONG).show()
                    Log.i("Error", "Error while inserting into the database!")
                }

                if(!creationResult){
                    Log.i("Error", "Couldn't insert the solicited farm into the database!")
                } else {
                    Toast.makeText(this, "Farm added to the database successfully! :)", Toast.LENGTH_LONG).show()
                    farmArray.add(farm)
                    clearEditTextFields()
                    lv_farms.adapter = farmAdapter
                }
            }
        }

        bt_delete.setOnClickListener {

                farmDAO.deleteFarms(selectedFarmsFromListView)
                selectedFarmsFromListView.clear()
                lv_farms.clearChoices()
                farmAdapter.notifyDataSetChanged()
                bt_delete.isEnabled = false

//                val clickedFarm = farmAdapter.getItem(lv_farms.checkedItemPosition)
//                if (clickedFarm != null) {
//                    Toast.makeText(this, "Farm Selected: "+clickedFarm.name, Toast.LENGTH_LONG).show()
//                    farmDAO.deleteFarm(clickedFarm)
//                } else {
//                    Toast.makeText(this, "Farm not encountered", Toast.LENGTH_LONG).show()
//                }
        }
    }
    fun validateInputs(): Boolean {
        return (et_farm_name.text.isNotBlank() && et_farm_record.text.isNotBlank() &&
                et_longitude.text.isNotBlank() && et_latitude.text.isNotBlank() && et_price.text.isNotBlank())
    }
    fun clearEditTextFields(){
        et_farm_name.text.clear()
        et_farm_record.text.clear()
        et_longitude.text.clear()
        et_latitude.text.clear()
        et_price.text.clear()
    }
//    fun unMarksContent(position : Int){
//        //Uncheck the selected item
//        lv_farms.setItemChecked(position, false)
//        lv_farms.getChildAt(position).setBackgroundColor(Color.TRANSPARENT) //Makes the color transparent again
//    }

    fun displayFarmOnTextFields(selectedFarm : Farm){
        et_farm_name.setText(selectedFarm.name)
        et_farm_record.setText(selectedFarm.record)
        et_price.setText(selectedFarm.price.toString())
        et_longitude.setText(selectedFarm.longitude.toString())
        et_latitude.setText(selectedFarm.latitude.toString())
    }
}