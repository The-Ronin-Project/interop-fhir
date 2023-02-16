package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4TimeValidatorTest {
    @Test
    fun `accepts valid time`() {
        val time = Time("10:00:25")
        R4TimeValidator.validate(time).alertIfErrors()
    }

    @Test
    fun `accepts valid time with microseconds`() {
        val time = Time("10:00:25.1234")
        R4TimeValidator.validate(time).alertIfErrors()
    }

    @Test
    fun `fails on value without seconds`() {
        val exception = assertThrows<IllegalArgumentException> {
            val time = Time("09:20")
            R4TimeValidator.validate(time).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Time",
            exception.message
        )
    }

    @Test
    fun `fails on value without minutes`() {
        val exception = assertThrows<IllegalArgumentException> {
            val time = Time("08")
            R4TimeValidator.validate(time).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Time",
            exception.message
        )
    }

    @Test
    fun `fails on 2400`() {
        val exception = assertThrows<IllegalArgumentException> {
            val time = Time("24:00:00")
            R4TimeValidator.validate(time).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Time",
            exception.message
        )
    }

    @Test
    fun `includes parent context`() {
        val exception = assertThrows<IllegalArgumentException> {
            val time = Time("24:00:00")
            R4TimeValidator.validate(time, LocationContext("Test", "field")).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Time @ Test.field",
            exception.message
        )
    }
}
