package com.invest.tracker.builder

import com.invest.currency.core.DollarCalculator
import com.invest.currency.core.Dollars
import com.invest.currency.core.MockRateProvider
import com.invest.currency.core.Quotation
import com.invest.tracker.core.instrument.Cash
import com.invest.tracker.core.instrument.Stock
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InstrumentAggregatorTest {
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
    fun `001 _ Cash aggregator should sum all cash into one`() {
        val cash =
            listOf(
                Cash(Dollars(3.0)),
                Cash(Dollars(2.0)),
            )

        val cashAggregator = CashAggregator(mockDollarCalculator)

        val result = cashAggregator.aggregate(cash)

        assertEquals((result[0] as Cash).getValuation(mockDollarCalculator), Dollars(5.0))
    }

    @Test
    fun `002 _ Stock aggregator should sum the same stocks into one`() {
        val stockTicket = "SPY"
        val stocks =
            listOf(
                Stock(stockTicket, 3, Dollars(10.0), date),
                Stock(stockTicket, 2, Dollars(11.0), date),
            )

        val stockAggregator = StockAggregator(mapOf(stockTicket to Dollars(12.0)))

        val result = stockAggregator.aggregate(stocks)

        assertEquals(stockTicket, (result[0] as Stock).ticket)
        assertEquals(5, (result[0] as Stock).quantity)
    }

    @Test
    fun `003 _ Stock aggregator should not sum different stocks into one`() {
        val stockTicket1 = "SPY"
        val stockTicket2 = "BRKB"
        val stocks =
            listOf(
                Stock(stockTicket1, 3, Dollars(10.0), date),
                Stock(stockTicket2, 2, Dollars(11.0), date),
            )

        val priceMap =
            mapOf(
                stockTicket1 to Dollars(12.0),
                stockTicket2 to Dollars(12.0),
            )

        val stockAggregator = StockAggregator(priceMap)

        val result = stockAggregator.aggregate(stocks)

        assertEquals(stockTicket1, (result[0] as Stock).ticket)
        assertEquals(3, (result[0] as Stock).quantity)

        assertEquals(stockTicket2, (result[1] as Stock).ticket)
        assertEquals(2, (result[1] as Stock).quantity)
    }

    @Test
    fun `004 _ Stock aggregator should use provided price map for final stock price`() {
        val stockTicket = "SPY"
        val stockFinalPrice = Dollars(12.0)
        val stocks =
            listOf(
                Stock(stockTicket, 3, Dollars(10.0), date),
                Stock(stockTicket, 2, Dollars(11.0), date),
            )

        val priceMap = mapOf(stockTicket to stockFinalPrice)

        val stockAggregator = StockAggregator(priceMap)

        val result = stockAggregator.aggregate(stocks)

        assertEquals(stockFinalPrice, (result[0] as Stock).price)
    }

    @Test
    fun `005 _ Stock aggregator should use throw exception if stock price is not present in price map`() {
        val stockTicket = "SPY"
        val stockFinalPrice = Dollars(12.0)
        val stocks =
            listOf(
                Stock(stockTicket, 3, Dollars(10.0), date),
                Stock(stockTicket, 2, stockFinalPrice, date),
            )

        val stockAggregator = StockAggregator(mapOf())

        assertThrows<IllegalArgumentException> { stockAggregator.aggregate(stocks) }
    }

    @Test
    fun `006 _ Stock aggregator should aggregate Stock instruments only`() {
        val stockTicket = "SPY"
        val stockFinalPrice = Dollars(12.0)
        val instruments =
            listOf(
                Stock(stockTicket, 3, Dollars(10.0), date),
                Stock(stockTicket, 2, Dollars(11.0), date),
                Cash(Dollars(10.0)),
                Cash(Dollars(10.0)),
            )

        val priceMap = mapOf(stockTicket to stockFinalPrice)

        val stockAggregator = StockAggregator(priceMap)

        val result = stockAggregator.aggregate(instruments)

        assertTrue(result[0] is Stock)
        assertTrue(result[1] is Cash)
        assertTrue(result[2] is Cash)
    }

    @Test
    fun `007 _ Cash aggregator should aggregate Cash instruments only`() {
        val stockTicket = "SPY"
        val instruments =
            listOf(
                Cash(Dollars(10.0)),
                Cash(Dollars(10.0)),
                Stock(stockTicket, 3, Dollars(10.0), date),
                Stock(stockTicket, 2, Dollars(11.0), date),
            )

        val stockAggregator = CashAggregator(mockDollarCalculator)

        val result = stockAggregator.aggregate(instruments)

        assertTrue(result[0] is Cash)
        assertTrue(result[1] is Stock)
        assertTrue(result[2] is Stock)
    }
}
