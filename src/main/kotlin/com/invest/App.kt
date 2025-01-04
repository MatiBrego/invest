package com.invest

import com.invest.csv.Parser
import com.invest.currency.core.DollarCalculator
import com.invest.currency.core.Dollars
import com.invest.currency.provider.RemoteRateProvider
import com.invest.tracker.builder.PortfolioBuilder
import com.invest.tracker.builder.aggregator.StockAggregator
import com.invest.tracker.core.instrument.Stock
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.scheduling.annotation.EnableScheduling
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@SpringBootApplication
@EntityScan("com.invest")
@EnableScheduling
class App

fun main(args: Array<String>) {
    val fileName = "operations.csv"
    val parser = Parser()
    val dollarCalculator = DollarCalculator(RemoteRateProvider())
    val startingDate = LocalDate.of(2025, 1, 4)

    val priceMap =
        mapOf(
            "SPY" to Dollars(29.45),
        )

    val startingInstrument =
        Stock(
            ticket = "SPY",
            quantity = 346,
            price = priceMap["SPY"]!!,
            date = startingDate,
        )

    val aggregators = listOf(StockAggregator(priceMap))

    val yearsPassed = ChronoUnit.DAYS.between(startingDate, LocalDate.now()) / 365.25

    try {
        val maps = parser.parseCsvToMaps(fileName)
        val operations = listOf(startingInstrument) + parser.parsMapsToInstrument(maps)

        val portfolio = PortfolioBuilder(operations, dollarCalculator, aggregators).build()

        val roi = Math.round(portfolio.getROI() * 10000.0) / 10.0

        println("Valuation: ${portfolio.getTotalValuation()}")
        println("Earned: ${portfolio.getEarnedAmount()}")
        println("ROI: %$roi")
        println("Yearly ROI Average: ${roi / yearsPassed} %")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}
