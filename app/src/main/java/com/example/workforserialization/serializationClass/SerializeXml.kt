package com.example.workforserialization.serializationClass

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.serializer
import nl.adaptivity.xmlutil.serialization.XML
import java.io.File

@ExperimentalSerializationApi
class SerializeXml {

    inline fun <reified T> listInXml(context: Context, list: List<T>, filePath: String){

        try {
            val xmlStr: String = XML
                .encodeToString(
                    ListSerializer(serializer<T>()),
                    list
                )
            Log.d("convert in XML", xmlStr)

            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filePath)
            file.printWriter().use { pw ->
                pw.print(xmlStr)
            }
            Toast.makeText(context, "Файл $filePath записан", Toast.LENGTH_SHORT).show()
        } catch (e: Exception){
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            Log.d("XML write error", e.message.toString())
        }

    }

    inline fun <reified T> listFromXml(context: Context, filePath: String): List<T>{
        var list: List<T> = listOf()
        try {
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filePath)
            var textInFile = ""
            file.bufferedReader().use {
                    br -> textInFile = br.readText()
            }
            list = XML.decodeFromString<List<T>>(textInFile)
        } catch (e: Exception){
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
        return list
    }

}