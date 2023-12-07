package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Oid
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4OidValidatorTest {
    @Test
    fun `accepts valid value`() {
        val oid = Oid("urn:oid:1.2.3.4.5")
        R4OidValidator.validate(oid).alertIfErrors()
    }

    @Test
    fun `fails on non-oid URN`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val oid = Oid("urn:uuid:1.2.3.4.5")
                R4OidValidator.validate(oid).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Oid",
            exception.message,
        )
    }

    @Test
    fun `fails on value with space`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val oid = Oid("urn:oid:1.2 3.4.5")
                R4OidValidator.validate(oid).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Oid",
            exception.message,
        )
    }

    @Test
    fun `fails on value with leading invalid digit`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val oid = Oid("urn:oid:5.4.3.2.1")
                R4OidValidator.validate(oid).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Oid",
            exception.message,
        )
    }

    @Test
    fun `includes parent context`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val oid = Oid("urn:oid:5.4.3.2.1")
                R4OidValidator.validate(oid, LocationContext("Test", "field")).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Oid @ Test.field",
            exception.message,
        )
    }
}
