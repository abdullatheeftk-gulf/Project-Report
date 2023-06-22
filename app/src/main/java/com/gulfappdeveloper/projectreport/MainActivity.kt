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
import com.gulfappdeveloper.projectreport.domain.services.PdfService
import com.gulfappdeveloper.projectreport.navigation.RootNavGraph
import com.gulfappdeveloper.projectreport.root.CommonMemory
import com.gulfappdeveloper.projectreport.root.RootViewModel
import com.gulfappdeveloper.projectreport.share.pdf.customer_ledger_report.CustomerLedgerReportPdf
import com.gulfappdeveloper.projectreport.ui.theme.ProjectReportTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val rootViewModel by viewModels<RootViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)

        var list = mutableListOf<Int>()
        var pageNo = 1
        val originalList = mutableListOf<Int>()
        (1..161).forEach {
            originalList.add(it)
        }

        originalList.forEachIndexed { index, i ->
            Log.d(TAG, "start: $i")
            list.add(index + 1)
            if ((index + 1) == 39) {

                if (originalList.size == 39) {
                    list = list.dropLast(1).toMutableList()
                    createPage(list = list, pageNo)
                    list.clear()
                    list.add(index + 1)
                    pageNo++
                    createPage(list = list, pageNo)
                    list.clear()
                } else {

                    createPage(list = list, pageNo = pageNo)
                    list.clear()
                    pageNo++
                }
            }
            if ((index - 38) % 41 == 0 && (index - 38) != 0) {
                /*list = list.dropLast(1).toMutableList()
                createPage(list = list, pageNo)
                list.clear()
                list.add(index + 1)
                pageNo++*/
                if (originalList.size==index+1){
                    list = list.dropLast(1).toMutableList()
                    createPage(list = list, pageNo)
                    list.clear()
                    list.add(index + 1)
                    pageNo++
                    createPage(list = list, pageNo)
                    list.clear()
                }else{
                    createPage(list = list, pageNo = pageNo)
                    list.clear()
                    pageNo++
                }
            }
        }
        when (list.size) {
            in 1..39 -> {
                createPage(list, pageNo)
            }

            40 -> {
                createPage(list, pageNo)
                list.clear()
                pageNo++
                createPage(list, pageNo = pageNo)
            }

            else -> Unit
        }


        val deviceId =
            Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID) + "PQ"

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

    private fun createPage(list: List<Int>, pageNo: Int) {
        Log.e(TAG, "createPage: $list")
        Log.d(TAG, "createPage: $pageNo")
    }


}
