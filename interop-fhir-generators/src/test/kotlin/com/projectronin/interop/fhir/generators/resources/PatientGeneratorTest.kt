package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.name
import com.projectronin.interop.fhir.generators.datatypes.period
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.date
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PatientGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val patient = patient {}
        assertNull(patient.id)
        assertNull(patient.meta)
        assertNull(patient.implicitRules)
        assertNull(patient.language)
        assertNull(patient.text)
        assertEquals(0, patient.contained.size)
        assertEquals(0, patient.extension.size)
        assertEquals(0, patient.modifierExtension.size)
        assertTrue(patient.identifier.isEmpty())
        assertNull(patient.active)
        assertEquals(1, patient.name.size)
        assertEquals(1, patient.telecom.size)
        assertNotNull(patient.gender)
        assertNotNull(patient.birthDate)
        assertNull(patient.deceased)
        assertEquals(1, patient.address.size)
        assertNull(patient.maritalStatus)
        assertNull(patient.multipleBirth)
        assertEquals(0, patient.photo.size)
        assertEquals(0, patient.contact.size)
        assertEquals(0, patient.communication.size)
        assertEquals(0, patient.generalPractitioner.size)
        assertNull(patient.managingOrganization)
        assertEquals(0, patient.link.size)
    }

    @Test
    fun `function works with parameters`() {
        val patient = patient {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            name of listOf(name { family of "Felt" })
            gender of "Very"
            birthDate of date { year of 1990 }
        }
        assertEquals("id", patient.id?.value)
        assertEquals(1, patient.identifier.size)
        assertEquals("Felt", patient.name.first().family?.value)
        assertEquals("Very", patient.gender?.value)
        assertTrue(patient.birthDate?.value?.startsWith("1990")!!)
    }
}

class PatientContactGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val patientContact = patientContact {}
        assertNull(patientContact.id)
        assertEquals(0, patientContact.extension.size)
        assertEquals(0, patientContact.modifierExtension.size)
        assertEquals(0, patientContact.relationship.size)
        assertNotNull(patientContact.name)
        assertEquals(1, patientContact.telecom.size)
        assertNotNull(patientContact.address)
        assertNotNull(patientContact.gender)
        assertNull(patientContact.organization)
        assertNull(patientContact.period)
    }

    @Test
    fun `function works with parameters`() {
        val patientContact = patientContact {
            period of period { }
        }
        assertNotNull(patientContact.period)
    }
}

class PatientCommunicationGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val patientCommunication = patientCommunication {}
        assertNull(patientCommunication.id)
        assertEquals(0, patientCommunication.extension.size)
        assertEquals(0, patientCommunication.modifierExtension.size)
        assertNotNull(patientCommunication.language)
        assertNull(patientCommunication.preferred)
    }

    @Test
    fun `function works with parameters`() {
        val patientCommunication = patientCommunication {
            preferred of true
        }
        assertEquals(FHIRBoolean.TRUE, patientCommunication.preferred)
    }
}

class PatientLinkGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val patientLink = patientLink {}
        assertNull(patientLink.id)
        assertEquals(0, patientLink.extension.size)
        assertEquals(0, patientLink.modifierExtension.size)
        assertNull(patientLink.other)
        assertNotNull(patientLink.type)
    }

    @Test
    fun `function works with parameters`() {
        val patientLink = patientLink {
            other of reference("Patient", "1234")
        }
        assertNotNull(patientLink.other)
    }
}
