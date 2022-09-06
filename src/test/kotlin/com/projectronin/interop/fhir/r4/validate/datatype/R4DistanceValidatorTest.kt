package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.Distance
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4DistanceValidatorTest {
    @Test
    fun `fails if code provided without system`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val distance = Distance(value = 2.0, code = Code("code"))
                R4DistanceValidator.validate(distance).alertIfErrors()
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
            val distance = Distance(value = 2.0)
            R4DistanceValidator.validate(distance).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_DIST_001: There SHALL be a code if there is a value @ Distance",
            exception.message
        )
    }

    @Test
    fun `fails if system is provided and not UCUM`() {
        val exception = assertThrows<IllegalArgumentException> {
            val distance = Distance(system = Uri("SNOMED"))
            R4DistanceValidator.validate(distance).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_DIST_002: If system is present, it SHALL be UCUM @ Distance.system",
            exception.message
        )
    }

    @Test
    fun `base quantity failure includes parent context`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val distance = Distance(value = 2.0, code = Code("code"))
                R4DistanceValidator.validate(distance, LocationContext("Test", "field")).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_QUAN_001: If a code for the unit is present, the system SHALL also be present @ Test.field",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val distance = Distance(
            value = 17.5,
            system = CodeSystem.UCUM.uri,
            code = Code("mm")
        )
        R4DistanceValidator.validate(distance).alertIfErrors()
    }
}
