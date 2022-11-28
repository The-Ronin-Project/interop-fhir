package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.valueset.ContactPointSystem
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ContactPointValidatorTest {
    @Test
    fun `fails if system is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val contactPoint = ContactPoint(
                value = FHIRString("name@site.com"),
                system = Code("unsupported-system")
            )
            R4ContactPointValidator.validate(contactPoint).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: system is outside of required value set @ ContactPoint.system",
            exception.message
        )
    }

    @Test
    fun `fails if use is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val contactPoint = ContactPoint(
                value = FHIRString("name@site.com"),
                system = ContactPointSystem.PHONE.asCode(),
                use = Code("unsupported-use")
            )
            R4ContactPointValidator.validate(contactPoint).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: use is outside of required value set @ ContactPoint.use",
            exception.message
        )
    }

    @Test
    fun `fails if value provided and no system is provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val contactPoint = ContactPoint(
                value = FHIRString("name@site.com")
            )
            R4ContactPointValidator.validate(contactPoint).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CNTCTPT_001: A system is required if a value is provided @ ContactPoint",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val contactPoint = ContactPoint(
            value = FHIRString("name@site.com"),
            system = ContactPointSystem.EMAIL.asCode()
        )
        R4ContactPointValidator.validate(contactPoint).alertIfErrors()
    }

    @Test
    fun `validates successfully with no value`() {
        val contactPoint = ContactPoint(id = FHIRString("1234"))
        R4ContactPointValidator.validate(contactPoint).alertIfErrors()
    }
}
