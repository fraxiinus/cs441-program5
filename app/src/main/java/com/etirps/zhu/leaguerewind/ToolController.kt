package com.etirps.zhu.leaguerewind

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast

class ToolController(val tokens: TokenImageManager, val activity: AppCompatActivity) {

    private var currentFragment: TokenSelectionFragment? = null

    fun getCurrentTool() {

    }

    fun handleChampTool(v: View) {
        showToolFragment(TokenSelectionFragment.newInstance("Champions", tokens.championTitles))
    }

    fun handleWardTool(v: View) {
        showToolFragment(TokenSelectionFragment.newInstance("Wards", tokens.wardTitles))
    }

    fun handleDrawTool(v: View) {
        Toast.makeText(v.context, "draw tool", Toast.LENGTH_SHORT).show()
    }

    private fun showToolFragment(fragment: TokenSelectionFragment) {
        if(fragment.arguments?.get("title") != currentFragment?.arguments?.get("title")) {
            if(currentFragment != null) {
                activity.supportFragmentManager.popBackStack()
                currentFragment = null
            }

            val ft = activity.supportFragmentManager.beginTransaction()
            ft.replace(R.id.placeholder, fragment)
            ft.addToBackStack(null)
            ft.commit()
            currentFragment = fragment
        } else {
            activity.supportFragmentManager.popBackStack()
            currentFragment = null
        }
    }

    private fun showSelectDialog(view: View, names: Array<String>): String {
        // Create content array
        val content = ArrayList<HashMap<String, String>>()
        // Each element is a map of name, to value
        for (name in names) {
            val hm = HashMap<String, String>()
            hm["list_name"] = name
            // Get the id (int) of the resource
            hm["list_img"] = view.context.resIdByName(name.clean(), "drawable").toString()
            content.add(hm)
        }

        // Key to tell adapter how to use content
        // list name goes to R.id.tv1
        // list img goes to R.id.iv1
        val fromMap = arrayOf("list_name", "list_img")
        val toMap = arrayOf(R.id.tv1, R.id.iv1)

        // Inflate the layout
        val layout = LayoutInflater.from(view.context).inflate(R.layout.select_dialog_layout, null)
        // Get the list view from the layout
        val list = layout.findViewById<ListView>(R.id.list_view)

        // Setup the adapter and give it to the list
        val arrayAdapter = SimpleAdapter(layout.context, content, R.layout.select_item_layout, fromMap, toMap.toIntArray())
        list.adapter = arrayAdapter

        // Build the dialog and show it
        val dialog = AlertDialog.Builder(view.context)
        dialog.setTitle("Choose Image")
        dialog.setView(layout)
        dialog.create()

        dialog.show()

        return ""
    }
}