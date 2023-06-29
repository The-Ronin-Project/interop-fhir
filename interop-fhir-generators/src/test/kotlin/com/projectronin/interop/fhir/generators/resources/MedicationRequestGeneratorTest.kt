package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.DynamicValues
import com.projectronin.interop.fhir.generators.datatypes.annotation
import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.dosage
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.period
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.dateTime
import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import com.projectronin.interop.fhir.r4.valueset.MedicationRequestIntent
import com.projectronin.interop.fhir.r4.valueset.MedicationRequestStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MedicationRequestGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val medicationRequest = medicationRequest {}
        assertNull(medicationRequest.id)
        assertNull(medicationRequest.meta)
        assertNull(medicationRequest.implicitRules)
        assertNull(medicationRequest.language)
        assertNull(medicationRequest.text)
        assertEquals(0, medicationRequest.contained.size)
        assertEquals(0, medicationRequest.extension.size)
        assertEquals(0, medicationRequest.modifierExtension.size)
        assertTrue(medicationRequest.identifier.isEmpty())
        assertNotNull(medicationRequest.status)
        medicationRequest.status?.value?.let { status ->
            assertNotNull(
                MedicationRequestStatus.values().firstOrNull { status == it.code }
            )
        }
        assertNull(medicationRequest.statusReason)
        assertNotNull(medicationRequest.intent)
        medicationRequest.intent?.value?.let { intent ->
            assertNotNull(
                MedicationRequestIntent.values().firstOrNull { intent == it.code }
            )
        }
        assertTrue(medicationRequest.category.isEmpty())
        assertNull(medicationRequest.priority)
        assertNull(medicationRequest.doNotPerform)
        assertNull(medicationRequest.reported)
        assertEquals(DynamicValueType.REFERENCE, medicationRequest.medication?.type)
        assertNotNull(medicationRequest.subject)
        assertNull(medicationRequest.encounter)
        assertTrue(medicationRequest.supportingInformation.isEmpty())
        assertNull(medicationRequest.authoredOn)
        assertNull(medicationRequest.requester)
        assertNull(medicationRequest.performer)
        assertNull(medicationRequest.performerType)
        assertNull(medicationRequest.recorder)
        assertTrue(medicationRequest.reasonCode.isEmpty())
        assertTrue(medicationRequest.reasonReference.isEmpty())
        assertTrue(medicationRequest.instantiatesCanonical.isEmpty())
        assertTrue(medicationRequest.instantiatesUri.isEmpty())
        assertTrue(medicationRequest.basedOn.isEmpty())
        assertNull(medicationRequest.groupIdentifier)
        assertNull(medicationRequest.courseOfTherapyType)
        assertTrue(medicationRequest.insurance.isEmpty())
        assertTrue(medicationRequest.note.isEmpty())
        assertTrue(medicationRequest.dosageInstruction.isEmpty())
        assertNull(medicationRequest.dispenseRequest)
        assertNull(medicationRequest.substitution)
        assertNull(medicationRequest.priorPrescription)
        assertTrue(medicationRequest.detectedIssue.isEmpty())
        assertTrue(medicationRequest.eventHistory.isEmpty())
    }

    @Test
    fun `function works with parameters`() {
        val medicationRequest = medicationRequest {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            status of "status"
            statusReason of codeableConcept { text of "statusReason" }
            intent of "intent"
            category of listOf(
                codeableConcept { text of "category" }
            )
            priority of Code("priority")
            doNotPerform of false
            reported of DynamicValues.boolean(true)
            medication of DynamicValues.reference(reference("Medication", "1234"))
            subject of reference("Patient", "123")
            encounter of reference("Encounter", "1234")
            supportingInformation of listOf(
                reference("Condition", "1234")
            )
            authoredOn of DateTime("2022-11-03")
            requester of reference("Practitioner", "1234")
            performer of reference("Practitioner", "5678")
            performerType of codeableConcept { text of "performer type" }
            recorder of reference("Practitioner", "3456")
            reasonCode of listOf(
                codeableConcept { text of "reason" }
            )
            reasonReference of listOf(
                reference("Condition", "5678")
            )
            instantiatesCanonical of listOf(
                Canonical("url")
            )
            instantiatesUri of listOf(
                Uri("uri")
            )
            basedOn of listOf(
                reference("CarePlan", "1234")
            )
            groupIdentifier of identifier { value of "XYZ" }
            courseOfTherapyType of codeableConcept { text of "therapy" }
            insurance of listOf(
                reference("Coverage", "1234")
            )
            note of listOf(
                annotation { text of Markdown("note") }
            )
            dosageInstruction of listOf(
                dosage { text of "dosage" }
            )
            dispenseRequest of medicationRequestDispenseRequest {
                numberOfRepeatsAllowed of UnsignedInt(2)
            }
            substitution of medicationRequestSubstitution {}
            priorPrescription of reference("MedicationRequest", "1234")
            detectedIssue of listOf(
                reference("DetectedIssue", "1234")
            )
            eventHistory of listOf(
                reference("Provenance", "1234")
            )
        }
        assertEquals("id", medicationRequest.id?.value)
        assertEquals(1, medicationRequest.identifier.size)
        assertEquals("status", medicationRequest.status?.value)
        assertEquals(
            "statusReason",
            medicationRequest.statusReason?.text?.value
        )
        assertEquals("intent", medicationRequest.intent?.value)
        assertEquals("category", medicationRequest.category.first().text?.value)
        assertEquals("priority", medicationRequest.priority?.value)
        assertEquals(false, medicationRequest.doNotPerform?.value)
        assertEquals(FHIRBoolean.TRUE, medicationRequest.reported?.value)
        val actualMedication = medicationRequest.medication?.value as Reference
        assertEquals("Medication/1234", actualMedication.reference?.value)
        assertEquals(
            "Patient/123",
            medicationRequest.subject?.reference?.value
        )
        assertEquals(
            "Encounter/1234",
            medicationRequest.encounter?.reference?.value
        )
        assertEquals(
            "Condition/1234",
            medicationRequest.supportingInformation.first().reference?.value
        )
        assertEquals(
            "Practitioner/1234",
            medicationRequest.requester?.reference?.value
        )
        assertEquals(
            "Practitioner/5678",
            medicationRequest.performer?.reference?.value
        )
        assertEquals(
            "performer type",
            medicationRequest.performerType?.text?.value
        )
        assertEquals(
            "Practitioner/3456",
            medicationRequest.recorder?.reference?.value
        )
        assertEquals("reason", medicationRequest.reasonCode.first().text?.value)
        assertEquals(
            "Condition/5678",
            medicationRequest.reasonReference.first().reference?.value
        )
        assertEquals(
            Canonical("url"),
            medicationRequest.instantiatesCanonical.first()
        )
        assertEquals(Uri("uri"), medicationRequest.instantiatesUri.first())
        assertEquals(
            "CarePlan/1234",
            medicationRequest.basedOn.first().reference?.value
        )
        assertEquals(
            FHIRString("XYZ"),
            medicationRequest.groupIdentifier?.value
        )
        assertEquals(
            "therapy",
            medicationRequest.courseOfTherapyType?.text?.value
        )
        assertEquals(
            "Coverage/1234",
            medicationRequest.insurance.first().reference?.value
        )
        assertEquals(
            "note",
            medicationRequest.note.first().text?.value
        )
        assertEquals(
            "dosage",
            medicationRequest.dosageInstruction.first().text?.value
        )
        assertEquals(
            2,
            medicationRequest.dispenseRequest?.numberOfRepeatsAllowed?.value
        )
        assertEquals(
            DynamicValueType.BOOLEAN,
            medicationRequest.substitution?.allowed?.type
        )
        assertEquals(
            "MedicationRequest/1234",
            medicationRequest.priorPrescription?.reference?.value
        )
        assertEquals(
            "DetectedIssue/1234",
            medicationRequest.detectedIssue.first().reference?.value
        )
        assertEquals(
            "Provenance/1234",
            medicationRequest.eventHistory.first().reference?.value
        )
    }
}

class MedicationRequestDispenseRequestGeneratorTest {
    @Test
    fun `function works with default`() {
        val requestDispense = medicationRequestDispenseRequest {}
        assertNull(requestDispense.id)
        assertEquals(emptyList<Extension>(), requestDispense.extension)
        assertEquals(emptyList<Extension>(), requestDispense.modifierExtension)
        assertNull(requestDispense.initialFill)
        assertNull(requestDispense.dispenseInterval)
        assertNull(requestDispense.validityPeriod)
        assertNull(requestDispense.numberOfRepeatsAllowed)
        assertNull(requestDispense.quantity)
        assertNull(requestDispense.expectedSupplyDuration)
        assertNull(requestDispense.performer)
    }

    @Test
    fun `function works with parameters`() {
        val requestDispense = medicationRequestDispenseRequest {
            initialFill of medicationRequestInitialFill {
                quantity of SimpleQuantity(
                    value = Decimal(BigDecimal(5.0)),
                    unit = "cm".asFHIR()
                )
            }
            dispenseInterval of Duration(
                value = Decimal(BigDecimal(1.0)),
                unit = "h".asFHIR()
            )
            validityPeriod of period {
                start of dateTime { year of 1990 }
                end of dateTime { year of 1993 }
            }
            numberOfRepeatsAllowed of UnsignedInt(2)
            quantity of SimpleQuantity(
                value = Decimal(BigDecimal(2.0)),
                unit = "cm".asFHIR()
            )
            expectedSupplyDuration of Duration(
                value = Decimal(BigDecimal(1.0)),
                unit = "h".asFHIR()
            )
            performer of reference("Practitioner", "1234")
        }
        assertEquals(Decimal(BigDecimal(5.0)), requestDispense.initialFill?.quantity?.value)
        assertEquals("cm", requestDispense.initialFill?.quantity?.unit?.value)
        assertEquals(Decimal(BigDecimal(1.0)), requestDispense.dispenseInterval?.value)
        assertEquals("h", requestDispense.dispenseInterval?.unit?.value)
        assertTrue(requestDispense.validityPeriod?.start?.value?.startsWith("1990") == true)
        assertTrue(requestDispense.validityPeriod?.end?.value?.startsWith("1993") == true)
        assertEquals(2, requestDispense.numberOfRepeatsAllowed?.value)
        assertEquals(Decimal(BigDecimal(2.0)), requestDispense.quantity?.value)
        assertEquals("cm", requestDispense.quantity?.unit?.value)
        assertEquals(Decimal(BigDecimal(1.0)), requestDispense.expectedSupplyDuration?.value)
        assertEquals("h", requestDispense.expectedSupplyDuration?.unit?.value)
        assertEquals("Practitioner/1234", requestDispense.performer?.reference?.value)
    }
}

class MedicationRequestInitialFillGeneratorTest {
    @Test
    fun `function works with default`() {
        val initialFill = medicationRequestInitialFill {}
        assertNull(initialFill.id)
        assertEquals(emptyList<Extension>(), initialFill.extension)
        assertEquals(emptyList<Extension>(), initialFill.modifierExtension)
        assertNull(initialFill.quantity)
        assertNull(initialFill.duration)
    }

    @Test
    fun `function works with parameters`() {
        val initialFill = medicationRequestInitialFill {
            quantity of SimpleQuantity(
                value = Decimal(BigDecimal(2.0)),
                unit = "cm".asFHIR()
            )
            duration of Duration(
                value = Decimal(BigDecimal(1.0)),
                unit = "h".asFHIR()
            )
        }
        assertEquals(Decimal(BigDecimal(2.0)), initialFill.quantity?.value)
        assertEquals("cm", initialFill.quantity?.unit?.value)
        assertEquals(Decimal(BigDecimal(1.0)), initialFill.duration?.value)
        assertEquals("h", initialFill.duration?.unit?.value)
    }
}

class MedicationRequestSubstitutionGeneratorTest {
    @Test
    fun `function works with default`() {
        val substitution = medicationRequestSubstitution {}
        assertNull(substitution.id)
        assertEquals(emptyList<Extension>(), substitution.extension)
        assertEquals(emptyList<Extension>(), substitution.modifierExtension)
        assertEquals(DynamicValueType.BOOLEAN, substitution.allowed?.type)
        assertNull(substitution.reason)
    }

    @Test
    fun `function works with parameters`() {
        val substitution = medicationRequestSubstitution {
            allowed of DynamicValues.boolean(false)
            reason of codeableConcept { text of "reason" }
        }
        val actualSubstitution = substitution.allowed?.value as FHIRBoolean
        assertEquals(false, actualSubstitution.value)
        assertEquals("reason", substitution.reason?.text?.value)
    }
}
