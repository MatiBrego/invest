package com.invest.currency.core

import java.time.LocalDate

class MockRateProvider(
    private val quotationRateMap: Map<Quotation, Double>,
    private val historicQuotationRateMap: Map<Quotation, Map<LocalDate, Double>>,
) : RateProvider {
    override fun getRateForQuotation(quotation: Quotation): Double {
        return quotationRateMap.getOrElse(quotation) {
            throw IllegalArgumentException("Quotation $quotation not found")
        }
    }

    override fun getHistoricRateForQuotation(
        quotation: Quotation,
        date: LocalDate,
    ): Double {
        val quotationDateMap =
            historicQuotationRateMap.getOrElse(quotation) {
                throw IllegalArgumentException("Quotation $quotation not found")
            }

        return quotationDateMap.getOrElse(date) {
            throw IllegalArgumentException("Quotation of $quotation for date $date not found")
        }
    }
}
