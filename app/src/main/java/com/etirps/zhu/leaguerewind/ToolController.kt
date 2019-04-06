package com.etirps.zhu.leaguerewind

import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast

class ToolController(private val tokens: TokenImageManager, private val activity: AppCompatActivity) {

    private var currentFragment: Fragment? = null

    fun getCurrentTool() {
    }

    fun handleChampTool() {
        showToolFragment(TokenSelectionFragment.newInstance("Champions", tokens.championTitles))
    }

    fun handleWardTool() {
        showToolFragment(TokenSelectionFragment.newInstance("Wards", tokens.wardTitles))
    }

    fun handleDrawTool() {
        showToolFragment(PenToolFragment.newInstance("Pen Tool"))
    }

    fun handleClearButton() {
        showConfirmation()
    }

    private fun showConfirmation(): AlertDialog {
        val dialog = AlertDialog.Builder(activity)
        dialog.setTitle("Are you sure?")
        dialog.setMessage("Completely clear the drawing?")

        dialog.setPositiveButton("YES") { dialogInterface: DialogInterface, which: Int ->
            (activity.application as ApplicationData).clearFlag = true
            dialogInterface.dismiss()
            Toast.makeText(activity, "Tap anywhere on the map to clear", Toast.LENGTH_SHORT).show()
        }

        dialog.setNegativeButton("NO") { dialogInterface: DialogInterface, which: Int ->
            (activity.application as ApplicationData).clearFlag = false
            dialogInterface.dismiss()
        }

        val alertDialog = dialog.create()
        alertDialog.show()
        return alertDialog
    }

    private fun showToolFragment(fragment: Fragment) {
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
}