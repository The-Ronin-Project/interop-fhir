package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Range
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4RangeValidatorTest {
    @Test
    fun `fails if low has a higher value than high`() {
        val exception = assertThrows<IllegalArgumentException> {
            val range = Range(low = SimpleQuantity(value = Decimal(1.0)), high = SimpleQuantity(value = Decimal(0.5)))
            R4RangeValidator.validate(range).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_RANGE_001: If present, low SHALL have a lower value than high @ Range",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val range = Range(low = SimpleQuantity(value = Decimal(1.0)))
        R4RangeValidator.validate(range).alertIfErrors()
    }
}
