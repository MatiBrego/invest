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

@SpringBootTest
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ToDollarServerTests
    @Autowired
    constructor(val mockMvc: MockMvc) {
        private val baseDollarUrl = "/v1/currency/dollar"

        @Test
        fun `to dollar endpoint exists`() {
            val uriWithParam = withAmountAndQuotation(baseDollarUrl, 100.0, Quotation.CCL)
            mockMvc.get(uriWithParam)
                .andExpect {
                    status { isOk() }
                }
        }

        @Test
        fun `can convert 100 pesos to dollar with CCL quotation`() {
            val pesos = 100.0
            val quotation = Quotation.CCL
            val uriWithParams = withAmountAndQuotation(baseDollarUrl, pesos, quotation)

            val expected = pesos / MockRateProviderBean.DOLLAR_CCL_RATE
            mockMvc.get(uriWithParams)
                .andExpect {
                    status { isOk() }
                    content {
                        string(expected.toString())
                    }
                }
        }

        @Test
        fun `can convert 200 pesos to dollar with CCL quotation`() {
            val pesos = 200.0
            val quotation = Quotation.CCL
            val uriWithParams = withAmountAndQuotation(baseDollarUrl, pesos, quotation)

            val expected = pesos / MockRateProviderBean.DOLLAR_CCL_RATE
            mockMvc.get(uriWithParams)
                .andExpect {
                    status { isOk() }
                    content {
                        string(expected.toString())
                    }
                }
        }

        @Test
        fun `can convert 100 pesos to dollar with Blue quotation`() {
            val pesos = 100.0
            val quotation = Quotation.BLUE
            val uriWithParams = withAmountAndQuotation(baseDollarUrl, pesos, quotation)

            val expected = pesos / MockRateProviderBean.DOLLAR_BLUE_RATE
            mockMvc.get(uriWithParams)
                .andExpect {
                    status { isOk() }
                    content {
                        string(expected.toString())
                    }
                }
        }

        private fun withAmountAndQuotation(
            uri: String,
            amount: Double,
            quotation: Quotation,
        ): String {
            return "$uri?amount=$amount&quotation=$quotation"
        }
    }
