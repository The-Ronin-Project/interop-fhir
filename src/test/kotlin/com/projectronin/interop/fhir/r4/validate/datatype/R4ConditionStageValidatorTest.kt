package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.ConditionStage
import com.projectronin.interop.fhir.r4.datatype.Reference
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ConditionStageValidatorTest {
    @Test
    fun `fails if value provided without summary`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val conditionStage = ConditionStage(id = "id")
                R4ConditionStageValidator.validate(conditionStage).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CNDSTG_001: stage SHALL have summary or assessment @ ConditionStage",
            exception.message
        )
    }

    @Test
    fun `validates successfully with summary`() {
        val conditionStage = ConditionStage(
            summary = CodeableConcept(id = "1234")
        )
        R4ConditionStageValidator.validate(conditionStage).alertIfErrors()
    }

    @Test
    fun `validates successfully with assessment`() {
        val conditionStage = ConditionStage(
            assessment = listOf(Reference(display = "assessment"))
        )
        R4ConditionStageValidator.validate(conditionStage).alertIfErrors()
    }
}
