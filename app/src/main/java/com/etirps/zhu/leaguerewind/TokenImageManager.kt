package com.etirps.zhu.leaguerewind

import android.content.ClipData
import android.content.Context
import android.content.res.Resources

class TokenImageManager {

    val championTitles = arrayOf("Ezreal", "Kayle", "Lee Sin", "Morgana", "Neeko", "Nidalee", "Thresh", "Vel'Koz", "Yasuo", "Zed")
    val wardTitles = arrayOf("Control", "Stealth", "Trinket")

    init {

    }

    /**
     * Given ward name, return filepath to ward token image
     * Returns empty string if champion name not found
     */
    fun getWardFilepath(name: String): String {
        if(!wardTitles.contains(name)) { return "" }
        val cleaned = name.clean()
        return "drawable/$cleaned.png"
    }

    /**
     * Given champion name, return filepath to champion token image
     * Returns empty string if champion name not found
     */
    fun getChampionFilepath(name: String): String {
        if(!championTitles.contains(name)) { return "" }
        val cleaned = name.clean()
        return "drawable/$cleaned.png"
    }
}