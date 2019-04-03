package com.etirps.zhu.leaguerewind

import android.view.MotionEvent
import android.view.View
import android.widget.Toast

class ToolController {


    init {

    }

    fun getCurrentTool() {

    }

    fun handleChampTool(v: View) {
        Toast.makeText(v.context, "champion tool", Toast.LENGTH_SHORT).show()
    }

    fun handleWardTool(v: View) {
        Toast.makeText(v.context, "ward tool", Toast.LENGTH_SHORT).show()
    }
}