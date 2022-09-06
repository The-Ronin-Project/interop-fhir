package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.valueset.QuantityComparator
import com.projectronin.interop.fhir.util.asCode
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4QuantityValidatorTest {
    @Test
    fun `fails if comparator is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val quantity = Quantity(
                comparator = Code("unsupported-comparator"),
                value = 17.5,
                system = CodeSystem.UCUM.uri
            )
            R4QuantityValidator.validate(quantity).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: comparator is outside of required value set @ Quantity.comparator",
            exception.message
        )
    }

    @Test
    fun `fails if code is provided without system`() {
        val exception = assertThrows<IllegalArgumentException> {
            val quantity = Quantity(
                value = 60.0,
                unit = "mL/min/1.73m2",
                code = Code("mL/min/{1.73_m2}"),
            )
            R4QuantityValidator.validate(quantity).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_QUAN_001: If a code for the unit is present, the system SHALL also be present @ Quantity",
            exception.message
        )
    }

    @Test
    fun `base quantity failure includes parent context`() {
        val exception = assertThrows<IllegalArgumentException> {
            val quantity = Quantity(
                comparator = Code("unsupported-comparator"),
                value = 17.5,
                system = CodeSystem.UCUM.uri
            )
            R4QuantityValidator.validate(quantity, LocationContext("Test", "field")).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: comparator is outside of required value set @ Test.field.comparator",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val quantity = Quantity(
            comparator = QuantityComparator.GREATER_OR_EQUAL_TO.asCode(),
            value = 17.5,
            system = CodeSystem.UCUM.uri
        )
        R4QuantityValidator.validate(quantity).alertIfErrors()
    }
}
