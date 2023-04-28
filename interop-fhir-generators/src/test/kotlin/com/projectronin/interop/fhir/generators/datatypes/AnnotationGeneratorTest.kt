package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class AnnotationGeneratorTest {
    @Test
    fun `function works with default`() {
        val annotation = annotation {}
        assertNull(annotation.id)
        assertEquals(0, annotation.extension.size)
        assertNull(annotation.author)
        assertNull(annotation.time)
        assertNotNull(annotation.text)
    }

    @Test
    fun `function works with parameters`() {
        val annotation = annotation {
            author of DynamicValues.string("Josh Smith")
        }
        assertEquals(DynamicValue(DynamicValueType.STRING, FHIRString("Josh Smith")), annotation.author)
    }
}
