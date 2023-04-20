package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ExtensionGeneratorTest {
    @Test
    fun `function works with default`() {
        val extension = extension { }
        assertNull(extension.id)
        assertEquals(0, extension.extension.size)
        assertNull(extension.url)
        assertNull(extension.value)
    }

    @Test
    fun `function works with parameters`() {
        val extension = extension {
            url of Uri("http://example.org/extension")
            value of DynamicValues.boolean(true)
        }
        assertEquals(Uri("http://example.org/extension"), extension.url)
        assertEquals(DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.TRUE), extension.value)
    }
}
