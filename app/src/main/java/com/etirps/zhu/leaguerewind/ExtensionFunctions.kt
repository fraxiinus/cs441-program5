package com.etirps.zhu.leaguerewind

import android.content.Context
import android.content.res.Resources

/**
 * Return resource id number for given name
 */
fun Context.resIdByName(resIdName: String?, resType: String): Int {
    resIdName?.let {
        return resources.getIdentifier(it, resType, packageName)
    }
    throw Resources.NotFoundException()
}

/**
 * Return string with only letters, numbers, and no whitespace
 */
fun String.clean(): String {
    return Regex("[^A-Za-z0-9]").replace(this, "").toLowerCase()
}