package com.gulfappdeveloper.projectreport.share.excel

import android.content.Context
import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.services.ExcelService

class ExcelServiceImpl(private val context: Context) : ExcelService {

    override suspend fun makeExcelForCustomerPaymentReport(
        list: List<CustomerPaymentResponse>,
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (Boolean, String?) -> Unit
    ) {
        CustomerPaymentReportExcel.writeExcelSheet(
            context = context,
            list = list,
            haveAnyError = haveAnyError,
            fromDate = fromDate,
            toDate = toDate,
            getUri = getUri

        )
    }
}