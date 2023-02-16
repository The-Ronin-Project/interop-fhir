package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LocationGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val location = location {}
        assertNull(location.id)
        assertTrue(location.identifier.isEmpty())
        assertNotNull(location.name)
    }

    @Test
    fun `function works with parameters`() {
        val location = location {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            name of "name"
        }
        assertEquals("id", location.id?.value)
        assertEquals(1, location.identifier.size)
        assertEquals("name", location.name?.value)
    }
}
