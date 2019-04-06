package com.etirps.zhu.leaguerewind

import android.app.Application
import android.graphics.Color

enum class DrawingModes { PEN, TOKEN }

class ApplicationData : Application() {

    // On tap, what should be drawn?
    var drawMode: DrawingModes = DrawingModes.PEN

    // Should we clear the map when we update?
    var clearFlag: Boolean = false

    // pen mode information
    var colorInt: Int = Color.BLACK
    var strokeWidth: Int = 8

    // token mode information
    var tokenIndex = 0
    var isEnemy = false
    var isWard = false

    var tokenImageManager: TokenImageManager = TokenImageManager()
}