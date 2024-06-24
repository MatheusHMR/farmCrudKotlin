package com.example.sqliteprojectforfarms

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.children
import backupView.backupView
import com.example.sqliteprojectforfarms.farmDAO.FarmDAO

class MainActivity : AppCompatActivity() {
    lateinit var et_farm_name: EditText
    lateinit var et_farm_record: EditText
    lateinit var et_longitude: EditText
    lateinit var et_latitude: EditText
    lateinit var et_price: EditText
    lateinit var bt_create: Button
    lateinit var bt_update: Button
    lateinit var bt_delete: Button
    lateinit var bt_backup: Button
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
        bt_backup = findViewById(R.id.BT_backup_view)
        lv_farms = findViewById(R.id.LV_farms)
        val intent_backup = Intent(this, backupView::class.java)

        bt_delete.isEnabled = false
        bt_update.isEnabled = false


        val farmDAO = FarmDAO(this)
        val farmArray = farmDAO.getAllFarms()

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
            bt_update.isEnabled = selectedFarmsFromListView.size == 1
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
                farmDAO.createFarm(farm)
                farmArray.add(farm)
                clearEditTextFields()
                lv_farms.adapter = farmAdapter
            }
        }

        bt_update.setOnClickListener{
            val farmDataToUpdate = Farm(
                et_farm_name.text.toString(),
                et_farm_record.text.toString(),
                et_price.text.toString().toFloat(),
                et_longitude.text.toString().toFloat(),
                et_latitude.text.toString().toFloat()
            )
            var oldFarm = selectedFarmsFromListView.last()
            Log.i("Test", "Received old farm: ${oldFarm.toString2()}")
            val response = validateUpdate(oldFarm,farmDataToUpdate)
            Log.i("Test", "Response: $response")
            if(response == ""){
                farmDAO.updateFarmUsingFarm(selectedFarmsFromListView[0], farmDataToUpdate)
                validateUpdate(oldFarm, farmDataToUpdate)
//                oldFarm = farmDataToUpdate
                oldFarm.name = farmDataToUpdate.name
                oldFarm.record = farmDataToUpdate.record
                oldFarm.price = farmDataToUpdate.price
                oldFarm.longitude = farmDataToUpdate.longitude
                oldFarm.latitude = farmDataToUpdate.latitude
                lv_farms.clearChoices()
                selectedFarmsFromListView.clear()
                farmAdapter.notifyDataSetChanged()
                bt_update.isEnabled = false
                bt_delete.isEnabled = false
                clearEditTextFields()
                lv_farms.getChildAt(farmAdapter.getPosition(oldFarm)).setBackgroundColor(Color.TRANSPARENT)
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("ATTENTION!!")
                builder.setMessage(response)
                builder.setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }
        }

        bt_delete.setOnClickListener {
            lv_farms.clearChoices()
            lv_farms.children.forEach{ farm ->
                farm.setBackgroundColor(Color.TRANSPARENT)
            }
            farmDAO.deleteFarms(selectedFarmsFromListView)
            farmArray.removeAll(selectedFarmsFromListView.toSet())
            selectedFarmsFromListView.clear()
            farmAdapter.notifyDataSetChanged()
            bt_delete.isEnabled = false
            bt_update.isEnabled = false
            clearEditTextFields()
        }

        bt_backup.setOnClickListener {
            startActivity(intent_backup)
        }
    }
    fun validateInputs(): Boolean {
        return (et_farm_name.text.isNotBlank() && et_farm_record.text.isNotBlank() &&
                et_longitude.text.isNotBlank() && et_latitude.text.isNotBlank() && et_price.text.isNotBlank())
    }

    fun validateUpdate(oldFarm : Farm, newFarm : Farm) : String {
        var response = ""
        if(oldFarm.name == newFarm.name && oldFarm.record == newFarm.record &&
            oldFarm.latitude == newFarm.latitude && oldFarm.longitude == newFarm.longitude &&
            oldFarm.price == newFarm.price){

            response = "You are updating with the same data that already exists!" +
                    " Are you sure you want to do that?"
            Log.i("Test", "All data is cloned")
        } else {
            Log.i("Test", "Passed the validation")
        }
        Log.i("Test", "Entered Validation, ${oldFarm.toString2()}")
        Log.i("Test", newFarm.toString2())
        return response
    }
    fun clearEditTextFields(){
        et_farm_name.text.clear()
        et_farm_record.text.clear()
        et_longitude.text.clear()
        et_latitude.text.clear()
        et_price.text.clear()
    }
    fun displayFarmOnTextFields(selectedFarm : Farm){
        et_farm_name.setText(selectedFarm.name)
        et_farm_record.setText(selectedFarm.record)
        et_price.setText(selectedFarm.price.toString())
        et_longitude.setText(selectedFarm.longitude.toString())
        et_latitude.setText(selectedFarm.latitude.toString())
    }
    fun insertingExamples(sampleSize : Int, startingPoint: Int) : ArrayList<Farm>{
        val examplesArray = ArrayList<Farm>()
        for(i in startingPoint until sampleSize + 1){
            val farmIterator = Farm(
                "farm${i}",
                "name${i}",
                i.toFloat()*(i.toFloat() - 1),
                i.toFloat()*(i.toFloat() - 2),
                i.toFloat()*(i.toFloat() + 3)
            )
            examplesArray.add(farmIterator)
        }
        return examplesArray
    }
    //This method belongs to this view
    fun buttonBackup(view : View){
//        startActivity(intent(this, BackupView::class.java))
    }

//            GenyMotion
}