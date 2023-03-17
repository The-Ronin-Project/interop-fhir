package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
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
        assertTrue(diagnosticReport.identifier.isEmpty())
        assertNull(diagnosticReport.status)
        assertNotNull(diagnosticReport.code)
        assertNotNull(diagnosticReport.subject)
        assertTrue(diagnosticReport.category.isEmpty())
    }

    @Test
    fun `function works with parameters`() {
        val diagnosticReport = diagnosticReport {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            status of "status"
            code of codeableConcept {
                text of "code"
            }
            subject of reference("Patient", "123")
            category of listOf(codeableConcept {})
        }
        assertEquals("id", diagnosticReport.id?.value)
        assertEquals(1, diagnosticReport.identifier.size)
        assertEquals("status", diagnosticReport.status?.value)
        assertEquals("code", diagnosticReport.code?.text?.value)
        assertEquals("Patient/123", diagnosticReport.subject?.reference?.value)
        assertEquals(1, diagnosticReport.category.size)
    }
}
