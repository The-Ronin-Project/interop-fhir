package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.DynamicValues
import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.resource.DiagnosticReportMedia
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DiagnosticReportGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val diagnosticReport = diagnosticReport {}
        assertNull(diagnosticReport.id)
        assertNull(diagnosticReport.meta)
        assertNull(diagnosticReport.implicitRules)
        assertNull(diagnosticReport.language)
        assertNull(diagnosticReport.text)
        assertEquals(0, diagnosticReport.contained.size)
        assertEquals(0, diagnosticReport.extension.size)
        assertEquals(0, diagnosticReport.modifierExtension.size)
        assertTrue(diagnosticReport.identifier.isEmpty())
        assertNull(diagnosticReport.status)
        assertNotNull(diagnosticReport.code)
        assertNotNull(diagnosticReport.subject)
        assertTrue(diagnosticReport.category.isEmpty())
        assertNotNull(diagnosticReport.encounter)
        assertNotNull(diagnosticReport.effective)
        assertNull(diagnosticReport.issued)
        assertEquals(0, diagnosticReport.performer.size)
        assertEquals(0, diagnosticReport.resultsInterpreter.size)
        assertEquals(0, diagnosticReport.specimen.size)
        assertEquals(0, diagnosticReport.result.size)
        assertEquals(0, diagnosticReport.imagingStudy.size)
        assertEquals(0, diagnosticReport.media.size)
        assertNull(diagnosticReport.conclusion)
        assertEquals(0, diagnosticReport.conclusionCode.size)
        assertEquals(0, diagnosticReport.presentedForm.size)
    }

    @Test
    fun `function works with parameters`() {
        val diagnosticReport =
            diagnosticReport {
                id of Id("id")
                identifier of
                    listOf(
                        identifier {},
                    )
                status of "status"
                code of
                    codeableConcept {
                        text of "code"
                    }
                subject of reference("Patient", "123")
                category of listOf(codeableConcept {})
                encounter of reference("Patient", "234")
                effective of DynamicValues.period(Period(FHIRString("Period")))
                issued of Instant("Issued")
                performer of listOf(reference("Patient", "345"))
                resultsInterpreter of listOf(reference("Patient", "456"))
                specimen of listOf(reference("Patient", "567"))
                result of listOf(reference("Patient", "678"))
                imagingStudy of listOf(reference("Patient", "789"))
                media of listOf(DiagnosticReportMedia(link = Reference(FHIRString("Link"))))
                conclusion of FHIRString("Conclusion")
                conclusionCode of listOf(CodeableConcept(FHIRString("Conclusion Code")))
                presentedForm of listOf(Attachment(FHIRString("Presented Form")))
            }
        assertEquals("id", diagnosticReport.id?.value)
        assertEquals(1, diagnosticReport.identifier.size)
        assertEquals("status", diagnosticReport.status?.value)
        assertEquals("code", diagnosticReport.code?.text?.value)
        assertEquals("Patient/123", diagnosticReport.subject?.reference?.value)
        assertEquals(1, diagnosticReport.category.size)
        assertEquals("Patient/234", diagnosticReport.encounter?.reference?.value)
        assertEquals(DynamicValueType.PERIOD, diagnosticReport.effective?.type)
        assertEquals(Period(FHIRString("Period")), diagnosticReport.effective?.value)
        assertEquals("Issued", diagnosticReport.issued?.value)
        assertEquals("Patient/345", diagnosticReport.performer.first().reference?.value)
        assertEquals("Patient/456", diagnosticReport.resultsInterpreter.first().reference?.value)
        assertEquals("Patient/567", diagnosticReport.specimen.first().reference?.value)
        assertEquals("Patient/678", diagnosticReport.result.first().reference?.value)
        assertEquals("Patient/789", diagnosticReport.imagingStudy.first().reference?.value)
        assertEquals("Link", diagnosticReport.media.first().link?.id?.value)
        assertEquals("Conclusion", diagnosticReport.conclusion?.value)
        assertEquals("Conclusion Code", diagnosticReport.conclusionCode.first().id?.value)
        assertEquals("Presented Form", diagnosticReport.presentedForm.first().id?.value)
    }
}
