package com.example.workforserialization.serializationClass

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.serializer
import net.mamoe.yamlkt.Yaml
import java.io.File

@ExperimentalSerializationApi
class SerializeYaml {

    inline fun <reified T> listInYaml(context: Context, list: List<T>, filePath: String){

        try {
            val yaml = Yaml()
            val yamlStr: String = yaml.encodeToString(ListSerializer(serializer<T>()),list)
            Log.d("convert in YAML", yamlStr)

            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filePath)
            file.printWriter().use { pw ->
                pw.print(yamlStr)
            }
            Toast.makeText(context, "Файл $filePath записан", Toast.LENGTH_SHORT).show()
        } catch (e: Exception){
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            Log.d("YAML write error", e.message.toString())
        }

    }

    inline fun <reified T> listFromYaml(context: Context, filePath: String): List<T>{
        var list: List<T> = listOf()
        try {
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filePath)
            var textInFile = ""
            file.bufferedReader().use {
                    br -> textInFile = br.readText()
            }
            val yaml = Yaml()
            list = yaml.decodeFromString<List<T>>(textInFile)
        } catch (e: Exception){
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
        return list
    }

}