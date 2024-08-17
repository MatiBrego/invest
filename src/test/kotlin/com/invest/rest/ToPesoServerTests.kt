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
class ToPesoServerTests
    @Autowired
    constructor(val mockMvc: MockMvc) {
        private val baseDollarUrl = "/v1/currency/peso"

        @Test
        fun `to dollar endpoint exists`() {
            val uriWithParam = withAmountAndQuotation(baseDollarUrl, 1.0, Quotation.CCL)
            mockMvc.get(uriWithParam)
                .andExpect {
                    status { isOk() }
                }
        }

        @Test
        fun `can convert 1 dollar to peso with CCL quotation`() {
            val dollars = 1.0
            val quotation = Quotation.CCL
            val uriWithParams = withAmountAndQuotation(baseDollarUrl, dollars, quotation)

            val expected = dollars * MockRateProviderBean.DOLLAR_CCL_RATE
            mockMvc.get(uriWithParams)
                .andExpect {
                    status { isOk() }
                    content {
                        string(expected.toString())
                    }
                }
        }

        @Test
        fun `can convert 2 dollars to peso with CCL quotation`() {
            val pesos = 2.0
            val quotation = Quotation.CCL
            val uriWithParams = withAmountAndQuotation(baseDollarUrl, pesos, quotation)

            val expected = pesos * MockRateProviderBean.DOLLAR_CCL_RATE
            mockMvc.get(uriWithParams)
                .andExpect {
                    status { isOk() }
                    content {
                        string(expected.toString())
                    }
                }
        }

        @Test
        fun `can convert 1 dollar to peso with Blue quotation`() {
            val pesos = 1.0
            val quotation = Quotation.BLUE
            val uriWithParams = withAmountAndQuotation(baseDollarUrl, pesos, quotation)

            val expected = pesos * MockRateProviderBean.DOLLAR_BLUE_RATE
            mockMvc.get(uriWithParams)
                .andExpect {
                    status { isOk() }
                    content {
                        string(expected.toString())
                    }
                }
        }
    }
