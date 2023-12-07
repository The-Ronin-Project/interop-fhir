package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.Count
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.validate.datatype.R4CountValidator
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4CountValidatorTest {
    @Test
    fun `fails if code provided without system`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val count = Count(value = Decimal(2.0), code = Code("1"))
                R4CountValidator.validate(count).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_QUAN_001: If a code for the unit is present, the system SHALL also be present @ Quantity",
            exception.message,
        )
    }

    @Test
    fun `fails if value provided without code`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val count = Count(value = Decimal(2.0), system = CodeSystem.UCUM.uri)
                R4CountValidator.validate(count).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CNT_001: There SHALL be a code with a value of \"1\" if there is a value @ Count",
            exception.message,
        )
    }

    @Test
    fun `fails if value provided with invalid code`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val count = Count(value = Decimal(2.0), code = Code("code-value"), system = CodeSystem.UCUM.uri)
                R4CountValidator.validate(count).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CNT_001: There SHALL be a code with a value of \"1\" if there is a value @ Count",
            exception.message,
        )
    }

    @Test
    fun `fails if system is provided and not UCUM`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val count = Count(system = Uri("SNOMED"))
                R4CountValidator.validate(count).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CNT_002: If system is present, it SHALL be UCUM @ Count.system",
            exception.message,
        )
    }

    @Test
    fun `fails if value is non-whole number`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val count = Count(code = Code("1"), system = CodeSystem.UCUM.uri, value = Decimal(1.2))
                R4CountValidator.validate(count).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CNT_003: If present, the value SHALL be a whole number @ Count.value",
            exception.message,
        )
    }

    @Test
    fun `base quantity failure includes parent context`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val count = Count(value = Decimal(2.0), code = Code("1"))
                R4CountValidator.validate(count, LocationContext("Test", "field")).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_QUAN_001: If a code for the unit is present, the system SHALL also be present @ Test.field",
            exception.message,
        )
    }

    @Test
    fun `validates successfully`() {
        val count =
            Count(
                value = Decimal(17.0),
                system = CodeSystem.UCUM.uri,
                code = Code("1"),
            )
        R4CountValidator.validate(count).alertIfErrors()
    }
}
