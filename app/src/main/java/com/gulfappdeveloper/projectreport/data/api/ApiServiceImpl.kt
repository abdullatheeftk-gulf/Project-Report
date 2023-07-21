package com.gulfappdeveloper.projectreport.data.api

import android.util.Log
import com.gulfappdeveloper.projectreport.domain.models.accounts.ExpenseLedgerReportResponse
import com.gulfappdeveloper.projectreport.domain.models.accounts.PaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.accounts.ReceiptResponse
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.general.Error
import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.domain.models.ip4.SeeIp
import com.gulfappdeveloper.projectreport.domain.models.ledger.GetCustomerForLedgerReportResponse
import com.gulfappdeveloper.projectreport.domain.models.ledger.LedgerReportResponse
import com.gulfappdeveloper.projectreport.domain.models.license.LicenseRequestBody
import com.gulfappdeveloper.projectreport.domain.models.license.LicenseResponse
import com.gulfappdeveloper.projectreport.domain.models.login_and_register.CompanyRegisterResponse
import com.gulfappdeveloper.projectreport.domain.models.login_and_register.LoginResponse
import com.gulfappdeveloper.projectreport.domain.models.purchase.PurchaseMastersResponse
import com.gulfappdeveloper.projectreport.domain.models.purchase.supplier_ledger_report.SupplierLedgerReportResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.SaleSummariesResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.SalesInvoiceResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.UserSalesResponse
import com.gulfappdeveloper.projectreport.domain.services.ApiService
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.ConnectException

private const val TAG = "ApiServiceImpl"

class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {
    override suspend fun getWelcomeMessage(url: String): Flow<GetDataFromRemote<String>> {
        return flow {
            emit(GetDataFromRemote.Loading)
            Log.i(TAG, "getWelcomeMessage: loading")
            try {
                val httpResponse = client.get(urlString = url)
                Log.d(TAG, "getWelcomeMessage: response completed")

                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.bodyAsText())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }


            } catch (e: ConnectTimeoutException) {
                // Log.e(TAG, " ConnectTimeoutException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                // Log.e(TAG, " NoTransformationFoundException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = e.message
                        )
                    )
                )
            }
        }
    }

    override suspend fun uniLicenseActivation(
        rioLabKey: String,
        url: String,
        licenseRequestBody: LicenseRequestBody
    ): Flow<GetDataFromRemote<LicenseResponse>> {
        return flow {
            try {
                val httpResponse = client.post(urlString = url) {
                    contentType(ContentType.Application.Json)
                    header("Authorization", rioLabKey)
                    setBody(licenseRequestBody)
                }

                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        var str = ""
                        try {
                            str = httpResponse.bodyAsText()
                        } catch (e: Exception) {
                            e.message
                        }

                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description + "-" + str
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {

                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {

                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {

                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getIp4Address(url: String): Flow<GetDataFromRemote<SeeIp>> {
        return flow {
            try {
                val httpResponse = client.get(urlString = url)
                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun registerCompany(url: String): Flow<GetDataFromRemote<CompanyRegisterResponse>> {
        return flow {
            emit(GetDataFromRemote.Loading)
            try {
                val httpResponse = client.get(urlString = url)
                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun login(url: String): Flow<GetDataFromRemote<LoginResponse>> {
        return flow {
            emit(GetDataFromRemote.Loading)
            try {
                val httpResponse = client.get(urlString = url)
                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getCustomerForLedger(url: String): Flow<GetDataFromRemote<List<GetCustomerForLedgerReportResponse>>> {
        Log.d("Query", "getCustomerForLedger: ")
        return flow {
            emit(GetDataFromRemote.Loading)
            try {
                val httpResponse = client.get(urlString = url)
                try {
                    val result = httpResponse.body<List<GetCustomerForLedgerReportResponse>>()
                    Log.i("Query", "getCustomerForLedger: $result")
                } catch (e: Exception) {
                    Log.e("Query", "getCustomerForLedger: $e")
                }
                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getCustomerLedgers(url: String): Flow<GetDataFromRemote<LedgerReportResponse>> {
        return flow {
            emit(GetDataFromRemote.Loading)
            try {
                val httpResponse = client.get(urlString = url)
                val result = httpResponse.bodyAsText()
                Log.e(TAG, "getCustomerLedgers: $result")
                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getCustomerPayment(url: String): Flow<GetDataFromRemote<List<CustomerPaymentResponse>>> {
        Log.d(TAG, "getCustomerPayment: test")
        return flow {
            emit(GetDataFromRemote.Loading)
            try {
                val httpResponse = client.get(urlString = url)
                val result = httpResponse.bodyAsText()
                Log.e(TAG, "getCustomerPayment: $result")
                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception ${e.cause}"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getSaleSummariesReport(url: String): Flow<GetDataFromRemote<List<SaleSummariesResponse>>> {
        return flow {
            emit(GetDataFromRemote.Loading)
            try {
                val httpResponse = client.get(urlString = url)
                //val result = httpResponse.bodyAsText()
                /*//Log.e(TAG, "getSaleSummariesReport: $result")
                val list:List<SaleSummariesResponse> = Json.decodeFromString(result)
                Log.w(TAG, "getSaleSummariesReport: $list", )*/
                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getSalesInvoiceReport(url: String): Flow<GetDataFromRemote<List<SalesInvoiceResponse>>> {
        return flow {
            emit(GetDataFromRemote.Loading)
            try {
                val httpResponse = client.get(urlString = url)
                val result = httpResponse.bodyAsText()
                Log.e(TAG, "getSalesInvoiceReport: $result")
                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                Log.e(TAG, "getSalesInvoiceReport: ${e.cause}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getUserSalesReport(url: String): Flow<GetDataFromRemote<List<UserSalesResponse>>> {
        return flow {
            emit(GetDataFromRemote.Loading)
            try {
                val httpResponse = client.get(urlString = url)
                val result = httpResponse.bodyAsText()
                Log.e(TAG, "getUserSalesReport: $result")
                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                Log.e(TAG, "getSalesInvoiceReport: ${e.cause}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getPosPaymentReport(url: String): Flow<GetDataFromRemote<List<PosPaymentResponse>>> {
        return flow {
            emit(GetDataFromRemote.Loading)
            try {
                val httpResponse = client.get(urlString = url)
                val result = httpResponse.bodyAsText()
                Log.e(TAG, "getPosPaymentReport: $result")
                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                Log.e(TAG, "getSalesInvoiceReport: ${e.cause}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getPurchaseMastersReport(url: String): Flow<GetDataFromRemote<List<PurchaseMastersResponse>>> {
        return flow {
            emit(GetDataFromRemote.Loading)
            try {
                val httpResponse = client.get(urlString = url)
                val result = httpResponse.bodyAsText()
                Log.e(TAG, "getPurchaseMastersReport: $result")
                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                Log.e(TAG, "getSalesInvoiceReport: ${e.cause}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getSupplierPurchaseReport(url: String): Flow<GetDataFromRemote<List<PurchaseMastersResponse>>> {
        return flow {
            emit(GetDataFromRemote.Loading)
            try {
                val httpResponse = client.get(urlString = url)
                val result = httpResponse.bodyAsText()
                Log.e(TAG, "getSupplierPurchaseReport: $result")
                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                Log.e(TAG, "getSalesInvoiceReport: ${e.cause}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getSupplierLedgerReport(url: String): Flow<GetDataFromRemote<SupplierLedgerReportResponse>> {
        return flow {
            emit(GetDataFromRemote.Loading)
            try {
                val httpResponse = client.get(urlString = url)
                val result = httpResponse.bodyAsText()
                Log.e(TAG, "getSupplierLedgerReport: $result")
                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                Log.e(TAG, "getSalesInvoiceReport: ${e.cause}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getExpenseLedgerReport(
        url: String,
        dateFrom: String,
        dateTo: String,
        expenseId: Int,
        companyId: Int
    ): Flow<GetDataFromRemote<ExpenseLedgerReportResponse>> {
        return flow {
            emit(GetDataFromRemote.Loading)
            try {
                val httpResponse = client.get(urlString = url){
                    url{
                        parameters.append(name="dateFrom", value = dateFrom)
                        parameters.append(name="dateTo", value = dateTo)
                        parameters.append(name = "expenseId", value = expenseId.toString())
                        parameters.append(name = "companyId",value = companyId.toString())
                    }
                }

                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getPaymentsReport(
        url: String,
        dateFrom: String,
        dateTo: String,
        companyId: Int
    ): Flow<GetDataFromRemote<List<PaymentResponse>>> {
        return flow {
            emit(GetDataFromRemote.Loading)
            try {
                val httpResponse = client.get(urlString = url){
                    url{
                        parameters.append(name="dateFrom", value = dateFrom)
                        parameters.append(name="dateTo", value = dateTo)
                        parameters.append(name = "companyId",value = companyId.toString())
                    }
                }

                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getReceiptReport(
        url: String,
        dateFrom: String,
        dateTo: String,
        companyId: Int
    ): Flow<GetDataFromRemote<List<ReceiptResponse>>> {
        return flow {
            emit(GetDataFromRemote.Loading)
            try {
                val httpResponse = client.get(urlString = url){
                    url{
                        parameters.append(name="dateFrom", value = dateFrom)
                        parameters.append(name="dateTo", value = dateTo)
                        parameters.append(name = "companyId",value = companyId.toString())
                    }
                }

                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }


}