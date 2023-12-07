package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4UnsignedIntValidatorTest {
    @Test
    fun `accepts positive`() {
        val unsignedInt = UnsignedInt(1)
        R4UnsignedIntValidator.validate(unsignedInt).alertIfErrors()
    }

    @Test
    fun `accepts zero`() {
        val unsignedInt = UnsignedInt(0)
        R4UnsignedIntValidator.validate(unsignedInt).alertIfErrors()
    }

    @Test
    fun `fails on negative`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val unsignedInt = UnsignedInt(-1)
                R4UnsignedIntValidator.validate(unsignedInt).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a UnsignedInt",
            exception.message,
        )
    }

    @Test
    fun `includes parent context`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val unsignedInt = UnsignedInt(-1)
                R4UnsignedIntValidator.validate(unsignedInt, LocationContext("Test", "field")).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a UnsignedInt @ Test.field",
            exception.message,
        )
    }
}
