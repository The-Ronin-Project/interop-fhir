package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Base64Binary
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4Base64BinaryValidatorTest {
    @Test
    fun `accepts valid value`() {
        val base64Binary = Base64Binary("abcdefgh")
        R4Base64BinaryValidator.validate(base64Binary).alertIfErrors()
    }

    @Test
    fun `accepts leading spaces`() {
        val base64Binary = Base64Binary("  abcd")
        R4Base64BinaryValidator.validate(base64Binary).alertIfErrors()
    }

    @Test
    fun `accepts trailing spaces`() {
        val base64Binary = Base64Binary("abcd  ")
        R4Base64BinaryValidator.validate(base64Binary).alertIfErrors()
    }

    @Test
    fun `fails on value with wrong number of values`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val base64Binary = Base64Binary("abc")
                R4Base64BinaryValidator.validate(base64Binary).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Base64Binary",
            exception.message,
        )
    }

    @Test
    fun `includes parent context`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val base64Binary = Base64Binary("abc")
                R4Base64BinaryValidator.validate(base64Binary, LocationContext("Test", "field")).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Base64Binary @ Test.field",
            exception.message,
        )
    }
}
