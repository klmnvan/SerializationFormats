package com.example.workforserialization.serializationClass

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.serializer
import java.io.File

class SerializeCsv {

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> listInCsv(context: Context, list: List<T>, filePath: String){

        try {
            val csv =  Csv { delimiter = ',' }
            val csvStr = csv.encodeToString(ListSerializer(serializer<T>()), list)
            Log.d("convert in CSV", csvStr)

            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filePath)
            file.printWriter().use { pw ->
                pw.print(csvStr)
            }
            Toast.makeText(context, "Файл $filePath записан", Toast.LENGTH_SHORT).show()
        } catch (e: Exception){
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            Log.d("Csv write error", e.message.toString())
        }

    }

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> listFromCsv(context: Context, filePath: String): List<T>{
        var list: List<T> = listOf()
        try {
            val csv =  Csv { delimiter = ',' }
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filePath)
            var textInFile = ""
            file.bufferedReader().use {
                br -> textInFile = br.readText()
            }
            list = csv.decodeFromString<List<T>>(textInFile)
        } catch (e: Exception){
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
        return list
    }

}