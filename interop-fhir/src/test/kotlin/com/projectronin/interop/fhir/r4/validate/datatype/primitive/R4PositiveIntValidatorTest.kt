package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4PositiveIntValidatorTest {
    @Test
    fun `accepts positive`() {
        val positiveInt = PositiveInt(1)
        R4PositiveIntValidator.validate(positiveInt).alertIfErrors()
    }

    @Test
    fun `fails on zero`() {
        val exception = assertThrows<IllegalArgumentException> {
            val positiveInt = PositiveInt(0)
            R4PositiveIntValidator.validate(positiveInt).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a PositiveInt",
            exception.message
        )
    }

    @Test
    fun `fails on negative`() {
        val exception = assertThrows<IllegalArgumentException> {
            val positiveInt = PositiveInt(-1)
            R4PositiveIntValidator.validate(positiveInt).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a PositiveInt",
            exception.message
        )
    }

    @Test
    fun `includes parent context`() {
        val exception = assertThrows<IllegalArgumentException> {
            val positiveInt = PositiveInt(-1)
            R4PositiveIntValidator.validate(positiveInt, LocationContext("Test", "field")).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a PositiveInt @ Test.field",
            exception.message
        )
    }

    @Test
    fun `handles null`() {
        // doesn't try to validate if value is null
        val positiveInt = PositiveInt(null)
        R4PositiveIntValidator.validate(positiveInt).alertIfErrors()
    }
}
