package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4NarrativeValidatorTest {
    @Test
    fun `fails if no status provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val narrative = Narrative(
                status = null,
                div = FHIRString("narrative text")
            )
            R4NarrativeValidator.validate(narrative).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ Narrative.status",
            exception.message
        )
    }

    @Test
    fun `fails if status is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val narrative = Narrative(
                status = Code("unsupported-status"),
                div = FHIRString("narrative text")
            )
            R4NarrativeValidator.validate(narrative).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-status' is outside of required value set @ Narrative.status",
            exception.message
        )
    }

    @Test
    fun `fails if no div provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val narrative = Narrative(
                status = NarrativeStatus.GENERATED.asCode(),
                div = null
            )
            R4NarrativeValidator.validate(narrative).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: div is a required element @ Narrative.div",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val narrative = Narrative(status = NarrativeStatus.GENERATED.asCode(), div = FHIRString("narrative text"))
        R4NarrativeValidator.validate(narrative).alertIfErrors()
    }
}
