package com.invest.tracker.builder

import com.invest.currency.core.DollarCalculator
import com.invest.currency.core.Dollars
import com.invest.currency.core.MockRateProvider
import com.invest.currency.core.Quotation
import com.invest.tracker.builder.aggregator.CashAggregator
import com.invest.tracker.builder.aggregator.StockAggregator
import com.invest.tracker.core.instrument.Cash
import com.invest.tracker.core.instrument.Stock
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class PortfolioBuilderTest {
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
                listOf(),
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
                listOf(),
            )

        val portfolio = portfolioBuilder.build()

        assertEquals(Dollars(100.0), portfolio.getTotalValuation())
    }

    @Test
    fun `003 _ operations from the same stock should add up in one holding in portfolio`() {
        val ticket = "SPY"

        val operations =
            listOf(
                Stock(ticket, 3, Dollars(10.0), date),
                Stock(ticket, 2, Dollars(11.0), date),
            )

        val aggregators =
            listOf(
                StockAggregator(
                    mapOf(ticket to Dollars(12.0)),
                ),
            )

        val portfolioBuilder =
            PortfolioBuilder(
                operations = operations,
                dollarCalculator = mockDollarCalculator,
                aggregators,
            )

        val portfolio = portfolioBuilder.build()

        assertEquals(5, (portfolio.holdings[0] as Stock).quantity)
    }

    @Test
    fun `004 _ cash operations should add up in one holding in portfolio`() {
        val operations =
            listOf(
                Cash(Dollars(10.0)),
                Cash(Dollars(10.0)),
            )

        val aggregators =
            listOf(
                CashAggregator(
                    mockDollarCalculator,
                ),
            )

        val portfolioBuilder =
            PortfolioBuilder(
                operations = operations,
                dollarCalculator = mockDollarCalculator,
                aggregators,
            )

        val portfolio = portfolioBuilder.build()

        assertEquals(Dollars(20.0), (portfolio.holdings[0] as Cash).getValuation(mockDollarCalculator))
    }

    @Test
    fun `005 _ operations of different instruments should be added into portfolio holdings`() {
        val ticket = "SPY"

        val operations =
            listOf(
                Cash(Dollars(10.0)),
                Cash(Dollars(10.0)),
                Stock(ticket, 3, Dollars(10.0), date),
                Stock(ticket, 2, Dollars(11.0), date),
            )

        val aggregators =
            listOf(
                CashAggregator(
                    mockDollarCalculator,
                ),
                StockAggregator(
                    mapOf(ticket to Dollars(12.0)),
                ),
            )

        val portfolioBuilder =
            PortfolioBuilder(
                operations = operations,
                dollarCalculator = mockDollarCalculator,
                aggregators,
            )

        val portfolio = portfolioBuilder.build()

        assertEquals(5, (portfolio.holdings[0] as Stock).quantity)
        assertEquals(Dollars(20.0), (portfolio.holdings[1] as Cash).getValuation(mockDollarCalculator))
    }
}
