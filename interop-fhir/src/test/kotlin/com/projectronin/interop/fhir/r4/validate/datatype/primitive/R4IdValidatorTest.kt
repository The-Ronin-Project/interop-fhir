package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4IdValidatorTest {
    @Test
    fun `accepts valid value`() {
        val id = Id("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-.")
        R4IdValidator.validate(id).alertIfErrors()
    }

    @Test
    fun `fails on underscore`() {
        val exception = assertThrows<IllegalArgumentException> {
            val id = Id("app_o_snd-ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-.")
            R4IdValidator.validate(id).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Id",
            exception.message
        )
    }

    @Test
    fun `fails on empty value`() {
        val exception = assertThrows<IllegalArgumentException> {
            val id = Id("")
            R4IdValidator.validate(id).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Id",
            exception.message
        )
    }

    @Test
    fun `accepts value over 64 characters`() {
        val id = Id("A".repeat(65))
        R4IdValidator.validate(id).alertIfErrors()
    }

    @Test
    fun `fails on invalid character`() {
        val exception = assertThrows<IllegalArgumentException> {
            val id = Id("@")
            R4IdValidator.validate(id).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Id",
            exception.message
        )
    }

    @Test
    fun `includes parent context`() {
        val exception = assertThrows<IllegalArgumentException> {
            val id = Id("@")
            R4IdValidator.validate(id, LocationContext("Test", "field")).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\nERROR R4_INV_PRIM: Supplied value is not valid for a Id @ Test.field",
            exception.message
        )
    }
}
