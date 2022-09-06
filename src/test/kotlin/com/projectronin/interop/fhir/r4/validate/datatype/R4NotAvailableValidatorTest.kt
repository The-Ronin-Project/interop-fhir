package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.NotAvailable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4NotAvailableValidatorTest {
    @Test
    fun `fails if no description provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val notAvailable = NotAvailable(
                description = null
            )
            R4NotAvailableValidator.validate(notAvailable).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: description is a required element @ NotAvailable.description",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val notAvailable = NotAvailable(
            description = "Vacation"
        )
        R4NotAvailableValidator.validate(notAvailable).alertIfErrors()
    }
}
