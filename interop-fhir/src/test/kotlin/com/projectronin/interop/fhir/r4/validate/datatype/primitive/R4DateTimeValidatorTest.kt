package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4DateTimeValidatorTest {
    @Test
    fun `accepts YYYY`() {
        val dateTime = DateTime("2021")
        R4DateTimeValidator.validate(dateTime).alertIfErrors()
    }

    @Test
    fun `accepts YYYY-MM`() {
        val dateTime = DateTime("2021-11")
        R4DateTimeValidator.validate(dateTime).alertIfErrors()
    }

    @Test
    fun `accepts YYYY-MM-DD`() {
        val dateTime = DateTime("2021-11-16")
        R4DateTimeValidator.validate(dateTime).alertIfErrors()
    }

    @Test
    fun `accepts YYYY-MM-DDThh_mm_ss+zz_zz`() {
        val dateTime = DateTime("2021-11-16T20:30:40+02:00")
        R4DateTimeValidator.validate(dateTime).alertIfErrors()
    }

    @Test
    fun `fails on invalid input`() {
        val exception = assertThrows<IllegalArgumentException> {
            val dateTime = DateTime("abc")
            R4DateTimeValidator.validate(dateTime).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a DateTime",
            exception.message
        )
    }

    @Test
    fun `fails on 24_00 time`() {
        val exception = assertThrows<IllegalArgumentException> {
            val dateTime = DateTime("2021-11-16T24:00:00Z")
            R4DateTimeValidator.validate(dateTime).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a DateTime",
            exception.message
        )
    }

    @Test
    fun `includes parent context`() {
        val exception = assertThrows<IllegalArgumentException> {
            val dateTime = DateTime("2021-11-16T24:00:00Z")
            R4DateTimeValidator.validate(dateTime, LocationContext("Test", "field")).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a DateTime @ Test.field",
            exception.message
        )
    }
}
