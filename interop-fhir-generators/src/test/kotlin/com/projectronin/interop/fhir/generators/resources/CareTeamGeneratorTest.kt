package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CareTeamGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val careTeam = careTeam {}
        assertNull(careTeam.id)
        assertNull(careTeam.meta)
        assertNull(careTeam.implicitRules)
        assertNull(careTeam.language)
        assertNull(careTeam.text)
        assertEquals(0, careTeam.contained.size)
        assertEquals(0, careTeam.extension.size)
        assertEquals(0, careTeam.modifierExtension.size)
        assertTrue(careTeam.identifier.isEmpty())
        assertNotNull(careTeam.subject)
        assertNull(careTeam.status)
    }

    @Test
    fun `function works with parameters`() {
        val careTeam = careTeam {
            id of Id("id")
            identifier of listOf(
                identifier {
                    value of "identifier"
                }
            )
            subject of reference("Patient", "123")
            status of "status"
        }

        assertEquals("id", careTeam.id?.value)
        assertEquals(1, careTeam.identifier.size)
        assertEquals("identifier", careTeam.identifier?.first()?.value?.value)
        assertEquals("Patient/123", careTeam.subject?.reference?.value)
        assertEquals("status", careTeam.status?.value)
    }
}
