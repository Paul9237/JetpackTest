package com.ootori.jetpacktest.utils

import com.google.gson.Gson
import java.lang.StringBuilder
import java.lang.reflect.Type
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun String.md5(): String {
    if (this.isEmpty()) {
        return ""
    }
    val sb = StringBuilder()
    try {
        val md5 = MessageDigest.getInstance("MD5")
        val byteData = md5.digest(this.toByteArray())
        byteData.forEach {
            var temp = Integer.toHexString(it.toInt() and 0xff)
            if (temp.length == 1) {
                temp = "0$temp"
            }
            sb.append(temp)
        }
    } catch (e: NoSuchAlgorithmException) {
    }
    return sb.toString()
}

fun <T> String?.fromJson(t: Class<T>): T? {
    return try {
        Gson().fromJson(this, t)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun <T> String?.fromJson(t: Type): T? {
    return try {
        Gson().fromJson(this, t)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Any?.toJson(): String? {
    return if (this == null) {
        null
    } else {
        try {
            Gson().toJson(this)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
