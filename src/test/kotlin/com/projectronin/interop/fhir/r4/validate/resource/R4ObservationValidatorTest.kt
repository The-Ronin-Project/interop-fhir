package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.ObservationComponent
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Observation
import com.projectronin.interop.fhir.r4.valueset.ObservationStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ObservationValidatorTest {
    @Test
    fun `status is not provided`() {
        val ex = assertThrows<IllegalArgumentException> {
            val observation = Observation(
                status = null,
                code = CodeableConcept(text = FHIRString("code"))
            )
            R4ObservationValidator.validate(observation).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ Observation.status",
            ex.message
        )
    }

    @Test
    fun `status is outside of required value set`() {
        val ex = assertThrows<IllegalArgumentException> {
            val observation = Observation(
                status = Code("unsupported-status"),
                code = CodeableConcept(text = FHIRString("code"))
            )
            R4ObservationValidator.validate(observation).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-status' is outside of required value set @ Observation.status",
            ex.message
        )
    }

    @Test
    fun `code is not provided`() {
        val ex = assertThrows<IllegalArgumentException> {
            val observation = Observation(
                status = ObservationStatus.CANCELLED.asCode(),
                code = null
            )
            R4ObservationValidator.validate(observation).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: code is a required element @ Observation.code",
            ex.message
        )
    }

    @Test
    fun `dataAbsentReason SHALL only be present if value is not present`() {
        val quantity = Quantity(
            value = Decimal(60.0),
            unit = FHIRString("mL/min/1.73m2"),
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )
        val ex = assertThrows<IllegalArgumentException> {
            val observation = Observation(
                status = ObservationStatus.CANCELLED.asCode(),
                code = CodeableConcept(text = FHIRString("code")),
                subject = Reference(display = FHIRString("Peter Chalmers")),
                value = DynamicValue(DynamicValueType.QUANTITY, quantity),
                dataAbsentReason = CodeableConcept(text = FHIRString("unable to reach vein")),
            )
            R4ObservationValidator.validate(observation).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_OBS_001: dataAbsentReason SHALL only be present if value[x] is not present @ Observation",
            ex.message
        )
    }

    @Test
    fun `If Observation code is the same as an Observation component code then the Observation value SHALL NOT be present`() {
        val quantity = Quantity(
            value = Decimal(60.0),
            unit = FHIRString("mL/min/1.73m2"),
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )
        val testCodeableConcept1 = CodeableConcept(coding = listOf(Coding(code = Code("code1"))))
        val testCodeableConcept2 = CodeableConcept(coding = listOf(Coding(code = Code("code2"))))
        val component1 = ObservationComponent(
            code = testCodeableConcept1,
            value = DynamicValue(DynamicValueType.QUANTITY, quantity)
        )
        val component2 = ObservationComponent(
            code = testCodeableConcept2,
            value = DynamicValue(DynamicValueType.QUANTITY, quantity)
        )
        val ex = assertThrows<IllegalArgumentException> {
            val observation = Observation(
                status = ObservationStatus.FINAL.asCode(),
                code = testCodeableConcept1,
                subject = Reference(display = FHIRString("Peter Chalmers")),
                value = (DynamicValue(DynamicValueType.QUANTITY, quantity)),
                component = listOf(component1, component2)
            )
            R4ObservationValidator.validate(observation).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_OBS_002: If Observation.code is the same as an Observation.component.code then the Observation.value SHALL NOT be present @ Observation",
            ex.message
        )
    }

    @Test
    fun `effective time with unsupported dynamic value type`() {
        val quantity = Quantity(
            value = Decimal(60.0),
            unit = FHIRString("mL/min/1.73m2"),
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )
        val ex = assertThrows<IllegalArgumentException> {
            val observation = Observation(
                status = ObservationStatus.FINAL.asCode(),
                code = CodeableConcept(text = FHIRString("code")),
                subject = Reference(reference = FHIRString("subject")),
                effective = DynamicValue(type = DynamicValueType.BOOLEAN, value = false),
                value = (DynamicValue(DynamicValueType.QUANTITY, quantity)),
            )
            R4ObservationValidator.validate(observation).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: effective can only be one of the following: DateTime, Period, Timing, Instant @ Observation.effective",
            ex.message
        )
    }

    @Test
    fun `succeeds when there is a Observation value and no Observation code is the same as any Observation component code`() {
        val quantity = Quantity(
            value = Decimal(60.0),
            unit = FHIRString("mL/min/1.73m2"),
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )
        val testCodeableConcept1 = CodeableConcept(coding = listOf(Coding(code = Code("code1"))))
        val testCodeableConcept2 = CodeableConcept(coding = listOf(Coding(code = Code("code2"))))
        val component2 = ObservationComponent(
            code = testCodeableConcept2,
            value = DynamicValue(DynamicValueType.QUANTITY, quantity)
        )
        val observation = Observation(
            status = ObservationStatus.FINAL.asCode(),
            code = testCodeableConcept1,
            subject = Reference(display = FHIRString("Peter Chalmers")),
            value = (DynamicValue(DynamicValueType.QUANTITY, quantity)),
            component = listOf(component2, component2)
        )
        R4ObservationValidator.validate(observation).alertIfErrors()
    }
}
