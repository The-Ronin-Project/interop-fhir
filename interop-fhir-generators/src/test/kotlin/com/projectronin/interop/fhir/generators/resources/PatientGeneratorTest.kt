package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.name
import com.projectronin.interop.fhir.generators.primitives.date
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
        assertTrue(patient.identifier.isEmpty())
        assertEquals(1, patient.name.size)
        assertNull(patient.gender)
        assertNotNull(patient.birthDate)
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
