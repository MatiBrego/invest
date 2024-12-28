package com.invest.currency.provider

import com.invest.App
import com.invest.currency.core.Quotation
import com.invest.currency.persistence.QuotationEntity
import com.invest.currency.persistence.QuotationRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
import kotlin.test.assertEquals

@ActiveProfiles("test")
@SpringBootTest(classes = [App::class])
class PersistenceRateProviderTest
    @Autowired
    constructor(
        private val quotationRepository: QuotationRepository,
    ) {
        private final val date1 = LocalDate.of(2002, 10, 1)
        private final val date2 = LocalDate.of(2002, 10, 2)

        val cclQuotation1 =
            QuotationEntity(
                amount = 10.0,
                quotation = Quotation.CCL,
                date = date1,
            )

        val cclQuotation2 =
            QuotationEntity(
                amount = 20.0,
                quotation = Quotation.CCL,
                date = date2,
            )

        val blueQuotation1 =
            QuotationEntity(
                amount = 11.0,
                quotation = Quotation.BLUE,
                date = date1,
            )

        val blueQuotation2 =
            QuotationEntity(
                amount = 21.0,
                quotation = Quotation.BLUE,
                date = date2,
            )

        val dbRateProvider = PersistenceRateProvider(quotationRepository)

        @BeforeEach
        fun setup() {
            quotationRepository.deleteAll()
        }

        @Test
        fun `001 get last rate for quotation from db when only one rate exists`() {
            val saved = quotationRepository.save(cclQuotation1)

            val retrievedAmount = dbRateProvider.getLastRateForQuotation(Quotation.CCL)

            assertEquals(saved.amount, retrievedAmount)
        }

        @Test
        fun `002 get last rate for quotation from db when two rates exist`() {
            quotationRepository.save(cclQuotation1)
            val saved2 = quotationRepository.save(cclQuotation2)

            val retrievedAmount = dbRateProvider.getLastRateForQuotation(Quotation.CCL)

            assertEquals(saved2.amount, retrievedAmount)
        }

        @Test
        fun `003 get last rate for quotation should return value of the right quotation`() {
            val savedBlue = quotationRepository.save(blueQuotation1)
            quotationRepository.save(cclQuotation2)

            val retrievedAmount = dbRateProvider.getLastRateForQuotation(Quotation.BLUE)

            assertEquals(savedBlue.amount, retrievedAmount)
        }

        @Test
        fun `004 get historic rate for right quotation when multiple dates and quotations`() {
            quotationRepository.save(cclQuotation1)
            quotationRepository.save(cclQuotation2)
            val savedBlue1 = quotationRepository.save(blueQuotation1)
            quotationRepository.save(blueQuotation2)

            val retrievedAmount = dbRateProvider.getHistoricRateForQuotation(Quotation.BLUE, savedBlue1.date)

            assertEquals(savedBlue1.amount, retrievedAmount)
        }
    }
