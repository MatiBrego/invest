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

    private val mockDollarCalculator = DollarCalculator(MockRateProvider(rateMap, historicRateMap))

    @Test
    fun `001 empty portfolio should return 0 valuation`() {
        val portfolio =
            Portfolio(
                holdings = listOf(),
                operations = listOf(),
                dollarCalculator = mockDollarCalculator,
            )

        assertEquals(Dollars(0.0), portfolio.getTotalValuation())
    }

    @Test
    fun `002 portfolio with 10 dollars should return 10 valuation`() {
        val portfolio =
            Portfolio(
                holdings = listOf(Cash(Dollars(10.0))),
                operations = listOf(),
                dollarCalculator = mockDollarCalculator,
            )

        assertEquals(Dollars(10.0), portfolio.getTotalValuation())
    }

    @Test
    fun `003 portfolio with 10 dollars worth of stocks should return 10 valuation`() {
        val portfolio =
            Portfolio(
                holdings = listOf(Stock("SPY", 10, Dollars(1.0))),
                operations = listOf(),
                dollarCalculator = mockDollarCalculator,
            )

        assertEquals(Dollars(10.0), portfolio.getTotalValuation())
    }

    @Test
    fun `004 empty portfolio should return ROI of 0`() {
        val portfolio =
            Portfolio(
                holdings = listOf(),
                operations = listOf(),
                dollarCalculator = mockDollarCalculator,
            )

        assertEquals(0.0, portfolio.getROI())
    }

    @Test
    fun `005 portfolio with stock that didn't change price should return ROI of 0`() {
        val portfolio =
            Portfolio(
                holdings = listOf(Stock("SPY", 10, Dollars(2.0))),
                operations = listOf(Stock("SPY", 10, Dollars(2.0))),
                dollarCalculator = mockDollarCalculator,
            )

        assertEquals(0.0, portfolio.getROI())
    }

    @Test
    fun `006 portfolio with stock that doubled price should return ROI of 1`() {
        val portfolio =
            Portfolio(
                holdings = listOf(Stock("SPY", 10, Dollars(2.0))),
                operations = listOf(Stock("SPY", 10, Dollars(1.0))),
                dollarCalculator = mockDollarCalculator,
            )

        assertEquals(1.0, portfolio.getROI())
    }

    @Test
    fun `007 portfolio with stock that increased price by half should return ROI of 0,5`() {
        val portfolio =
            Portfolio(
                holdings = listOf(Stock("SPY", 10, Dollars(1.5))),
                operations = listOf(Stock("SPY", 10, Dollars(1.0))),
                dollarCalculator = mockDollarCalculator,
            )

        assertEquals(0.5, portfolio.getROI())
    }
}
