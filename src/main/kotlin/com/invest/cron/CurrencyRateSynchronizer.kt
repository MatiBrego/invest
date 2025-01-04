package com.invest.cron

import com.invest.currency.core.Quotation
import com.invest.currency.persistence.QuotationEntity
import com.invest.currency.persistence.QuotationRepository
import com.invest.currency.provider.RemoteRateProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CurrencyRateSynchronizer
    @Autowired
    constructor(
        private val quotationRepository: QuotationRepository,
    ) {
        private val remoteRateProvider: RemoteRateProvider = RemoteRateProvider()

        @Scheduled(cron = "0 * * * * ?")
        fun syncYesterdaysRates() {
            val yesterday = LocalDate.now().minusDays(1)

            val newQuotationEntity =
                syncRateByDate(yesterday)

            quotationRepository.save(newQuotationEntity)
        }

        private fun syncRateByDate(date: LocalDate): QuotationEntity {
            val rate =
                remoteRateProvider.getHistoricRateForQuotation(
                    Quotation.CCL,
                    date,
                )

            val newQuotationEntity =
                QuotationEntity(
                    amount = rate,
                    quotation = Quotation.CCL,
                    date = date,
                )
            return newQuotationEntity
        }

        fun syncRatesForDateList(dates: List<LocalDate>) {
            for (date in dates) {
                this.syncRateByDate(date)
            }
        }
    }
