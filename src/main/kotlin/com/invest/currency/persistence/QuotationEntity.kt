package com.invest.currency.persistence

import com.invest.currency.core.Quotation
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.TemporalType
import org.springframework.data.jpa.repository.Temporal
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "quotation")
data class QuotationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,
    @Column(name = "amount")
    val amount: Double,
    @Enumerated(EnumType.STRING)
    val quotation: Quotation,
    @Temporal(TemporalType.DATE)
    val date: LocalDate,
)
