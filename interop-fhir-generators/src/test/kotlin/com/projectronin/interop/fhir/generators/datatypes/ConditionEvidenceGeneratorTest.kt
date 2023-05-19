package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ConditionEvidenceGeneratorTest {

    @Test
    fun `function works with defaults`() {
        val conditionEvidence = conditionEvidence { }
        assertTrue(conditionEvidence.code.isEmpty())
        assertTrue(conditionEvidence.detail.isEmpty())
    }

    @Test
    fun `function works with parameters`() {
        val conditionEvidence = conditionEvidence {
            code of listOf(
                codeableConcept {
                    coding of listOf(
                        coding {
                            system of "system"
                            code of "code"
                        }
                    )
                }
            )
            detail of listOf(
                reference("Patient", "1234")
            )
        }

        assertEquals(1, conditionEvidence.code.size)
        assertEquals(
            listOf(Coding(code = Code("code"), system = Uri("system"))),
            conditionEvidence.code.firstOrNull()?.coding
        )
        assertEquals(1, conditionEvidence.detail.size)
        val actualReference = conditionEvidence.detail.first()
        assertEquals("Patient/1234", actualReference.reference?.value)
    }
}
