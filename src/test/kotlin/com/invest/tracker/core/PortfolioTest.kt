package com.invest.tracker.core

import com.invest.tracker.core.instrument.Cash
import com.invest.tracker.core.instrument.Stock
import kotlin.test.Test
import kotlin.test.assertEquals

class PortfolioTest {
    @Test
    fun `001 empty portfolio should return 0 valuation`() {
        val portfolio = Portfolio(listOf())

        assertEquals(0.0, portfolio.getTotalValuation())
    }

    @Test
    fun `002 portfolio with 10 dollars should return 10 valuation`() {
        val portfolio = Portfolio(listOf(Holding(Cash(10.0))))

        assertEquals(10.0, portfolio.getTotalValuation())
    }

    @Test
    fun `003 portfolio with 10 dollars worth of stocks should return 10 valuation`() {
        val portfolio = Portfolio(listOf(Holding(Stock("SPY", 10, 1.0))))

        assertEquals(10.0, portfolio.getTotalValuation())
    }
}
