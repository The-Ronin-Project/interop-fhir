package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.coding
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.period
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.dateTime
import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class EncounterGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val encounter = encounter {}
        assertNull(encounter.id)
        assertNull(encounter.meta)
        assertNull(encounter.implicitRules)
        assertNull(encounter.language)
        assertNull(encounter.text)
        assertEquals(0, encounter.contained.size)
        assertEquals(0, encounter.extension.size)
        assertEquals(0, encounter.modifierExtension.size)
        assertEquals(0, encounter.identifier.size)
        assertNotNull(encounter.status)
        assertEquals(0, encounter.statusHistory.size)
        assertNotNull(encounter.`class`)
        assertEquals(0, encounter.classHistory.size)
        assertEquals(0, encounter.type.size)
        assertNull(encounter.serviceType)
        assertNull(encounter.priority)
        assertNull(encounter.subject)
        assertEquals(0, encounter.episodeOfCare.size)
        assertEquals(0, encounter.basedOn.size)
        assertEquals(0, encounter.participant.size)
        assertEquals(0, encounter.appointment.size)
        assertNull(encounter.period)
        assertNull(encounter.length)
        assertEquals(0, encounter.reasonCode.size)
        assertEquals(0, encounter.reasonReference.size)
        assertEquals(0, encounter.diagnosis.size)
        assertEquals(0, encounter.account.size)
        assertNull(encounter.hospitalization)
        assertEquals(0, encounter.location.size)
        assertNull(encounter.serviceProvider)
        assertNull(encounter.partOf)
    }

    @Test
    fun `function works with parameters`() {
        val encounter = encounter {
            id of "id"
            identifier of listOf(
                identifier {}
            )
            status of "status"
            participant of listOf(
                encounterParticipant {}
            )

            `class` of coding { code of "coding" }
            period of period {
                start of dateTime { year of 1990 }
                end of dateTime { year of 1990 }
            }
            subject of reference("Patient", "123")
            type of listOf(
                codeableConcept { text of "type" }
            )
        }
        assertEquals(Id("id"), encounter.id)
        assertEquals(1, encounter.identifier.size)
        assertEquals(Code("status"), encounter.status)
        assertEquals(1, encounter.participant.size)
        assertEquals("coding", encounter.`class`?.code?.value)
        assertEquals("Patient/123", encounter.subject?.reference?.value)
        assertEquals("type", encounter.type.first().text?.value)
        assertTrue(encounter.period?.start?.value?.startsWith("1990")!!)
        assertTrue(encounter.period?.end?.value?.startsWith("1990")!!)
    }
}

class EncounterClassHistoryGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val encounterClassHistory = encounterClassHistory {}
        assertNull(encounterClassHistory.id)
        assertEquals(0, encounterClassHistory.extension.size)
        assertEquals(0, encounterClassHistory.modifierExtension.size)
        assertNotNull(encounterClassHistory.`class`)
        assertNotNull(encounterClassHistory.period)
    }

    @Test
    fun `function works with parameters`() {
        val encounterClassHistory = encounterClassHistory {
            `class` of "my-class"
        }
        assertEquals(Code("my-class"), encounterClassHistory.`class`)
    }
}

class EncounterParticipantGeneratorTest {
    @Test
    fun `participant function works with default`() {
        val participant = encounterParticipant {}
        assertNull(participant.id)
        assertEquals(0, participant.extension.size)
        assertEquals(0, participant.modifierExtension.size)
        assertEquals(0, participant.type.size)
        assertNull(participant.period)
        assertNotNull(participant.individual)
    }

    @Test
    fun `participant function works with parameters`() {
        val participant = encounterParticipant {
            type of listOf(codeableConcept { })
            individual of reference("Patient", "123")
            period of period { }
        }
        assertEquals(1, participant.type.size)
        assertEquals("Patient/123", participant.individual?.reference?.value)
        assertNotNull(participant.period)
    }
}

class EncounterDiagnosisGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val encounterDiagnosis = encounterDiagnosis {}
        assertNull(encounterDiagnosis.id)
        assertEquals(0, encounterDiagnosis.extension.size)
        assertEquals(0, encounterDiagnosis.modifierExtension.size)
        assertNotNull(encounterDiagnosis.condition)
        assertNull(encounterDiagnosis.use)
        assertNull(encounterDiagnosis.rank)
    }

    @Test
    fun `function works with parameters`() {
        val encounterDiagnosis = encounterDiagnosis {
            rank of 1
        }
        assertEquals(PositiveInt(1), encounterDiagnosis.rank)
    }
}

class EncounterHospitalizationGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val encounterHospitalization = encounterHospitalization {}
        assertNull(encounterHospitalization.id)
        assertEquals(0, encounterHospitalization.extension.size)
        assertEquals(0, encounterHospitalization.modifierExtension.size)
        assertNull(encounterHospitalization.preAdmissionIdentifier)
        assertNull(encounterHospitalization.origin)
        assertNull(encounterHospitalization.admitSource)
        assertNull(encounterHospitalization.reAdmission)
        assertEquals(0, encounterHospitalization.dietPreference.size)
        assertEquals(0, encounterHospitalization.specialCourtesy.size)
        assertEquals(0, encounterHospitalization.specialArrangement.size)
        assertNull(encounterHospitalization.destination)
        assertNull(encounterHospitalization.dischargeDisposition)
    }

    @Test
    fun `function works with parameters`() {
        val encounterHospitalization = encounterHospitalization {
            origin of reference("Location", "123")
        }
        assertNotNull(encounterHospitalization.origin)
    }
}

class EncounterLocationGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val encounterLocation = encounterLocation {}
        assertNull(encounterLocation.id)
        assertEquals(0, encounterLocation.extension.size)
        assertEquals(0, encounterLocation.modifierExtension.size)
        assertNotNull(encounterLocation.location)
        assertNull(encounterLocation.status)
        assertNull(encounterLocation.physicalType)
        assertNull(encounterLocation.period)
    }

    @Test
    fun `function works with parameters`() {
        val encounterLocation = encounterLocation {
            status of "active"
        }
        assertEquals(Code("active"), encounterLocation.status)
    }
}

class EncounterStatusHistoryGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val encounterStatusHistory = encounterStatusHistory {}
        assertNull(encounterStatusHistory.id)
        assertEquals(0, encounterStatusHistory.extension.size)
        assertEquals(0, encounterStatusHistory.modifierExtension.size)
        assertNotNull(encounterStatusHistory.status)
        assertNotNull(encounterStatusHistory.period)
    }

    @Test
    fun `function works with parameters`() {
        val encounterStatusHistory = encounterStatusHistory {
            status of "invalid-status"
        }
        assertEquals(Code("invalid-status"), encounterStatusHistory.status)
    }
}
