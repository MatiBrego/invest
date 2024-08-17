package com.invest.rest.route

import com.invest.currency.core.Quotation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate

@RequestMapping("/v1/currency")
interface CurrencyRouteSpec {
    @GetMapping("/dollar")
    fun toDollar(
        @RequestParam("amount") amount: Double,
        @RequestParam("quotation") quotation: Quotation,
    ): Double

    @GetMapping("/peso")
    fun toPeso(
        @RequestParam("amount") amount: Double,
        @RequestParam("quotation") quotation: Quotation,
    ): Double

    @GetMapping("/historic/dollar")
    fun toHistoricDollar(
        @RequestParam("amount") amount: Double,
        @RequestParam("quotation") quotation: Quotation,
        @RequestParam("date") date: LocalDate,
    ): Double

    @GetMapping("/historic/peso")
    fun toHistoricPeso(
        @RequestParam("amount") amount: Double,
        @RequestParam("quotation") quotation: Quotation,
        @RequestParam("date") date: LocalDate,
    ): Double
}
