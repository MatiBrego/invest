package com.invest.csv

import com.invest.currency.core.Dollars
import com.invest.currency.core.Pesos
import com.invest.tracker.core.instrument.Instrument
import com.invest.tracker.core.instrument.Stock
import com.opencsv.CSVReaderBuilder
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Parser {
    fun parseCsvToMaps(resourcePath: String): List<Map<String, String>> {
        val result = mutableListOf<Map<String, String>>()

        // Access the file from resources
        val inputStream =
            this::class.java.classLoader.getResourceAsStream(resourcePath)
                ?: throw IllegalArgumentException("File not found: $resourcePath")

        InputStreamReader(inputStream).use { reader ->
            CSVReaderBuilder(reader)
                .build().use { csvReader ->
                    val csvData = csvReader.readAll()
                    val headers = csvData.firstOrNull() ?: return emptyList() // Get header row

                    csvData.drop(1).forEach { line -> // Process rows
                        val map =
                            headers.zip(line).toMap().mapValues { (key, value) ->
                                when (key) {
                                    "Cantidad" -> { // Handle "Cantidad" column
                                        value.replace(",", "").dropLast(4)
                                    }
                                    "Precio Ponderado" -> { // Handle "Price" column
                                        value.replace(".", "").replace(",", ".")
                                    }
                                    else -> { // For other columns, keep as-is
                                        value
                                    }
                                }
                            }
                        result.add(map)
                    }
                }
        }

        return result
    }

    fun parsMapsToInstrument(maps: List<Map<String, String>>): List<Instrument> {
        val filteredList =
            maps.filterNot {
                it["Simbolo"].equals("AL30") || it["Simbolo"].equals("AL30D")
            }

        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss")

        val cedears =
            filteredList.map {
                val ticket = it["Simbolo"]!!
                val currency = it["Moneda"]!!
                val isSell = it["Tipo Transacción"]!!.equals("Venta")
                val quantity = if (isSell) -it["Cantidad"]!!.toInt() else it["Cantidad"]!!.toInt()
                val price = it["Precio Ponderado"]!!.toDouble()
                val date = LocalDateTime.parse(it["Fecha Transacción"], formatter).toLocalDate()

                Stock(
                    ticket = if (currency.equals("US\$")) ticket.dropLast(1) else ticket,
                    quantity = quantity,
                    price = if (currency.equals("US\$")) Dollars(price) else Pesos(price),
                    date = date,
                )
            }

        return cedears
    }
}
