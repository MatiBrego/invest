package com.invest.rest

import com.invest.currency.core.Quotation
import com.invest.rest.bean.MockRateProviderBean
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDate

@SpringBootTest
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ToHistoricDollarServerTests
    @Autowired
    constructor(val mockMvc: MockMvc) {
        private val baseUrl = "/v1/currency/historic/dollar"

        @Test
        fun `to historic dollar endpoint exists`() {
            val uriWithParam =
                withAmountQuotationAndDate(
                    baseUrl,
                    100.0,
                    Quotation.CCL,
                    LocalDate.of(2002, 10, 1),
                )
            mockMvc.get(uriWithParam)
                .andExpect {
                    status { isOk() }
                }
        }

        @Test
        fun `can convert 100 pesos to dollar with CCL quotation of 2002-10-01`() {
            val pesos = 100.0
            val quotation = Quotation.CCL
            val date = MockRateProviderBean.date1

            val uriWithParams =
                withAmountQuotationAndDate(
                    baseUrl,
                    pesos,
                    quotation,
                    date,
                )

            val expected = pesos / MockRateProviderBean.CCLDateMap[date]!!
            mockMvc.get(uriWithParams)
                .andExpect {
                    status { isOk() }
                    content {
                        string(expected.toString())
                    }
                }
        }

        @Test
        fun `can convert 300 pesos to dollar with CCL quotation of 2002-10-02`() {
            val pesos = 300.0
            val quotation = Quotation.CCL
            val date = MockRateProviderBean.date2

            val uriWithParams =
                withAmountQuotationAndDate(
                    baseUrl,
                    pesos,
                    quotation,
                    date,
                )

            val expected = pesos / MockRateProviderBean.CCLDateMap[date]!!
            mockMvc.get(uriWithParams)
                .andExpect {
                    status { isOk() }
                    content {
                        string(expected.toString())
                    }
                }
        }
    }
