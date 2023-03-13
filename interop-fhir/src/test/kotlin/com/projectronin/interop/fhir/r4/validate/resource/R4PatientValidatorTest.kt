package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.resource.Patient
import com.projectronin.interop.fhir.r4.resource.PatientCommunication
import com.projectronin.interop.fhir.r4.resource.PatientContact
import com.projectronin.interop.fhir.r4.resource.PatientLink
import com.projectronin.interop.fhir.r4.valueset.AdministrativeGender
import com.projectronin.interop.fhir.r4.valueset.LinkType
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4PatientValidatorTest {
    @Test
    fun `gender is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val patient = Patient(
                gender = Code("unsupported-gender")
            )
            R4PatientValidator.validate(patient).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-gender' is outside of required value set @ Patient.gender",
            exception.message
        )
    }

    @Test
    fun `gender value null is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val patient = Patient(
                gender = Code(null)
            )
            R4PatientValidator.validate(patient).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'null' is outside of required value set @ Patient.gender",
            exception.message
        )
    }

    @Test
    fun `missing gender is accepted with no error for R4`() {
        val patient = Patient()
        val validation = R4PatientValidator.validate(patient)
        assertEquals(0, validation.issues().size)
    }

    @Test
    fun `null gender is accepted with no error for R4`() {
        val patient = Patient(
            gender = null
        )
        val validation = R4PatientValidator.validate(patient)
        assertEquals(0, validation.issues().size)
    }

    @Test
    fun `deceased can only be one of the following data types`() {
        val exception = assertThrows<IllegalArgumentException> {
            val patient = Patient(
                gender = AdministrativeGender.FEMALE.asCode(),
                deceased = DynamicValue(type = DynamicValueType.BASE_64_BINARY, value = false)
            )
            R4PatientValidator.validate(patient).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: deceased can only be one of the following: Boolean, DateTime @ Patient.deceased",
            exception.message
        )
    }

    @Test
    fun `multipleBirth can only be one of the following data types`() {
        val exception = assertThrows<IllegalArgumentException> {
            val patient = Patient(
                gender = AdministrativeGender.FEMALE.asCode(),
                multipleBirth = DynamicValue(type = DynamicValueType.BASE_64_BINARY, value = 2)
            )
            R4PatientValidator.validate(patient).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: multipleBirth can only be one of the following: Boolean, Integer @ Patient.multipleBirth",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val patient = Patient(
            gender = AdministrativeGender.FEMALE.asCode()
        )
        R4PatientValidator.validate(patient).alertIfErrors()
    }
}

class R4PatientCommunicationValidatorTest {
    @Test
    fun `fails if no language provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val patientCommunication = PatientCommunication(
                language = null
            )
            R4PatientCommunicationValidator.validate(patientCommunication).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: language is a required element @ PatientCommunication.language",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val patientCommunication = PatientCommunication(language = CodeableConcept(text = FHIRString("English")))
        R4PatientCommunicationValidator.validate(patientCommunication).alertIfErrors()
    }
}

class R4PatientContactValidatorTest {
    @Test
    fun `fails if gender is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val contact = PatientContact(
                gender = Code("unsupported-gender"),
                name = HumanName(text = FHIRString("name"))
            )
            R4PatientContactValidator.validate(contact).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-gender' is outside of required value set @ PatientContact.gender",
            exception.message
        )
    }

    @Test
    fun `fails without details`() {
        val exception = assertThrows<IllegalArgumentException> {
            val contact = PatientContact()
            R4PatientContactValidator.validate(contact).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CNTCT_001: contact SHALL at least contain a contact's details or a reference to an organization @ PatientContact",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val contact = PatientContact(
            name = HumanName(text = FHIRString("Jane Doe")),
            telecom = listOf(
                ContactPoint(
                    value = FHIRString("name@site.com"),
                    system = com.projectronin.interop.fhir.r4.valueset.ContactPointSystem.EMAIL.asCode()
                )
            ),
        )
        R4PatientContactValidator.validate(contact).alertIfErrors()
    }
}

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
                "ERROR INV_VALUE_SET: 'unsupported-type' is outside of required value set @ PatientLink.type",
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
