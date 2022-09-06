package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.ConditionEvidence
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ConditionEvidenceValidatorTest {
    @Test
    fun `fails if value provided without code or detail`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val conditionEvidence = ConditionEvidence(id = "id")
                R4ConditionEvidenceValidator.validate(conditionEvidence).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CNDEV_001: evidence SHALL have code or details @ ConditionEvidence",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val conditionEvidence = ConditionEvidence(
            code = listOf(CodeableConcept(text = "code"))
        )
        R4ConditionEvidenceValidator.validate(conditionEvidence).alertIfErrors()
    }
}
