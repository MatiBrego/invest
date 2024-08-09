package com.invest.app

import com.invest.currency.core.DollarCalculator
import com.invest.currency.core.Quotation
import com.invest.currency.remote.RemoteRateProvider
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class AppApplication

fun main(args: Array<String>) {
//    runApplication<AppApplication>(*args)
    val calculator = DollarCalculator(RemoteRateProvider())

    println(calculator.toDollars(1500000.0, Quotation.EXCHANGE))
}
