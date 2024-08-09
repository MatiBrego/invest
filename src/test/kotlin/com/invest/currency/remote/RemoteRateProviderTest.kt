package com.invest.currency.remote

import com.invest.currency.core.Quotation
import java.time.LocalDate
import kotlin.test.Test

// @Ignore("This is for experimenting purposes only")
class RemoteRateProviderTest {
    @Test
    fun `001 provide today's rate for CLL`() {
        val remoteRateProvider = RemoteRateProvider()

        val result = remoteRateProvider.getRateForQuotation(Quotation.CCL)

        println(result)
    }

    @Test
    fun `002 provide today's rate for Blue`() {
        val remoteRateProvider = RemoteRateProvider()

        val result = remoteRateProvider.getRateForQuotation(Quotation.BLUE)

        println(result)
    }

    @Test
    fun `003 provide today's rate for Official`() {
        val remoteRateProvider = RemoteRateProvider()

        val result = remoteRateProvider.getRateForQuotation(Quotation.OFFICIAL)

        println(result)
    }

    @Test
    fun `004 provide today's rate for Mep`() {
        val remoteRateProvider = RemoteRateProvider()

        val result = remoteRateProvider.getRateForQuotation(Quotation.EXCHANGE)

        println(result)
    }

    @Test
    fun `005 provide 01-10-2023 rate for Blue`() {
        val remoteRateProvider = RemoteRateProvider()
        val date = LocalDate.of(2023, 10, 1)

        val result = remoteRateProvider.getHistoricRateForQuotation(Quotation.BLUE, date)

        println(result)
    }
}
