package com.gulfappdeveloper.projectreport

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.gulfappdeveloper.projectreport.navigation.RootNavGraph
import com.gulfappdeveloper.projectreport.root.RootViewModel
import com.gulfappdeveloper.projectreport.ui.theme.ProjectReportTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val rootViewModel by viewModels<RootViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val path =
                Environment.getExternalStorageDirectory().path + "/Android/data/${packageName}/files"
            val directory = File(path)
            val files = directory.listFiles()
            files?.forEach {
                it.delete()
            }
        } catch (e: Exception) {
            e.message
        }


        //VW for this app only
        val deviceId =
            Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID) + "VW"

        setContent {
            ProjectReportTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navHostController = rememberNavController()

                    RootNavGraph(
                        navHostController = navHostController,
                        hideKeyboard = {
                            hideSoftKeyboard()
                        },
                        deviceId = deviceId,
                        rootViewModel = rootViewModel
                    )

                }
            }
        }

    }

    private fun hideSoftKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


}
