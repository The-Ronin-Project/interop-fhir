package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.EncounterClassHistory
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4EncounterClassHistoryValidatorTest {
    @Test
    fun `validate - succeeds with only required attributes`() {
        val encounterClassHistory = EncounterClassHistory(
            `class` = Code("PRENC"),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            )
        )
        R4EncounterClassHistoryValidator.validate(encounterClassHistory).alertIfErrors()
    }

    @Test
    fun `validate - succeeds with required and other attributes`() {
        val encounterClassHistory = EncounterClassHistory(
            id = "12345",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            `class` = Code("PRENC"),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            )
        )
        R4EncounterClassHistoryValidator.validate(encounterClassHistory).alertIfErrors()
    }

    @Test
    fun `validate - fails for missing class`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterClassHistory = EncounterClassHistory(
                    `class` = null,
                    period = Period(
                        start = DateTime(value = "2021-11-17T08:00:00Z"),
                        end = DateTime(value = "2021-11-17T09:00:00Z")
                    )
                )
                R4EncounterClassHistoryValidator.validate(encounterClassHistory).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: class is a required element @ EncounterClassHistory.class",
            exception.message
        )
    }

    @Test
    fun `validate - fails for missing period`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterClassHistory = EncounterClassHistory(
                    `class` = Code("PRENC"),
                    period = null,
                )
                R4EncounterClassHistoryValidator.validate(encounterClassHistory).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: period is a required element @ EncounterClassHistory.period",
            exception.message
        )
    }

    @Test
    fun `validate - when there are multiple failure cases, reports all`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterClassHistory = EncounterClassHistory(
                    `class` = null,
                    period = null,
                )
                R4EncounterClassHistoryValidator.validate(encounterClassHistory).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: class is a required element @ EncounterClassHistory.class\n" +
                "ERROR REQ_FIELD: period is a required element @ EncounterClassHistory.period",
            exception.message
        )
    }
}
