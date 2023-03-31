package com.projectronin.interop.fhir.generators.datatypes

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ConditionStageGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val conditionStage = conditionStage {}
        assertTrue(conditionStage.assessment.isEmpty())
    }

    @Test
    fun `function works with parameters`() {
        val conditionStage = conditionStage {
            assessment of listOf(reference("Patient", "123"))
        }
        assertEquals(1, conditionStage.assessment.size)
    }
}
