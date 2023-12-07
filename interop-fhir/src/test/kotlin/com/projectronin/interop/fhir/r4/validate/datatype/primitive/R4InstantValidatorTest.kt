package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4InstantValidatorTest {
    @Test
    fun `accepts valid value`() {
        val instant = Instant("2017-01-01T00:00:00Z")
        R4InstantValidator.validate(instant).alertIfErrors()
    }

    @Test
    fun `accepts minus timezone`() {
        val instant = Instant("2017-01-01T00:00:00-02:00")
        R4InstantValidator.validate(instant).alertIfErrors()
    }

    @Test
    fun `accepts plus timezone`() {
        val instant = Instant("2017-01-01T00:00:00+10:30")
        R4InstantValidator.validate(instant).alertIfErrors()
    }

    @Test
    fun `accepts microseconds`() {
        val instant = Instant("2017-01-01T00:00:00.123Z")
        R4InstantValidator.validate(instant).alertIfErrors()
    }

    @Test
    fun `accepts microseconds with timezone`() {
        val instant = Instant("2017-01-01T00:00:00.123+05:00")
        R4InstantValidator.validate(instant).alertIfErrors()
    }

    @Test
    fun `fails on invalid format`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val instant = Instant("2017-01-01 00:00:00")
                R4InstantValidator.validate(instant).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Instant",
            exception.message,
        )
    }

    @Test
    fun `includes parent context`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val instant = Instant("2017-01-01 00:00:00")
                R4InstantValidator.validate(instant, LocationContext("Test", "field")).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Instant @ Test.field",
            exception.message,
        )
    }
}
