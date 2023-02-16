package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4UriValidatorTest {
    @Test
    fun `accepts valid value`() {
        val uri = Uri("urn:uuid:53fefa32-fcbb-4ff8-8a92-55ee120877b7")
        R4UriValidator.validate(uri).alertIfErrors()
    }

    @Test
    fun `fails on value with space`() {
        val exception = assertThrows<IllegalArgumentException> {
            val uri = Uri("Some value")
            R4UriValidator.validate(uri).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Uri",
            exception.message
        )
    }

    @Test
    fun `includes parent context`() {
        val exception = assertThrows<IllegalArgumentException> {
            val uri = Uri("Some value")
            R4UriValidator.validate(uri, LocationContext("Test", "field")).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Uri @ Test.field",
            exception.message
        )
    }
}
