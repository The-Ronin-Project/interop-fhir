package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class OrganizationGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val organization = organization {}
        assertNull(organization.id)
        assertNull(organization.meta)
        assertNull(organization.implicitRules)
        assertNull(organization.language)
        assertNull(organization.text)
        assertEquals(0, organization.contained.size)
        assertEquals(0, organization.extension.size)
        assertEquals(0, organization.modifierExtension.size)
        assertTrue(organization.identifier.isEmpty())
        assertNotNull(organization.name?.value)
    }

    @Test
    fun `function works with parameters`() {
        val organization = organization {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            name of "name"
        }
        assertEquals("id", organization.id?.value)
        assertEquals(1, organization.identifier.size)
        assertEquals("name", organization.name?.value)
    }
}
