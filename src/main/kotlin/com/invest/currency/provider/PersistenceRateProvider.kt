package com.invest.currency.provider

import com.invest.currency.core.Quotation
import com.invest.currency.core.RateProvider
import com.invest.currency.persistence.QuotationRepository
import java.time.LocalDate

class PersistenceRateProvider(
    private val quotationRepository: QuotationRepository,
) : RateProvider {
    override fun getLastRateForQuotation(quotation: Quotation): Double {
        return quotationRepository.findFirstByQuotationOrderByDateDesc(quotation).amount
    }

    override fun getHistoricRateForQuotation(
        quotation: Quotation,
        date: LocalDate,
    ): Double {
        return quotationRepository.findByQuotationAndDate(quotation, date).amount
    }
}
