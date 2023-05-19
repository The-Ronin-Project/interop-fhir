package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.generators.datatypes.annotation
import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.coding
import com.projectronin.interop.fhir.generators.datatypes.conditionEvidence
import com.projectronin.interop.fhir.generators.datatypes.conditionStage
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.valueset.ConditionClinicalStatusCodes
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ConditionGeneratorTest {

    @Test
    fun `example use with serialization`() {
        // Create a condition with the details you need.
        val condition = condition {
            // Say your test require condition for Breast Cancer
            code of codeableConcept {
                coding of listOf(
                    coding {
                        code of Code("254837009")
                        system of CodeSystem.SNOMED_CT.uri
                        display of "Malignant neoplasm of breast"
                    }
                )
                text of "Breast Cancer"
            }
            // And active status
            clinicalStatus of codeableConcept {
                coding of listOf(
                    coding {
                        code of "active"
                        system of CodeSystem.CONDITION_CLINICAL.uri
                        display of "Active"
                    }
                )
            }
            // For a specific patient (reference their FHIR id)
            patient {
                reference("Patient", "FHIR-1234")
            }
        }

        // This object can be serialized to JSON to be injected into your workflow, all required R4 attributes wil be generated
        val conditionJSON = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(condition)

        // Uncomment to take a peek at the JSON
        // println(conditionJSON)
        assertNotNull(conditionJSON)
    }

    @Test
    fun `function works with defaults`() {
        val condition = condition {}
        assertNull(condition.id)
        assertNull(condition.meta)
        assertNull(condition.implicitRules)
        assertNull(condition.language)
        assertNull(condition.text)
        assertEquals(0, condition.contained.size)
        assertEquals(0, condition.extension.size)
        assertEquals(0, condition.modifierExtension.size)
        assertTrue(condition.identifier.isEmpty())
        assertNotNull(condition.clinicalStatus)
        assertNotNull(
            ConditionClinicalStatusCodes.values()
                .firstOrNull { it.code == condition.clinicalStatus!!.coding.firstOrNull()!!.code!!.value }
        )
        assertNull(condition.verificationStatus)
        assertTrue(condition.category.isEmpty())
        assertNotNull(condition.subject)
        assertTrue(condition.bodySite.isEmpty())
        assertNull(condition.severity)
        assertNull(condition.code)
        assertNull(condition.encounter)
        assertNull(condition.onset)
        assertNull(condition.abatement)
        assertNull(condition.recordedDate)
        assertNull(condition.recorder)
        assertNull(condition.asserter)
        assertTrue(condition.stage.isEmpty())
        assertNotNull(condition.evidence)
    }

    @Test
    fun `function works with parameters`() {
        val condition = condition {
            id of Id("id")
            identifier of listOf(identifier {})
            clinicalStatus of codeableConcept { text of "clinicalStatus" }
            verificationStatus of codeableConcept { text of "verificationStatus" }
            category of listOf(codeableConcept {})
            severity of codeableConcept { text of "severity" }
            code of codeableConcept { text of "code" }
            bodySite of listOf(codeableConcept { text of "bodySite" })
            subject of reference("Patient", "123")
            encounter of reference("Encounter", "456")
            recordedDate of DateTime("2015-02-07T13:28:17-05:00")
            recorder of reference("Patient", "789")
            asserter of reference("Practitioner", "567")
            stage of listOf(conditionStage { })
            evidence of listOf(conditionEvidence { })
            note of listOf(annotation { })
        }
        assertEquals("id", condition.id?.value)
        assertEquals(1, condition.identifier.size)
        assertEquals("clinicalStatus", condition.clinicalStatus?.text?.value)
        assertEquals("verificationStatus", condition.verificationStatus?.text?.value)
        assertEquals(1, condition.category.size)
        assertEquals("code", condition.code?.text?.value)
        assertNotNull(condition.code)
        assertEquals("severity", condition.severity?.text?.value)
        assertEquals(1, condition.bodySite.size)
        assertEquals("Patient/123", condition.subject?.reference?.value)
        assertEquals("Encounter/456", condition.encounter?.reference?.value)
        assertEquals("2015-02-07T13:28:17-05:00", condition.recordedDate?.value)
        assertEquals("Patient/789", condition.recorder?.reference?.value)
        assertEquals("Practitioner/567", condition.asserter?.reference?.value)
        assertEquals(1, condition.stage.size)
        assertEquals(1, condition.evidence.size)
        assertEquals(1, condition.note.size)
    }
}
