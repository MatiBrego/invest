package com.invest.currency.persistence

import com.invest.currency.core.Quotation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.UUID

@Repository
interface QuotationRepository : JpaRepository<QuotationEntity, UUID> {
    fun findFirstByQuotationOrderByDateDesc(quotation: Quotation): QuotationEntity

    fun findByQuotationAndDate(
        quotation: Quotation,
        date: LocalDate,
    ): QuotationEntity
}
