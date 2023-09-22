package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.resource.Encounter
import com.projectronin.interop.fhir.r4.valueset.EncounterStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4EncounterValidatorTest {
    @Test
    fun `validate - succeeds with only required attributes`() {
        val encounter = Encounter(
            status = EncounterStatus.TRIAGED.asCode(),
            `class` = Coding(code = EncounterStatus.TRIAGED.asCode())
        )
        R4EncounterValidator.validate(encounter).alertIfErrors()
    }

    @Test
    fun `validate - succeeds with required and other attributes`() {
        val encounter = Encounter(
            status = EncounterStatus.ONLEAVE.asCode(),
            `class` = Coding(code = EncounterStatus.UNKNOWN.asCode()),
            priority = CodeableConcept(
                coding = listOf(
                    Coding(
                        system = CodeSystem.SNOMED_CT.uri,
                        code = Code("103391001"),
                        display = FHIRString("Non-urgent ear, nose and throat admission")
                    )
                )
            ),
            subject = Reference(
                reference = FHIRString("Patient/f001"),
                display = FHIRString("P. van de Heuvel")
            ),
            length = Duration(
                value = Decimal(90.0),
                unit = FHIRString("min"),
                system = CodeSystem.UCUM.uri,
                code = Code("min")
            ),
            reasonCode = listOf(
                CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = CodeSystem.SNOMED_CT.uri,
                            code = Code("18099001"),
                            display = FHIRString("Retropharyngeal abscess")
                        )
                    )
                )
            )
        )
        R4EncounterValidator.validate(encounter).alertIfErrors()
    }

    @Test
    fun `validate - fails for null class`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounter = Encounter(
                    status = EncounterStatus.UNKNOWN.asCode(),
                    `class` = null
                )
                R4EncounterValidator.validate(encounter).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: class is a required element @ Encounter.class",
            exception.message
        )
    }

    @Test
    fun `validate - fails for null status`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounter = Encounter(
                    status = null,
                    `class` = Coding(code = EncounterStatus.UNKNOWN.asCode())
                )
                R4EncounterValidator.validate(encounter).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ Encounter.status",
            exception.message
        )
    }

    @Test
    fun `validate - fails for bad status`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounter = Encounter(
                    status = Code("unavailable"),
                    `class` = Coding(code = EncounterStatus.UNKNOWN.asCode())
                )
                R4EncounterValidator.validate(encounter).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unavailable' is outside of required value set @ Encounter.status",
            exception.message
        )
    }

    @Test
    fun `validate - when there are multiple failure cases, reports all`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounter = Encounter(
                    status = Code("unavailable"),
                    `class` = null
                )
                R4EncounterValidator.validate(encounter).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: class is a required element @ Encounter.class\n" +
                "ERROR INV_VALUE_SET: 'unavailable' is outside of required value set @ Encounter.status",
            exception.message
        )
    }
}
