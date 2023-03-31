package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.conditionStage
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ConditionGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val condition = condition {}
        assertNull(condition.id)
        assertTrue(condition.identifier.isEmpty())
        assertNotNull(condition.clinicalStatus)
        assertTrue(condition.category.isEmpty())
        assertNotNull(condition.code)
        assertNotNull(condition.subject)
        assertTrue(condition.stage.isEmpty())
    }

    @Test
    fun `function works with parameters`() {
        val condition = condition {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            clinicalStatus of codeableConcept { text of "clinicalStatus" }
            category of listOf(codeableConcept {})
            code of codeableConcept { text of "code" }
            subject of reference("Patient", "123")
            stage of listOf(conditionStage { })
        }
        assertEquals("id", condition.id?.value)
        assertEquals(1, condition.identifier.size)
        assertEquals("clinicalStatus", condition.clinicalStatus?.text?.value)
        assertEquals(1, condition.category.size)
        assertEquals("code", condition.code?.text?.value)
        assertNotNull(condition.code)
        assertEquals("Patient/123", condition.subject?.reference?.value)
        assertEquals(1, condition.stage.size)
    }
}
