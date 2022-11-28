package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.PatientLink
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.valueset.LinkType
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4PatientLinkValidatorTest {
    @Test
    fun `fails if no other provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val patientLink = PatientLink(
                other = null,
                type = LinkType.REPLACES.asCode()
            )
            R4PatientLinkValidator.validate(patientLink).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: other is a required element @ PatientLink.other",
            exception.message
        )
    }

    @Test
    fun `fails if no type provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val patientLink = PatientLink(
                other = Reference(display = FHIRString("reference")),
                type = null
            )
            R4PatientLinkValidator.validate(patientLink).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: type is a required element @ PatientLink.type",
            exception.message
        )
    }

    @Test
    fun `fails if type is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val patientLink = PatientLink(
                other = Reference(display = FHIRString("reference")),
                type = Code("unsupported-type")
            )
            R4PatientLinkValidator.validate(patientLink).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: type is outside of required value set @ PatientLink.type",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val patientLink = PatientLink(
            other = Reference(display = FHIRString("any")),
            type = LinkType.REPLACES.asCode()
        )
        R4PatientLinkValidator.validate(patientLink).alertIfErrors()
    }
}
