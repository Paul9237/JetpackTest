package com.ootori.jetpacktest.utils

import android.util.Log
import com.google.gson.reflect.TypeToken
import com.ootori.jetpacktest.App
import com.ootori.jetpacktest.model.BottomBar
import com.ootori.jetpacktest.model.Destination
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object AppConfig {

    var destConfig: HashMap<String, Destination>? = null
        get() {
            if (field != null) {
                return field
            }
            val type = object : TypeToken<HashMap<String, Destination>>() {}.type
            field = parseFiles("destination.json").fromJson(type)
            Log.d("honoka", "destination: ${field.toString()}")
            return field
        }

    var bottomBar: BottomBar? = null
        get() {
            if (field != null) {
                return field
            }
            field = parseFiles("main_tabs_config.json").fromJson(BottomBar::class.java)
            Log.d("honoka", "BottomBar: ${field.toString()}")
            return field
        }


    private fun parseFiles(fileName: String): String {
        val result = StringBuffer()
        var inputStream: InputStream? = null
        var reader: BufferedReader? = null
        try {
            inputStream = App.instance.resources.assets.open(fileName)
            reader = BufferedReader(InputStreamReader(inputStream))
            reader.readLines().forEach {
                result.append(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                reader?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return result.toString()
    }
}
