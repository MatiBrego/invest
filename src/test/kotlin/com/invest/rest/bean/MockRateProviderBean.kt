package com.invest.rest.bean

import com.invest.currency.core.MockRateProvider
import com.invest.currency.core.Quotation
import com.invest.currency.core.RateProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.LocalDate

@Configuration
@Profile("test")
class MockRateProviderBean {
    companion object {
        const val DOLLAR_CCL_RATE = 100.0
        const val DOLLAR_BLUE_RATE = 200.0

        val date1 = LocalDate.of(2002, 10, 1)
        val date2 = LocalDate.of(2002, 10, 2)
    }

    private val rateMap = mapOf(Quotation.CCL to DOLLAR_CCL_RATE, Quotation.BLUE to DOLLAR_BLUE_RATE)
    private val historicRateMap =
        mapOf(
            Quotation.CCL to
                mapOf(
                    date1 to 100.0,
                    date2 to 300.0,
                ),
        )

    @Bean
    fun mockRateProvider(): RateProvider {
        return MockRateProvider(
            rateMap,
            historicRateMap,
        )
    }
}
