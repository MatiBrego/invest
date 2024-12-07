package com.invest.tracker.core

import com.invest.currency.core.DollarCalculator
import com.invest.currency.core.Dollars
import com.invest.currency.core.MockRateProvider
import com.invest.currency.core.Quotation
import com.invest.tracker.core.instrument.Cash
import com.invest.tracker.core.instrument.Stock
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

class PortfolioTest {
    private val dollarCCLRate = 100.0
    private val rateMap = mapOf(Quotation.CCL to dollarCCLRate)
    private val historicRateMap = mapOf<Quotation, Map<LocalDate, Double>>()

    val dollarCalculator = DollarCalculator(MockRateProvider(rateMap, historicRateMap))

    @Test
    fun `001 empty portfolio should return 0 valuation`() {
        val portfolio = Portfolio(listOf(), dollarCalculator)

        assertEquals(Dollars(0.0), portfolio.getTotalValuation())
    }

    @Test
    fun `002 portfolio with 10 dollars should return 10 valuation`() {
        val portfolio = Portfolio(listOf(Cash(Dollars(10.0))), dollarCalculator)

        assertEquals(Dollars(10.0), portfolio.getTotalValuation())
    }

    @Test
    fun `003 portfolio with 10 dollars worth of stocks should return 10 valuation`() {
        val portfolio = Portfolio(listOf(Stock("SPY", 10, Dollars(1.0))), dollarCalculator)

        assertEquals(Dollars(10.0), portfolio.getTotalValuation())
    }
}
