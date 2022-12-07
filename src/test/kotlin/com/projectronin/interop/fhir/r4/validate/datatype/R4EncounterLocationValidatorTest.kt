package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.EncounterLocation
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.valueset.EncounterLocationStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4EncounterLocationValidatorTest {
    @Test
    fun `validate - succeeds with only required attributes`() {
        val encounterLocation = EncounterLocation(
            location = Reference(reference = FHIRString("Location/example"))
        )
        R4EncounterLocationValidator.validate(encounterLocation).alertIfErrors()
    }

    @Test
    fun `validate - succeeds with required and other attributes`() {
        val encounterLocation = EncounterLocation(
            location = Reference(reference = FHIRString("Location/example")),
            status = EncounterLocationStatus.ACTIVE.asCode(),
            physicalType = CodeableConcept(coding = listOf(Coding(code = Code("ca"))))
        )
        R4EncounterLocationValidator.validate(encounterLocation).alertIfErrors()
    }

    @Test
    fun `validate - fails for missing location`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterLocation = EncounterLocation(
                    location = null,
                    status = EncounterLocationStatus.ACTIVE.asCode()
                )
                R4EncounterLocationValidator.validate(encounterLocation).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: location is a required element @ EncounterLocation.location",
            exception.message
        )
    }

    @Test
    fun `validate - fails for bad status`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterLocation = EncounterLocation(
                    location = Reference(reference = FHIRString("Location/example")),
                    status = Code("unavailable")
                )
                R4EncounterLocationValidator.validate(encounterLocation).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unavailable' is outside of required value set @ EncounterLocation.status",
            exception.message
        )
    }

    @Test
    fun `validate - when there are multiple failure cases, reports all`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterLocation = EncounterLocation(
                    location = null,
                    status = Code("unavailable")
                )
                R4EncounterLocationValidator.validate(encounterLocation).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: location is a required element @ EncounterLocation.location\n" +
                "ERROR INV_VALUE_SET: 'unavailable' is outside of required value set @ EncounterLocation.status",
            exception.message
        )
    }
}
