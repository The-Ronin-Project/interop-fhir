package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.ObservationReferenceRange
import com.projectronin.interop.fhir.r4.datatype.Range
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ObservationReferenceRangeValidatorTest {
    @Test
    fun `fails if no low, high or text`() {
        val ex = assertThrows<IllegalArgumentException> {
            val referenceRange = ObservationReferenceRange(
                age = Range(low = SimpleQuantity(value = 15.0))
            )
            R4ObservationReferenceRangeValidator.validate(referenceRange).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_OBSREFRNG_001: referenceRange must have at least a low or a high or text @ ObservationReferenceRange",
            ex.message
        )
    }

    @Test
    fun `validates successfully with low`() {
        val referenceRange = ObservationReferenceRange(
            low = SimpleQuantity(value = 15.0)
        )
        R4ObservationReferenceRangeValidator.validate(referenceRange).alertIfErrors()
    }

    @Test
    fun `validates successfully with high`() {
        val referenceRange = ObservationReferenceRange(
            high = SimpleQuantity(value = 15.0)
        )
        R4ObservationReferenceRangeValidator.validate(referenceRange).alertIfErrors()
    }

    @Test
    fun `validates successfully with text`() {
        val referenceRange = ObservationReferenceRange(
            text = "text"
        )
        R4ObservationReferenceRangeValidator.validate(referenceRange).alertIfErrors()
    }
}
