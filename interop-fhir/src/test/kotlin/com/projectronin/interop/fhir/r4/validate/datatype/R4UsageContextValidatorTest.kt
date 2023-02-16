package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.UsageContext
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4UsageContextValidatorTest {
    @Test
    fun `fails for no code provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val usageContext = UsageContext(
                    code = null,
                    value = DynamicValue(DynamicValueType.REFERENCE, Reference(reference = FHIRString("reference")))
                )
                R4UsageContextValidator.validate(usageContext).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: code is a required element @ UsageContext.code",
            exception.message
        )
    }

    @Test
    fun `fails for no value provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val usageContext = UsageContext(
                    code = Coding(display = FHIRString("code")),
                    value = null
                )
                R4UsageContextValidator.validate(usageContext).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: value is a required element @ UsageContext.value",
            exception.message
        )
    }

    @Test
    fun `fails for unsupported value dynamic value type`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val usageContext = UsageContext(
                    code = Coding(display = FHIRString("code")),
                    value = DynamicValue(DynamicValueType.INTEGER, FHIRInteger(1))
                )
                R4UsageContextValidator.validate(usageContext).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: value can only be one of the following: CodeableConcept, Quantity, Range, Reference @ UsageContext.value",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val usageContext = UsageContext(
            code = Coding(display = FHIRString("code")),
            value = DynamicValue(DynamicValueType.QUANTITY, Quantity(value = Decimal(1.0)))
        )
        R4UsageContextValidator.validate(usageContext).alertIfErrors()
    }
}
