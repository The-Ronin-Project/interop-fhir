package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4DurationValidatorTest {
    @Test
    fun `fails if code provided without system`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val duration = Duration(value = 2.0, code = Code("code"))
                R4DurationValidator.validate(duration).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_QUAN_001: If a code for the unit is present, the system SHALL also be present @ Quantity",
            exception.message
        )
    }

    @Test
    fun `fails if value provided without code`() {
        val exception = assertThrows<IllegalArgumentException> {
            val duration = Duration(value = 2.0)
            R4DurationValidator.validate(duration).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_DUR_001: There SHALL be a code if there is a value @ Duration",
            exception.message
        )
    }

    @Test
    fun `fails if system is provided and not UCUM`() {
        val exception = assertThrows<IllegalArgumentException> {
            val duration = Duration(system = Uri("SNOMED"))
            R4DurationValidator.validate(duration).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_DUR_002: If system is present, it SHALL be UCUM @ Duration.system",
            exception.message
        )
    }

    @Test
    fun `base quantity failure includes parent context`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val duration = Duration(value = 2.0, code = Code("code"))
                R4DurationValidator.validate(duration, LocationContext("Test", "field")).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_QUAN_001: If a code for the unit is present, the system SHALL also be present @ Test.field",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val duration = Duration(
            value = 17.5,
            system = CodeSystem.UCUM.uri,
            code = Code("a")
        )
        R4DurationValidator.validate(duration).alertIfErrors()
    }
}
