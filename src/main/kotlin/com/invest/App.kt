package com.invest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.invest.currency.persistence"])
@EntityScan("com.invest")
class App

fun main(args: Array<String>) {
    runApplication<App>(*args)
}
