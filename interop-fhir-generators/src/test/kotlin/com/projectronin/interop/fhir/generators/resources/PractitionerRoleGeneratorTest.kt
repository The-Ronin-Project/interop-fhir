package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PractitionerRoleGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val practitionerRole = practitionerRole {}
        assertNull(practitionerRole.id)
        assertNull(practitionerRole.meta)
        assertNull(practitionerRole.implicitRules)
        assertNull(practitionerRole.language)
        assertNull(practitionerRole.text)
        assertEquals(0, practitionerRole.contained.size)
        assertEquals(0, practitionerRole.extension.size)
        assertEquals(0, practitionerRole.modifierExtension.size)
        assertTrue(practitionerRole.identifier.isEmpty())
        assertNotNull(practitionerRole.practitioner)
        assertTrue(practitionerRole.location.isEmpty())
    }

    @Test
    fun `function works with parameters`() {
        val practitionerRole =
            practitionerRole {
                id of Id("id")
                identifier of
                    listOf(
                        identifier {},
                    )
                practitioner of reference("Practitioner", "123")
                location of
                    listOf(
                        reference("Location", "123"),
                    )
            }
        assertEquals("id", practitionerRole.id?.value)
        assertEquals(1, practitionerRole.identifier.size)
        assertEquals("Practitioner/123", practitionerRole.practitioner?.reference?.value)
        assertEquals("Location/123", practitionerRole.location.first().reference?.value)
    }
}
