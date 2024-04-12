package com.example.workforserialization

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.workforserialization.navigation.Navigation
import com.example.workforserialization.screens.StartScreen
import com.example.workforserialization.ui.theme.WorkForSerializationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkForSerializationTheme {
                // A surface container using the 'background' color from the theme
                val context = LocalContext.current
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    verifyStoragePermission(context, this)
                    StartScreen(this)
                }
            }
        }
    }

    //Permissions Check
    fun verifyStoragePermission(context: Context, activity: Activity) {
        val permission1 = ActivityCompat.checkSelfPermission(context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val permission2 = ActivityCompat.checkSelfPermission(context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (!Environment.isExternalStorageManager() && permission1 != PackageManager.PERMISSION_GRANTED
            && permission2 != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(activity, permissions,101)
        }
    }
}