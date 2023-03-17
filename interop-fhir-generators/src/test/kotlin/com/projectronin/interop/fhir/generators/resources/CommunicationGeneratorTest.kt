package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CommunicationGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val communication = communication {}
        assertNull(communication.id)
        assertTrue(communication.identifier.isEmpty())
        assertNull(communication.status)
    }

    @Test
    fun `function works with parameters`() {
        val communication = communication {
            id of Id("id")
            identifier of listOf(
                identifier {
                    value of "identifier"
                }
            )
            status of "status"
        }

        assertEquals("id", communication.id?.value)
        assertEquals(1, communication.identifier.size)
        assertEquals("identifier", communication.identifier?.first()?.value?.value)
        assertEquals("status", communication.status?.value)
    }
}
