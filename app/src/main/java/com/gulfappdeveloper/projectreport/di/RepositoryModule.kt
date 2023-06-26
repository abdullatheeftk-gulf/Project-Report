package com.gulfappdeveloper.projectreport.di

import com.gulfappdeveloper.projectreport.repositories.ApiRepository
import com.gulfappdeveloper.projectreport.repositories.DataStoreRepository
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository
import com.gulfappdeveloper.projectreport.repositories.RoomDatabaseRepository
import com.gulfappdeveloper.projectreport.usecases.UseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.accounts.ExpenseLedgerReportUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.accounts.GetPaymentsReportUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.accounts.GetReceiptReportUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.customer_payment.GetCustomerPaymentUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.ledger.GetCustomerForLedgerUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.ledger.GetCustomerLedgers
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.license.GetIP4AddressUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.license.UniLicenseActivationUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.purchase.PurchaseMastersReportUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.purchase.SupplierLedgerReportUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.purchase.SupplierPurchaseReportUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.register_and_login_use_cases.LoginUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.register_and_login_use_cases.RegisterCompanyUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.sales.GetPosPaymentReportUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.sales.GetSalesInvoiceReportUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.sales.GetSalesSummariesReportUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.sales.GetUserSalesReportUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.welcome.WelcomeMessageUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.company_data_use_case.ReadCompanyDataUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.company_data_use_case.SaveCompanyDataUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.device_id_use_cases.ReadDeviceIdUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.device_id_use_cases.SaveDeviceIdUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.ip_address_use_cases.ReadIpAddressUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.ip_address_use_cases.SaveIpAddressUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.operation_counter_use_cases.ReadOperationCountUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.operation_counter_use_cases.UpdateOperationCountUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.uni_license_use_cases.ReadUniLicenseUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.uni_license_use_cases.SaveUniLicenseUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.user_name_use_case.ReadUserNameUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.user_name_use_case.SaveUserNameUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.customer_ledger_report.ExcelMakerCustomerLedgerReportUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.customer_ledger_report.PdfMakerCustomerLedgerReportUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.customer_payment_report.ExcelMakerUseCaseForCustomerPaymentReport
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.customer_payment_report.PdfMakerUseCaseForCustomerPaymentReport
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.expense_lesdger_report_use_case.ExcelMakerExpenseLedgerReportUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.expense_lesdger_report_use_case.PdfMakerExpenseLedgerReportUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.payments_report.MakeExcelForPaymentReportUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.payments_report.MakePdfForPaymentsReportUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.pos_payment_report.ExcelMakerUseCaseForPosPaymentReport
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.pos_payment_report.PdfMakerUseCaseForPosPaymentReport
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.receipts_report.MakeExcelForReceiptReportUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.receipts_report.MakePdfForReceiptReportUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.sale_summaries_report.ExcelMakerSaleSummariesReportUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.sale_summaries_report.PdfMakerSaleSummariesReportUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.sales_invoice_report.ExcelMakerSalesInvoiceReportUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.sales_invoice_report.PdfMakerSalesInvoiceReportUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.supplier_ledger_report.ExcelMakerForSupplierLedgerReportUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.supplier_ledger_report.PdfMakerSupplierLedgerReportUseCase
import com.gulfappdeveloper.projectreport.usecases.room_use_case.GetAllLocalCompanyData
import com.gulfappdeveloper.projectreport.usecases.room_use_case.RoomInsertDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUseCase(
        apiRepository: ApiRepository,
        dataStoreRepository: DataStoreRepository,
        roomDatabaseRepository: RoomDatabaseRepository,
        pdfExcelRepository: PdfExcelRepository
    ) = UseCase(
        welcomeMessageUseCase = WelcomeMessageUseCase(apiRepository = apiRepository),
        uniLicenseActivationUseCase = UniLicenseActivationUseCase(apiRepository = apiRepository),
        getIP4AddressUseCase = GetIP4AddressUseCase(apiRepository = apiRepository),

        registerCompanyUseCase = RegisterCompanyUseCase(apiRepository = apiRepository),

        loginUseCase = LoginUseCase(apiRepository = apiRepository),


        // Data store repository
        saveIpAddressUseCase = SaveIpAddressUseCase(dataStoreRepository = dataStoreRepository),
        readIpAddressUseCase = ReadIpAddressUseCase(dataStoreRepository = dataStoreRepository),

        saveDeviceIdUseCase = SaveDeviceIdUseCase(dataStoreRepository = dataStoreRepository),
        readDeviceIdUseCase = ReadDeviceIdUseCase(dataStoreRepository = dataStoreRepository),

        updateOperationCountUseCase = UpdateOperationCountUseCase(dataStoreRepository = dataStoreRepository),
        readOperationCountUseCase = ReadOperationCountUseCase(dataStoreRepository = dataStoreRepository),

        saveUniLicenseUseCase = SaveUniLicenseUseCase(dataStoreRepository = dataStoreRepository),
        readUniLicenseUseCase = ReadUniLicenseUseCase(dataStoreRepository = dataStoreRepository),

        saveCompanyDataUseCase = SaveCompanyDataUseCase(dataStoreRepository = dataStoreRepository),
        readCompanyDataUseCase = ReadCompanyDataUseCase(dataStoreRepository = dataStoreRepository),

        saveUserNameUseCase = SaveUserNameUseCase(dataStoreRepository = dataStoreRepository),
        readUserNameUseCase = ReadUserNameUseCase(dataStoreRepository = dataStoreRepository),

        // Room
        roomInsertDataUseCase = RoomInsertDataUseCase(roomDatabaseRepository = roomDatabaseRepository),
        getAllLocalCompanyData = GetAllLocalCompanyData(roomDatabaseRepository = roomDatabaseRepository),

        // Ledger
        getCustomerForLedgerUseCase = GetCustomerForLedgerUseCase(apiRepository = apiRepository),
        getCustomerLedgers = GetCustomerLedgers(apiRepository = apiRepository),

        // Customer payment
        getCustomerPaymentUseCase = GetCustomerPaymentUseCase(apiRepository = apiRepository),

        // Sales
        getSalesInvoiceReportUseCase = GetSalesInvoiceReportUseCase(apiRepository = apiRepository),
        getSalesSummariesReportUseCase = GetSalesSummariesReportUseCase(apiRepository = apiRepository),
        getUserSalesReportUseCase = GetUserSalesReportUseCase(apiRepository = apiRepository),
        getPosPaymentReportUseCase = GetPosPaymentReportUseCase(apiRepository = apiRepository),

        pdfMakerUseCaseForCustomerPaymentReport = PdfMakerUseCaseForCustomerPaymentReport(
            pdfExcelRepository = pdfExcelRepository
        ),
        excelMakerUseCaseForCustomerPaymentReport = ExcelMakerUseCaseForCustomerPaymentReport(
            pdfExcelRepository = pdfExcelRepository
        ),

        pdfMakerUseCaseForPosPaymentReport = PdfMakerUseCaseForPosPaymentReport(pdfExcelRepository = pdfExcelRepository),
        excelMakerUseCasePosPaymentReportUseCase = ExcelMakerUseCaseForPosPaymentReport(
            pdfExcelRepository = pdfExcelRepository
        ),

        pdfMakerCustomerLedgerReportUseCase = PdfMakerCustomerLedgerReportUseCase(pdfExcelRepository = pdfExcelRepository),
        excelMakerCustomerLedgerReportUseCase = ExcelMakerCustomerLedgerReportUseCase(
            pdfExcelRepository = pdfExcelRepository
        ),

        pdfMakerSupplierLedgerReportUseCase = PdfMakerSupplierLedgerReportUseCase(pdfExcelRepository = pdfExcelRepository),
        excelMakerForSupplierLedgerReportUseCase = ExcelMakerForSupplierLedgerReportUseCase(
            pdfExcelRepository = pdfExcelRepository
        ),

        purchaseMastersReportUseCase = PurchaseMastersReportUseCase(apiRepository = apiRepository),
        supplierPurchaseReportUseCase = SupplierPurchaseReportUseCase(apiRepository = apiRepository),
        supplierLedgerReportUseCase = SupplierLedgerReportUseCase(apiRepository = apiRepository),

        pdfMakerSalesInvoiceReportUseCase = PdfMakerSalesInvoiceReportUseCase(pdfExcelRepository = pdfExcelRepository),
        excelMakerSalesInvoiceReportUseCase = ExcelMakerSalesInvoiceReportUseCase(pdfExcelRepository = pdfExcelRepository),

        pdfMakerSaleSummariesReportUseCase = PdfMakerSaleSummariesReportUseCase(pdfExcelRepository = pdfExcelRepository),
        excelMakerSaleSummariesReportUseCase = ExcelMakerSaleSummariesReportUseCase(
            pdfExcelRepository = pdfExcelRepository
        ),
        expenseLedgerReportUseCase = ExpenseLedgerReportUseCase(apiRepository = apiRepository),
        excelMakerExpenseLedgerReportUseCase = ExcelMakerExpenseLedgerReportUseCase(
            pdfExcelRepository = pdfExcelRepository
        ),
        pdfMakerExpenseLedgerReportUseCase = PdfMakerExpenseLedgerReportUseCase(pdfExcelRepository = pdfExcelRepository),

        getPaymentsReportUseCase = GetPaymentsReportUseCase(apiRepository = apiRepository),
        getReceiptReportUseCase = GetReceiptReportUseCase(apiRepository = apiRepository),

        makePdfForPaymentsReportUseCase = MakePdfForPaymentsReportUseCase(pdfExcelRepository = pdfExcelRepository),
        makeExcelForPaymentReportUseCase = MakeExcelForPaymentReportUseCase(pdfExcelRepository = pdfExcelRepository),

        makePdfForReceiptReportUseCase = MakePdfForReceiptReportUseCase(pdfExcelRepository = pdfExcelRepository),
        makeExcelForReceiptReportUseCase = MakeExcelForReceiptReportUseCase(pdfExcelRepository = pdfExcelRepository)


    )
}