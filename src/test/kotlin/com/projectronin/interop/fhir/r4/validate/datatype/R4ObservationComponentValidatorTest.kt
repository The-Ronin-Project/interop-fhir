package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.ObservationComponent
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ObservationComponentValidatorTest {
    @Test
    fun `fails if code is not provided`() {
        val ex = assertThrows<IllegalArgumentException> {
            val component = ObservationComponent(
                code = null,
                value = DynamicValue(DynamicValueType.BOOLEAN, true)
            )
            R4ObservationComponentValidator.validate(component).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: code is a required element @ ObservationComponent.code",
            ex.message
        )
    }

    @Test
    fun `fails if value outside supported types`() {
        val ex = assertThrows<IllegalArgumentException> {
            val component = ObservationComponent(
                code = CodeableConcept(text = "code"),
                value = DynamicValue(DynamicValueType.DECIMAL, Decimal(1.2))
            )
            R4ObservationComponentValidator.validate(component).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: value can only be one of the following: Quantity, CodeableConcept, String, Boolean, Integer, Range, Ratio, SampledData, Time, DateTime, Period @ ObservationComponent.value",
            ex.message
        )
    }

    @Test
    fun `fails if dataAbsentReason and value are present`() {
        val ex = assertThrows<IllegalArgumentException> {
            val component = ObservationComponent(
                code = CodeableConcept(text = "code"),
                value = DynamicValue(DynamicValueType.BOOLEAN, true),
                dataAbsentReason = CodeableConcept(text = "unable to reach vein"),
            )
            R4ObservationComponentValidator.validate(component).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_OBSCOM_001: dataAbsentReason SHALL only be present if value[x] is not present @ ObservationComponent",
            ex.message
        )
    }

    @Test
    fun `validates successfully`() {
        val component = ObservationComponent(
            code = CodeableConcept(text = "code"),
            value = DynamicValue(DynamicValueType.BOOLEAN, true)
        )
        R4ObservationComponentValidator.validate(component).alertIfErrors()
    }
}
