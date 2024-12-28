package com.invest.currency.core

import java.time.LocalDate

interface RateProvider {
    fun getLastRateForQuotation(quotation: Quotation): Double

    fun getHistoricRateForQuotation(
        quotation: Quotation,
        date: LocalDate,
    ): Double
}
