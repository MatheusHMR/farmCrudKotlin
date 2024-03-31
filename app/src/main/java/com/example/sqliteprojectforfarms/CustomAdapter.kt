//package com.example.sqliteprojectforfarms
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ArrayAdapter
//import android.widget.TextView
//
//class CustomAdapter(context: Context, val arrayFarm: ArrayList<Farm>, selectedFarmsFromListView : HashSet<Int>) :  ArrayAdapter<Farm>(context, 0, arrayFarm){
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
//        val textView = view.findViewById<TextView>(android.R.id.text1)
//        textView.text = arrayFarm[position].toString()
//
//        if (selectedItems.contains(position)) {
//            textView.setBackgroundColor(Color.LTGRAY) // Selected item background color
//        } else {
//            textView.setBackgroundColor(Color.TRANSPARENT) // Default item background color
//        }
//
//        return view
//    }
//
//}