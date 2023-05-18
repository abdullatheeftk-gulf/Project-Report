package com.gulfappdeveloper.projectreport.data.api

import com.gulfappdeveloper.projectreport.domain.models.general.Error
import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.domain.services.ApiService
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.ConnectException

class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {
    override suspend fun getWelcomeMessage(url: String): Flow<GetDataFromRemote<String>> {
        return flow {
            emit(GetDataFromRemote.Loading)
            try {
                val httpResponse = client.get(urlString = url)

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





}