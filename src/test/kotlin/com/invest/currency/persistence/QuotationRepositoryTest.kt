package com.invest.currency.persistence

import com.invest.App
import com.invest.currency.core.Quotation
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertNotNull

@ActiveProfiles("test")
@SpringBootTest(classes = [App::class])
class QuotationRepositoryTest {
    @Autowired
    lateinit var quotationRepository: QuotationRepository

    @Test
    fun `test create and retrieve quotation`() {
        // Arrange
        val quotation =
            QuotationEntity(
                amount = 100.0,
                quotation = Quotation.CCL,
                date = LocalDate.now(),
            )

        // Act
        val savedQuotation = quotationRepository.save(quotation)

        // Assert
        assertNotNull(savedQuotation.id) // Ensure the ID is generated
        assertEquals(Quotation.CCL, savedQuotation.quotation)
        assertEquals(100.0, savedQuotation.amount)
    }
}
