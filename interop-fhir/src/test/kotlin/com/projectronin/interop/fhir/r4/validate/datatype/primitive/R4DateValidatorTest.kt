package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4DateValidatorTest {
    @Test
    fun `accepts YYYY`() {
        val date = Date("2021")
        R4DateValidator.validate(date).alertIfErrors()
    }

    @Test
    fun `accepts YYYY-MM`() {
        val date = Date("2021-11")
        R4DateValidator.validate(date).alertIfErrors()
    }

    @Test
    fun `accepts YYYY-MM-DD`() {
        val date = Date("2021-11-16")
        R4DateValidator.validate(date).alertIfErrors()
    }

    @Test
    fun `fails on invalid input`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val date = Date("abc")
                R4DateValidator.validate(date).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Date",
            exception.message,
        )
    }

    @Test
    fun `includes parent context`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val date = Date("abc")
                R4DateValidator.validate(date, LocationContext("Test", "field")).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Date @ Test.field",
            exception.message,
        )
    }
}
