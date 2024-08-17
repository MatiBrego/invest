package com.invest.rest.route

import com.invest.currency.core.Quotation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

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
}
