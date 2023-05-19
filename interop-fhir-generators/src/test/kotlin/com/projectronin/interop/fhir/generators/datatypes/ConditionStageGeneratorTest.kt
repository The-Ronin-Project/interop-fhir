package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ConditionStageGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val conditionStage = conditionStage {}
        assertTrue(conditionStage.assessment.isEmpty())
        assertTrue(conditionStage.summary == null)
        assertTrue(conditionStage.type == null)
    }

    @Test
    fun `function works with parameters`() {
        val conditionStage = conditionStage {
            assessment of listOf(reference("Patient", "123"))
            type of codeableConcept {
                coding of listOf(
                    coding {
                        system of "system"
                        code of "code"
                    }
                )
            }
            summary of codeableConcept {
                coding of listOf(
                    coding {
                        system of "summarySystem"
                        code of "summaryCode"
                    }
                )
            }
        }
        assertEquals(1, conditionStage.assessment.size)
        assertEquals(
            Coding(system = Uri("system"), code = Code("code")),
            conditionStage.type?.coding?.firstOrNull()
        )
        assertEquals(
            Coding(system = Uri("summarySystem"), code = Code("summaryCode")),
            conditionStage.summary?.coding?.firstOrNull()
        )
    }
}
