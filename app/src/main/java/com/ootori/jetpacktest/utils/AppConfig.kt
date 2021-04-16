package com.ootori.jetpacktest.utils

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.ootori.jetpacktest.App
import com.ootori.jetpacktest.model.Destination
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object AppConfig {

    var destConfig = HashMap<String, Destination>()
        get() {
            val type = object : TypeReference<HashMap<String, Destination>>() {}.type
            val map: HashMap<String, Destination>? =
                JSON.parseObject(parseFiles("destination.json"), type)
            if (!map.isNullOrEmpty()) {
                field.putAll(map)
            }
            return field
        }


    private fun parseFiles(fileName: String): String {
        val result = StringBuffer()
        var inputStream: InputStream? = null
        var reader: BufferedReader? = null
        try {
            inputStream = App.instance.assets.open(fileName)
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
