package com.invest.currency.core

import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class DollarCalculatorTest {
    private val dollarCCLRate = 100.0
    private val dollarBlueRate = 200.0

    private val date1 = LocalDate.of(2002, 10, 1)
    private val date2 = LocalDate.of(2002, 10, 2)

    private val rateMap = mapOf(Quotation.CCL to dollarCCLRate, Quotation.BLUE to dollarBlueRate)
    private val historicRateMap =
        mapOf(
            Quotation.CCL to
                mapOf(
                    date1 to 100.0,
                    date2 to 300.0,
                ),
        )

    @Test
    fun `001 convert one dollar to pesos at CCL rate 100`() {
        val calculator = DollarCalculator(MockRateProvider(rateMap, historicRateMap))

        val result = calculator.toPesos(dollars = Dollars(1.0), Quotation.CCL)

        assertEquals(Pesos(100.0), result)
    }

    @Test
    fun `002 convert two dollars to pesos at CCL rate 100`() {
        val calculator = DollarCalculator(MockRateProvider(rateMap, historicRateMap))

        val result = calculator.toPesos(dollars = Dollars(2.0), Quotation.CCL)

        assertEquals(Pesos(200.0), result)
    }

    @Test
    fun `003 convert 100 pesos to dollars at CCL rate 100`() {
        val calculator = DollarCalculator(MockRateProvider(rateMap, historicRateMap))

        val result = calculator.toDollars(pesos = Pesos(100.0), Quotation.CCL)

        assertEquals(Dollars(1.0), result)
    }

    @Test
    fun `004 convert 200 pesos to dollars at CCL rate 100`() {
        val calculator = DollarCalculator(MockRateProvider(rateMap, historicRateMap))

        val result = calculator.toDollars(pesos = Pesos(200.0), Quotation.CCL)

        assertEquals(Dollars(2.0), result)
    }

    @Test
    fun `005 convert 1 dollar to pesos at Blue rate 200`() {
        val calculator = DollarCalculator(MockRateProvider(rateMap, historicRateMap))

        val result = calculator.toPesos(dollars = Dollars(1.0), Quotation.BLUE)

        assertEquals(Pesos(200.0), result)
    }

    @Test
    fun `006 convert 200 dollar to pesos at Blue rate 200`() {
        val calculator = DollarCalculator(MockRateProvider(rateMap, historicRateMap))

        val result = calculator.toDollars(pesos = Pesos(200.0), Quotation.BLUE)

        assertEquals(Dollars(1.0), result)
    }

    @Test
    fun `007 convert 1 dollar to pesos at CCL rate 100 in date1`() {
        val calculator = DollarCalculator(MockRateProvider(rateMap, historicRateMap))
        val date = date1

        val result = calculator.toHistoricPesos(dollars = Dollars(1.0), Quotation.CCL, date = date)

        assertEquals(Pesos(100.0), result)
    }

    @Test
    fun `008 convert 1 dollar to pesos at CCL rate 300 in date2`() {
        val calculator = DollarCalculator(MockRateProvider(rateMap, historicRateMap))
        val date = date2

        val result = calculator.toHistoricPesos(dollars = Dollars(1.0), Quotation.CCL, date = date)

        assertEquals(Pesos(300.0), result)
    }

    @Test
    fun `009 convert 100 pesos to dollar at CCL rate 100 in date1`() {
        val calculator = DollarCalculator(MockRateProvider(rateMap, historicRateMap))
        val date = date1

        val result = calculator.toHistoricDollar(pesos = Pesos(100.0), Quotation.CCL, date = date)

        assertEquals(Dollars(1.0), result)
    }

    @Test
    fun `010 convert 300 pesos to dollar at CCL rate 300 in date2`() {
        val calculator = DollarCalculator(MockRateProvider(rateMap, historicRateMap))
        val date = date2

        val result = calculator.toHistoricDollar(pesos = Pesos(300.0), Quotation.CCL, date = date)

        assertEquals(Dollars(1.0), result)
    }
}
