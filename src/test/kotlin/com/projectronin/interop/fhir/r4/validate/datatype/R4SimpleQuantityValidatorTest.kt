package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4SimpleQuantityValidatorTest {
    @Test
    fun `fails if code is provided without system`() {
        val exception = assertThrows<IllegalArgumentException> {
            val simpleQuantity = SimpleQuantity(
                value = 60.0,
                unit = "mL/min/1.73m2",
                code = Code("mL/min/{1.73_m2}"),
            )
            R4SimpleQuantityValidator.validate(simpleQuantity).alertIfErrors()
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
            val simpleQuantity = SimpleQuantity(
                value = 60.0,
                unit = "mL/min/1.73m2",
                code = Code("mL/min/{1.73_m2}"),
            )
            R4SimpleQuantityValidator.validate(simpleQuantity, LocationContext("Test", "field")).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_QUAN_001: If a code for the unit is present, the system SHALL also be present @ Test.field",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val simpleQuantity = SimpleQuantity(
            id = "12345",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            value = 17.5,
        )
        R4SimpleQuantityValidator.validate(simpleQuantity).alertIfErrors()
    }
}
