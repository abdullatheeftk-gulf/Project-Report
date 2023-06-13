package com.gulfappdeveloper.projectreport

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.gulfappdeveloper.projectreport.navigation.RootNavGraph
import com.gulfappdeveloper.projectreport.root.RootViewModel
import com.gulfappdeveloper.projectreport.ui.theme.ProjectReportTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootViewModel by viewModels<RootViewModel>()
        


        val deviceId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)+"PQ"

        setContent {
            ProjectReportTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navHostController = rememberNavController()

                    RootNavGraph(
                        navHostController =navHostController,
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
    
    fun myAdd(list:MutableList<Int>,value:Int){
        list.add(value)
    }
}
