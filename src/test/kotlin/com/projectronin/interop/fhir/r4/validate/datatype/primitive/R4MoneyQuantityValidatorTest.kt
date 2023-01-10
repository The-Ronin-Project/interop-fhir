package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.MoneyQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.validate.datatype.R4MoneyQuantityValidator
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4MoneyQuantityValidatorTest {
    @Test
    fun `fails if code provided without system`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val quantity = MoneyQuantity(value = Decimal(2.0), code = Code("USD"))
                R4MoneyQuantityValidator.validate(quantity).alertIfErrors()
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
                val quantity = MoneyQuantity(value = Decimal(2.0))
                R4MoneyQuantityValidator.validate(quantity).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_MONQUAN_001: There SHALL be a code if there is a value @ MoneyQuantity",
            exception.message
        )
    }

    @Test
    fun `fails if system is provided and not CURRENCY`() {
        val exception = assertThrows<IllegalArgumentException> {
            val quantity = MoneyQuantity(system = Uri("SNOMED"))
            R4MoneyQuantityValidator.validate(quantity).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_MONQUAN_002: If system is present, it SHALL be CURRENCY @ MoneyQuantity.system",
            exception.message
        )
    }

    @Test
    fun `base quantity failure includes parent context`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val quantity = MoneyQuantity(value = Decimal(2.0), code = Code("USD"))
                R4MoneyQuantityValidator.validate(quantity, LocationContext("Test", "field")).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_QUAN_001: If a code for the unit is present, the system SHALL also be present @ Test.field",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val quantity = MoneyQuantity(
            value = Decimal(17.5),
            system = CodeSystem.CURRENCY.uri,
            code = Code("USD")
        )
        R4MoneyQuantityValidator.validate(quantity).alertIfErrors()
    }
}
