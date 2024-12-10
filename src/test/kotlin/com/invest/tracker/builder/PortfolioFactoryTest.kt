package com.invest.tracker.builder

import com.invest.currency.core.DollarCalculator
import com.invest.currency.core.Dollars
import com.invest.currency.core.MockRateProvider
import com.invest.currency.core.Quotation
import com.invest.tracker.core.instrument.Stock
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class PortfolioFactoryTest {
    private val dollarCCLRate = 100.0
    private val rateMap = mapOf(Quotation.CCL to dollarCCLRate)

    private val date = LocalDate.of(2002, 10, 1)
    private val historicRateMap =
        mapOf<Quotation, Map<LocalDate, Double>>(
            Quotation.CCL to
                mapOf(
                    date to dollarCCLRate,
                ),
        )

    private val mockDollarCalculator = DollarCalculator(MockRateProvider(rateMap, historicRateMap))

    @Test
    fun `001 _ build an empty portfolio`() {
        val portfolioBuilder =
            PortfolioBuilder(
                operations = listOf(),
                dollarCalculator = mockDollarCalculator,
            )

        val portfolio = portfolioBuilder.build()

        assertEquals(Dollars(0.0), portfolio.getTotalValuation())
    }

    @Test
    fun `002 _ build a portfolio out of 1 operation`() {
        val operations =
            listOf(
                Stock("SPY", 10, Dollars(10.0), date),
            )

        val portfolioBuilder =
            PortfolioBuilder(
                operations = operations,
                dollarCalculator = mockDollarCalculator,
            )

        val portfolio = portfolioBuilder.build()

        assertEquals(Dollars(100.0), portfolio.getTotalValuation())
    }
}
