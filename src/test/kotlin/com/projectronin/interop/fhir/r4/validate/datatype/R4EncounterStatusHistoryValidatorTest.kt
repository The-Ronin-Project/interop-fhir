package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.EncounterStatusHistory
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.EncounterStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4EncounterStatusHistoryValidatorTest {
    @Test
    fun `validate - succeeds with only required attributes`() {
        val encounterStatusHistory = EncounterStatusHistory(
            status = EncounterStatus.CANCELLED.asCode(),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            )
        )
        R4EncounterStatusHistoryValidator.validate(encounterStatusHistory).alertIfErrors()
    }

    @Test
    fun `validate - succeeds with required and other attributes`() {
        val encounterStatusHistory = EncounterStatusHistory(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            status = EncounterStatus.UNKNOWN.asCode(),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            )
        )
        R4EncounterStatusHistoryValidator.validate(encounterStatusHistory).alertIfErrors()
    }

    @Test
    fun `validate - fails for missing status`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterStatusHistory = EncounterStatusHistory(
                    status = null,
                    period = Period(
                        start = DateTime(value = "2021-11-17T08:00:00Z"),
                        end = DateTime(value = "2021-11-17T09:00:00Z")
                    )
                )
                R4EncounterStatusHistoryValidator.validate(encounterStatusHistory).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ EncounterStatusHistory.status",
            exception.message
        )
    }

    @Test
    fun `validate - fails for bad status`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounter = EncounterStatusHistory(
                    status = Code("unavailable"),
                    period = Period(
                        start = DateTime(value = "2021-11-17T08:00:00Z"),
                        end = DateTime(value = "2021-11-17T09:00:00Z")
                    )
                )
                R4EncounterStatusHistoryValidator.validate(encounter).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: status is outside of required value set @ EncounterStatusHistory.status",
            exception.message
        )
    }

    @Test
    fun `validate - fails for missing period`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterStatusHistory = EncounterStatusHistory(
                    status = EncounterStatus.TRIAGED.asCode(),
                    period = null,
                )
                R4EncounterStatusHistoryValidator.validate(encounterStatusHistory).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: period is a required element @ EncounterStatusHistory.period",
            exception.message
        )
    }

    @Test
    fun `validate - when there are multiple failure cases, reports all`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterStatusHistory = EncounterStatusHistory(
                    status = null,
                    period = null,
                )
                R4EncounterStatusHistoryValidator.validate(encounterStatusHistory).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ EncounterStatusHistory.status\n" +
                "ERROR REQ_FIELD: period is a required element @ EncounterStatusHistory.period",
            exception.message
        )
    }
}
