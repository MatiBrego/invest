package com.invest.tracker.core

import com.invest.currency.core.DollarCalculator
import com.invest.currency.core.Dollars
import com.invest.currency.core.MockRateProvider
import com.invest.currency.core.Pesos
import com.invest.currency.core.Quotation
import com.invest.tracker.core.instrument.Cash
import com.invest.tracker.core.instrument.Stock
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class InstrumentTest {
    private val dollarCCLRate = 100.0
    private val rateMap = mapOf(Quotation.CCL to dollarCCLRate)
    private val historicRateMap = mapOf<Quotation, Map<LocalDate, Double>>()

    private val dollarCalculator = DollarCalculator(MockRateProvider(rateMap, historicRateMap))

    @Test
    fun `001 _ Cash can be from dollars`() {
        val amountDollars = 100.0
        val cash = Cash(Dollars(amountDollars))

        assertEquals(Dollars(amountDollars), cash.getValuation(dollarCalculator))
    }

    @Test
    fun `002 _ Cash can be from pesos`() {
        val amountPesos = 1000.0
        val cash = Cash(Pesos(amountPesos))

        val expected = Dollars(amountPesos / dollarCCLRate)
        assertEquals(expected, cash.getValuation(dollarCalculator))
    }

    @Test
    fun `003 _ Stock can be from dollars`() {
        val priceDollars = 30.0
        val quantity = 2
        val stock = Stock("SPY", quantity, Dollars(priceDollars))

        val expected = Dollars(priceDollars * quantity)
        assertEquals(expected, stock.getValuation(dollarCalculator))
    }

    @Test
    fun `004 _ Stock can be from pesos`() {
        val pricePesos = 30000.0
        val quantity = 2
        val stock = Stock("SPY", quantity, Pesos(pricePesos))

        val expected = Dollars((pricePesos * quantity) / dollarCCLRate)
        assertEquals(expected, stock.getValuation(dollarCalculator))
    }
}
