package com.etirps.zhu.leaguerewind

import android.app.AlertDialog
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast

class ToolController(val tokens: TokenImageManager, val activity: AppCompatActivity) {

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