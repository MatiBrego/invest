package com.invest.rest.bean

import com.invest.currency.core.DollarCalculator
import com.invest.currency.core.RateProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DollarCalculatorBean
    @Autowired
    constructor(private val rateProvider: RateProvider) {
        @Bean
        fun dollarCalculator(): DollarCalculator  {
            return DollarCalculator(rateProvider)
        }
    }
