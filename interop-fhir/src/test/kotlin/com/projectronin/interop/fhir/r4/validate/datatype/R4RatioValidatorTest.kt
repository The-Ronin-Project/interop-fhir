package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4RatioValidatorTest {
    @Test
    fun `fails if non-null numerator and null denominator`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val ratio = Ratio(numerator = Quantity(value = Decimal(3.0)))
                R4RatioValidator.validate(ratio).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_RATIO_001: denominator required when numerator present @ Ratio",
            exception.message
        )
    }

    @Test
    fun `fails if null numerator and non-null denominator`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val ratio = Ratio(
                    extension = listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                        )
                    ),
                    denominator = Quantity(value = Decimal(3.0))
                )
                R4RatioValidator.validate(ratio).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_RATIO_002: numerator required when denominator present @ Ratio",
            exception.message
        )
    }

    @Test
    fun `fails if no numerator or denominator and no extension`() {
        val exception = assertThrows<IllegalArgumentException> {
            val ratio = Ratio()
            R4RatioValidator.validate(ratio).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_RATIO_003: extension required if no numerator and denominator @ Ratio",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val ratio = Ratio(numerator = Quantity(value = Decimal(3.0)), denominator = Quantity(value = Decimal(4.0)))
        R4RatioValidator.validate(ratio).alertIfErrors()
    }
}
