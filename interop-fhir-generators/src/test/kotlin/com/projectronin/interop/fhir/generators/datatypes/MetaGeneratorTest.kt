package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class MetaGeneratorTest {
    @Test
    fun `function works with default`() {
        val meta = meta { }
        assertNull(meta.id)
        assertEquals(0, meta.extension.size)
        assertNull(meta.versionId)
        assertNull(meta.lastUpdated)
        assertNull(meta.source)
        assertEquals(0, meta.profile.size)
        assertEquals(0, meta.security.size)
        assertEquals(0, meta.tag.size)
    }

    @Test
    fun `function works with parameters`() {
        val meta =
            meta {
                profile plus Canonical("profile")
            }
        assertEquals(listOf(Canonical("profile")), meta.profile)
    }
}
