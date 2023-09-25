package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.resource.MedicationRequest
import com.projectronin.interop.fhir.r4.valueset.MedicationRequestIntent
import com.projectronin.interop.fhir.r4.valueset.MedicationRequestStatus
import com.projectronin.interop.fhir.r4.valueset.RequestPriority
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4MedicationRequestValidatorTest {
    @Test
    fun `fails if no status provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val medicationRequest = MedicationRequest(
                status = null,
                intent = MedicationRequestIntent.FILLER_ORDER.asCode(),
                medication = DynamicValue(
                    DynamicValueType.CODEABLE_CONCEPT,
                    CodeableConcept(text = FHIRString("medication"))
                ),
                subject = Reference(reference = FHIRString("Patient/1234"))
            )
            R4MedicationRequestValidator.validate(medicationRequest).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ MedicationRequest.status",
            exception.message
        )
    }

    @Test
    fun `fails for status outside value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val medicationRequest = MedicationRequest(
                status = Code("invalid-status"),
                intent = MedicationRequestIntent.FILLER_ORDER.asCode(),
                medication = DynamicValue(
                    DynamicValueType.CODEABLE_CONCEPT,
                    CodeableConcept(text = FHIRString("medication"))
                ),
                subject = Reference(reference = FHIRString("Patient/1234"))
            )
            R4MedicationRequestValidator.validate(medicationRequest).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'invalid-status' is outside of required value set @ MedicationRequest.status",
            exception.message
        )
    }

    @Test
    fun `fails if no intent provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val medicationRequest = MedicationRequest(
                status = MedicationRequestStatus.COMPLETED.asCode(),
                intent = null,
                medication = DynamicValue(
                    DynamicValueType.CODEABLE_CONCEPT,
                    CodeableConcept(text = FHIRString("medication"))
                ),
                subject = Reference(reference = FHIRString("Patient/1234"))
            )
            R4MedicationRequestValidator.validate(medicationRequest).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: intent is a required element @ MedicationRequest.intent",
            exception.message
        )
    }

    @Test
    fun `fails for intent outside value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val medicationRequest = MedicationRequest(
                status = MedicationRequestStatus.COMPLETED.asCode(),
                intent = Code("invalid-intent"),
                medication = DynamicValue(
                    DynamicValueType.CODEABLE_CONCEPT,
                    CodeableConcept(text = FHIRString("medication"))
                ),
                subject = Reference(reference = FHIRString("Patient/1234"))
            )
            R4MedicationRequestValidator.validate(medicationRequest).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'invalid-intent' is outside of required value set @ MedicationRequest.intent",
            exception.message
        )
    }

    @Test
    fun `fails for priority outside value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val medicationRequest = MedicationRequest(
                status = MedicationRequestStatus.COMPLETED.asCode(),
                intent = MedicationRequestIntent.FILLER_ORDER.asCode(),
                medication = DynamicValue(
                    DynamicValueType.CODEABLE_CONCEPT,
                    CodeableConcept(text = FHIRString("medication"))
                ),
                subject = Reference(reference = FHIRString("Patient/1234")),
                priority = Code("invalid-priority")
            )
            R4MedicationRequestValidator.validate(medicationRequest).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'invalid-priority' is outside of required value set @ MedicationRequest.priority",
            exception.message
        )
    }

    @Test
    fun `fails for unsupported reported dynamic value type`() {
        val exception = assertThrows<IllegalArgumentException> {
            val medicationRequest = MedicationRequest(
                status = MedicationRequestStatus.COMPLETED.asCode(),
                intent = MedicationRequestIntent.FILLER_ORDER.asCode(),
                medication = DynamicValue(
                    DynamicValueType.CODEABLE_CONCEPT,
                    CodeableConcept(text = FHIRString("medication"))
                ),
                subject = Reference(reference = FHIRString("Patient/1234")),
                reported = DynamicValue(DynamicValueType.STRING, FHIRString("reported"))
            )
            R4MedicationRequestValidator.validate(medicationRequest).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: reported can only be one of the following: Boolean, Reference @ MedicationRequest.reported",
            exception.message
        )
    }

    @Test
    fun `fails if no medication provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val medicationRequest = MedicationRequest(
                status = MedicationRequestStatus.COMPLETED.asCode(),
                intent = MedicationRequestIntent.FILLER_ORDER.asCode(),
                medication = null,
                subject = Reference(reference = FHIRString("Patient/1234"))
            )
            R4MedicationRequestValidator.validate(medicationRequest).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: medication is a required element @ MedicationRequest.medication",
            exception.message
        )
    }

    @Test
    fun `fails for unsupported medication dynamic value type`() {
        val exception = assertThrows<IllegalArgumentException> {
            val medicationRequest = MedicationRequest(
                status = MedicationRequestStatus.COMPLETED.asCode(),
                intent = MedicationRequestIntent.FILLER_ORDER.asCode(),
                medication = DynamicValue(DynamicValueType.BOOLEAN, false),
                subject = Reference(reference = FHIRString("Patient/1234"))
            )
            R4MedicationRequestValidator.validate(medicationRequest).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: medication can only be one of the following: CodeableConcept, Reference @ MedicationRequest.medication",
            exception.message
        )
    }

    @Test
    fun `fails if no subject provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val medicationRequest = MedicationRequest(
                status = MedicationRequestStatus.COMPLETED.asCode(),
                intent = MedicationRequestIntent.FILLER_ORDER.asCode(),
                medication = DynamicValue(
                    DynamicValueType.CODEABLE_CONCEPT,
                    CodeableConcept(text = FHIRString("medication"))
                ),
                subject = null
            )
            R4MedicationRequestValidator.validate(medicationRequest).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: subject is a required element @ MedicationRequest.subject",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val medicationRequest = MedicationRequest(
            status = MedicationRequestStatus.COMPLETED.asCode(),
            intent = MedicationRequestIntent.FILLER_ORDER.asCode(),
            medication = DynamicValue(
                DynamicValueType.CODEABLE_CONCEPT,
                CodeableConcept(text = FHIRString("medication"))
            ),
            subject = Reference(reference = FHIRString("Patient/1234")),
            priority = RequestPriority.ASAP.asCode(),
            reported = DynamicValue(DynamicValueType.BOOLEAN, false)
        )
        R4MedicationRequestValidator.validate(medicationRequest).alertIfErrors()
    }
}
