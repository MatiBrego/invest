package com.invest.currency.remote

import com.invest.currency.core.Quotation
import com.invest.currency.core.RateProvider
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RemoteRateProvider(
    private val restTemplate: RestTemplate = RestTemplate(),
    private val dollarApiBaseUrl: String = "https://dolarapi.com/v1/dolares",
    private val historicDollarApiBaseUrl: String = "https://api.argentinadatos.com/v1/cotizaciones/dolares",
) : RateProvider {
    private val quotationAdapter =
        mapOf(
            Quotation.CCL to "contadoconliqui",
            Quotation.BLUE to "blue",
            Quotation.OFFICIAL to "oficial",
            Quotation.EXCHANGE to "bolsa",
        )

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

    override fun getRateForQuotation(quotation: Quotation): Double {
        val getUrl = "$dollarApiBaseUrl/${quotationAdapter[quotation]}"
        val response = restTemplate.getForEntity<DollarApiResponse>(getUrl)
        return response.body!!.venta
    }

    override fun getHistoricRateForQuotation(
        quotation: Quotation,
        date: LocalDate,
    ): Double {
        val dateString = dateFormatter.format(date)
        val getUrl = "$historicDollarApiBaseUrl/${quotationAdapter[quotation]}/$dateString"
        val response = restTemplate.getForEntity<DollarApiResponse>(getUrl)
        return response.body!!.venta
    }
}

class DollarApiResponse(
    val compra: Double,
    val venta: Double,
)
