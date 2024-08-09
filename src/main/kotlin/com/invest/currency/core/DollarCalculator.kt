package com.invest.currency.core

import java.time.LocalDate

class DollarCalculator(
    private val rateProvider: RateProvider,
) {
    fun toPesos(
        amount: Double,
        dollarQuotation: Quotation,
    ): Double {
        return amount * rateProvider.getRateForQuotation(dollarQuotation)
    }

    fun toDollars(
        amount: Double,
        dollarQuotation: Quotation,
    ): Double {
        return amount / rateProvider.getRateForQuotation(dollarQuotation)
    }

    fun toHistoricPesos(
        amount: Double,
        quotation: Quotation,
        date: LocalDate,
    ): Double {
        return amount * rateProvider.getHistoricRateForQuotation(quotation, date)
    }

    fun toHistoricDollar(
        amount: Double,
        quotation: Quotation,
        date: LocalDate,
    ): Double {
        return amount / rateProvider.getHistoricRateForQuotation(quotation, date)
    }
}
