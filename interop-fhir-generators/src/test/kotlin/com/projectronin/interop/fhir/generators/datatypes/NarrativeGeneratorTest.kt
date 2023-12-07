package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class NarrativeGeneratorTest {
    @Test
    fun `function works with default`() {
        val narrative = narrative { }
        assertNull(narrative.id)
        assertEquals(0, narrative.extension.size)
        assertNull(narrative.status)
        assertNull(narrative.div)
    }

    @Test
    fun `function works with parameters`() {
        val narrative =
            narrative {
                status of "complete"
                div of "<div>data</div>"
            }
        assertEquals(Code("complete"), narrative.status)
        assertEquals(FHIRString("<div>data</div>"), narrative.div)
    }
}
