package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4CodeValidatorTest {
    @Test
    fun `accepts valid value`() {
        val code = Code("Some Value")
        R4CodeValidator.validate(code).alertIfErrors()
    }

    @Test
    fun `fails on empty value`() {
        val exception = assertThrows<IllegalArgumentException> {
            val code = Code("")
            R4CodeValidator.validate(code).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Code",
            exception.message
        )
    }

    @Test
    fun `fails on leading whitespace`() {
        val exception = assertThrows<IllegalArgumentException> {
            val code = Code(" Value")
            R4CodeValidator.validate(code).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Code",
            exception.message
        )
    }

    @Test
    fun `fails on consecutive internal whitespace`() {
        val exception = assertThrows<IllegalArgumentException> {
            val code = Code("Some  Value")
            R4CodeValidator.validate(code).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Code",
            exception.message
        )
    }

    @Test
    fun `fails on trailing whitespace`() {
        val exception = assertThrows<IllegalArgumentException> {
            val code = Code("Value ")
            R4CodeValidator.validate(code).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Code",
            exception.message
        )
    }

    @Test
    fun `includes parent context`() {
        val exception = assertThrows<IllegalArgumentException> {
            val code = Code("Value ")
            R4CodeValidator.validate(code, LocationContext("Test", "field")).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Code @ Test.field",
            exception.message
        )
    }
}
