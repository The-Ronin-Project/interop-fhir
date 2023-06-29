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
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.valueset.MedicationStatementStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MedicationStatementGeneratorTest {

    @Test
    fun `function works with defaults`() {
        val medicationStatement = medicationStatement {}
        assertNull(medicationStatement.id)
        assertNull(medicationStatement.meta)
        assertNull(medicationStatement.implicitRules)
        assertNull(medicationStatement.language)
        assertNull(medicationStatement.text)
        assertEquals(0, medicationStatement.contained.size)
        assertEquals(0, medicationStatement.extension.size)
        assertEquals(0, medicationStatement.modifierExtension.size)
        assertTrue(medicationStatement.identifier.isEmpty())
        assertTrue(medicationStatement.basedOn.isEmpty())
        assertTrue(medicationStatement.partOf.isEmpty())
        assertNotNull(medicationStatement.status)
        medicationStatement.status?.value?.let { status ->
            assertNotNull(
                MedicationStatementStatus.values().firstOrNull { status == it.code }
            )
        }
        assertTrue(medicationStatement.statusReason.isEmpty())
        assertNull(medicationStatement.category)
        assertEquals(DynamicValueType.REFERENCE, medicationStatement.medication?.type)
        assertNotNull(medicationStatement.subject)
        assertNull(medicationStatement.context)
        assertNull(medicationStatement.effective)
        assertNull(medicationStatement.dateAsserted)
        assertNull(medicationStatement.informationSource)
        assertTrue(medicationStatement.derivedFrom.isEmpty())
        assertTrue(medicationStatement.reasonCode.isEmpty())
        assertTrue(medicationStatement.reasonReference.isEmpty())
        assertTrue(medicationStatement.note.isEmpty())
        assertTrue(medicationStatement.dosage.isEmpty())
    }

    @Test
    fun `function works with parameters`() {
        val medicationStatement = medicationStatement {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            basedOn of listOf(
                reference("CarePlan", "1234")
            )
            partOf of listOf(
                reference("CarePlan", "5678")
            )
            status of "status"
            statusReason of listOf(
                codeableConcept { text of "statusReason" }
            )
            category of codeableConcept { text of "category" }
            medication of DynamicValues.reference(reference("Medication", "1234"))
            subject of reference("Patient", "123")
            context of reference("Encounter", "1234")
            effective of DynamicValues.period(
                period {
                    start of dateTime {
                        year of 1990
                        day of 9
                        month of 4
                    }
                    end of dateTime {
                        year of 1990
                        day of 10
                        month of 4
                    }
                }
            )
            dateAsserted of dateTime { year of 2001 }
            informationSource of reference("Practitioner", "1234")
            derivedFrom of listOf(
                reference("MedicationStatement", "5678")
            )
            reasonCode of listOf(
                codeableConcept { text of "reasonCode" }
            )
            reasonReference of listOf(
                reference("Condition", "1234")
            )
            note of listOf(
                annotation { text of Markdown("note") }
            )
            dosage of listOf(
                dosage { text of "dosage" }
            )
        }
        assertEquals("id", medicationStatement.id?.value)
        assertEquals(1, medicationStatement.identifier.size)
        assertEquals("CarePlan/1234", medicationStatement.basedOn.first().reference?.value)
        assertEquals("CarePlan/5678", medicationStatement.partOf.first().reference?.value)
        assertEquals("status", medicationStatement.status?.value)
        assertEquals(
            "statusReason",
            medicationStatement.statusReason.first().text?.value
        )
        assertEquals("category", medicationStatement.category?.text?.value)
        assertEquals(DynamicValueType.REFERENCE, medicationStatement.medication?.type)
        val actualMedication = medicationStatement.medication?.value as Reference
        assertEquals("Medication/1234", actualMedication.reference?.value)
        assertEquals("Patient/123", medicationStatement.subject?.reference?.value)
        assertEquals("Encounter/1234", medicationStatement.context?.reference?.value)
        val actualEffective = medicationStatement.effective?.value as Period
        assertTrue(actualEffective.start?.value == "1990-04-09")
        assertTrue(actualEffective.end?.value == "1990-04-10")
        val actualYear = medicationStatement.dateAsserted?.value as String
        assertTrue(actualYear.startsWith("2001"))
        assertEquals("Practitioner/1234", medicationStatement.informationSource?.reference?.value)
        assertEquals("MedicationStatement/5678", medicationStatement.derivedFrom.first().reference?.value)
        assertEquals("reasonCode", medicationStatement.reasonCode.first().text?.value)
        assertEquals("Condition/1234", medicationStatement.reasonReference.first().reference?.value)
        assertEquals(
            "note",
            medicationStatement.note.first().text?.value
        )
        assertEquals(
            "dosage",
            medicationStatement.dosage.first().text?.value
        )
    }
}
