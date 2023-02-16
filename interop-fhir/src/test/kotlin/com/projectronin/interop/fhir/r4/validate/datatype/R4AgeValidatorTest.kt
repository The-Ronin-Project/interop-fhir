package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.Age
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4AgeValidatorTest {
    @Test
    fun `fails if code provided without system`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val age = Age(value = Decimal(2.0), code = Code("code"))
                R4AgeValidator.validate(age).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_QUAN_001: If a code for the unit is present, the system SHALL also be present @ Quantity",
            exception.message
        )
    }

    @Test
    fun `fails if value provided without code`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val age = Age(value = Decimal(2.0), system = CodeSystem.UCUM.uri)
                R4AgeValidator.validate(age).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_AGE_001: There SHALL be a code if there is a value @ Age",
            exception.message
        )
    }

    @Test
    fun `fails if system is provided and not UCUM`() {
        val exception = assertThrows<IllegalArgumentException> {
            val age = Age(system = Uri("SNOMED"))
            R4AgeValidator.validate(age).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_AGE_002: If system is present, it SHALL be UCUM @ Age.system",
            exception.message
        )
    }

    @Test
    fun `fails if value is zero`() {
        val exception = assertThrows<IllegalArgumentException> {
            val age = Age(code = Code("code"), system = CodeSystem.UCUM.uri, value = Decimal(0.0))
            R4AgeValidator.validate(age).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_AGE_003: If value is present, it SHALL be positive @ Age.value",
            exception.message
        )
    }

    @Test
    fun `fails if value is negative`() {
        val exception = assertThrows<IllegalArgumentException> {
            val age = Age(code = Code("code"), system = CodeSystem.UCUM.uri, value = Decimal(-3.0))
            R4AgeValidator.validate(age).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_AGE_003: If value is present, it SHALL be positive @ Age.value",
            exception.message
        )
    }

    @Test
    fun `base quantity failure includes parent context`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val age = Age(value = Decimal(2.0), code = Code("code"))
                R4AgeValidator.validate(age, LocationContext("Test", "field")).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_QUAN_001: If a code for the unit is present, the system SHALL also be present @ Test.field",
            exception.message
        )
    }

    @Test
    fun `age failure includes parent context`() {
        val exception = assertThrows<IllegalArgumentException> {
            val age = Age(code = Code("code"), system = CodeSystem.UCUM.uri, value = Decimal(0.0))
            R4AgeValidator.validate(age, LocationContext("Test", "field")).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_AGE_003: If value is present, it SHALL be positive @ Test.field.value",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val age = Age(
            value = Decimal(17.0),
            system = CodeSystem.UCUM.uri,
            code = Code("a")
        )
        R4AgeValidator.validate(age).alertIfErrors()
    }
}
