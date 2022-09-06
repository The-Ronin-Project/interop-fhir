package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Uuid
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4UuidValidatorTest {
    @Test
    fun `accepts valid value`() {
        val uuid = Uuid("urn:uuid:12345678-90ab-cdef-1234-567890abcdef")
        R4UuidValidator.validate(uuid).alertIfErrors()
    }

    @Test
    fun `fails on non-uuid URN`() {
        val exception = assertThrows<IllegalArgumentException> {
            val uuid = Uuid("urn:oid:12345678-90ab-cdef-1234-567890abcdef")
            R4UuidValidator.validate(uuid).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Uuid",
            exception.message
        )
    }

    @Test
    fun `fails on value with space`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val uuid = Uuid("urn:uuid:12345678-90ab-cd ef-1234-567890abcdef")
                R4UuidValidator.validate(uuid).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Uuid",
            exception.message
        )
    }

    @Test
    fun `fails on value with too few characters`() {
        val exception = assertThrows<IllegalArgumentException> {
            val uuid = Uuid("urn:uuid:12345678-90ab-cdef-1234-567890abcd")
            R4UuidValidator.validate(uuid).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Uuid",
            exception.message
        )
    }

    @Test
    fun `fails on value with invalid pattern`() {
        val exception = assertThrows<IllegalArgumentException> {
            val uuid = Uuid("urn:uuid:12345678-90ab-cdef-12345678-90abcdef")
            R4UuidValidator.validate(uuid).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Uuid",
            exception.message
        )
    }

    @Test
    fun `includes parent context`() {
        val exception = assertThrows<IllegalArgumentException> {
            val uuid = Uuid("urn:uuid:12345678-90ab-cdef-12345678-90abcdef")
            R4UuidValidator.validate(uuid, LocationContext("Test", "field")).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Uuid @ Test.field",
            exception.message
        )
    }
}
