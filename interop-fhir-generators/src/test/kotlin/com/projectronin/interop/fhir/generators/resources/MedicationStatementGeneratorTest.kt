package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.dateTime
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
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
        assertTrue(medicationStatement.identifier.isEmpty())
        assertTrue(medicationStatement.dateAsserted is DateTime)
        assertEquals(DynamicValueType.CODEABLE_CONCEPT, medicationStatement.medication?.type)
        assertNull(medicationStatement.status)
        assertNotNull(medicationStatement.subject)
    }

    @Test
    fun `function works with parameters`() {
        val medicationStatement = medicationStatement {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            dateAsserted of dateTime { year of 2001 }
            medicationCodeableConcept of codeableConcept { text of "code" }
            status of "status"
            subject of reference("Patient", "123")
        }

        assertEquals("id", medicationStatement.id?.value)
        assertEquals(1, medicationStatement.identifier.size)
        val actualMedication = medicationStatement.medication?.value as CodeableConcept
        assertEquals("code", actualMedication.text?.value)
        val actualYear = medicationStatement.dateAsserted?.value as String
        assertTrue(actualYear.startsWith("2001"))
        assertEquals("status", medicationStatement.status?.value)
        assertEquals("Patient/123", medicationStatement.subject?.reference?.value)
    }
}
