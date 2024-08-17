package com.invest.rest.bean

import com.invest.currency.core.RateProvider
import com.invest.currency.remote.RemoteRateProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("production")
class RemoteRateProviderBean {
    @Bean
    fun rateProvider(): RateProvider {
        return RemoteRateProvider()
    }
}
