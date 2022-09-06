package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Communication
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4CommunicationValidatorTest {
    @Test
    fun `fails if no language provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val communication = Communication(
                language = null
            )
            R4CommunicationValidator.validate(communication).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: language is a required element @ Communication.language",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val communication = Communication(language = CodeableConcept(text = "English"))
        R4CommunicationValidator.validate(communication).alertIfErrors()
    }
}
