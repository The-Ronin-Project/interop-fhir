package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.resource.ConceptMap
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ConceptMapValidatorTest {
    @Test
    fun `status is required`() {
        val ex = assertThrows<IllegalArgumentException> {
            val conceptMap = ConceptMap(
                status = null
            )
            R4ConceptMapValidator.validate(conceptMap).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ ConceptMap.status",
            ex.message
        )
    }
    @Test
    fun `status is outside of required value set`() {
        val ex = assertThrows<IllegalArgumentException> {
            val conceptMap = ConceptMap(
                status = Code("unsupported-status"),
            )
            R4ConceptMapValidator.validate(conceptMap).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: status is outside of required value set @ ConceptMap.status",
            ex.message
        )
    }
}
