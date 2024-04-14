package com.example.workforserialization.serializationClass

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.io.File

class SerializeJson {

    inline fun <reified T> listInJson(context: Context, list: List<T>, filePath: String){

        try {
            val jsonStr: String = Json.encodeToString(ListSerializer(serializer<T>()), list)
            Log.d("convert in JSON", jsonStr)

            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filePath)
            file.printWriter().use { pw ->
                    pw.print(jsonStr)
            }
            Toast.makeText(context, "Файл $filePath записан", Toast.LENGTH_SHORT).show()
        } catch (e: Exception){
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            Log.d("Json write error", e.message.toString())
        }

    }

    inline fun <reified T> listFromJson(context: Context, filePath: String): List<T>{
        var list: List<T> = listOf()
        try {
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filePath)
            var textInFile = ""
            file.bufferedReader().use {
                br -> textInFile = br.readText()
            }
            list = Json.decodeFromString<List<T>>(textInFile)
        } catch (e: Exception){
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
        return list
    }

}