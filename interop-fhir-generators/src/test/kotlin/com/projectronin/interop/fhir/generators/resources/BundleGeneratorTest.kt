package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class BundleGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val bundle = bundle {}
        assertNull(bundle.id)
        assertNull(bundle.identifier)
        assertNull(bundle.type)
    }

    @Test
    fun `function works with parameters`() {
        val bundle = bundle {
            id of Id("id")
            identifier of listOf(
                identifier {
                    value of "identifier"
                }
            )
            type of "type"
        }

        assertEquals("id", bundle.id?.value)
        assertEquals("identifier", bundle.identifier?.value?.value)
        assertEquals("type", bundle.type?.value)
    }
}
