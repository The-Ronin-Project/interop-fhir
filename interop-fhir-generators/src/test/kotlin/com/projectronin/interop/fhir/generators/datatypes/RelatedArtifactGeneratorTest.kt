package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class RelatedArtifactGeneratorTest {
    @Test
    fun `function works with default`() {
        val relatedArtifact = relatedArtifact {}
        assertNull(relatedArtifact.id)
        assertEquals(0, relatedArtifact.extension.size)
        assertNotNull(relatedArtifact.type)
        assertNull(relatedArtifact.label)
        assertNull(relatedArtifact.display)
        assertNull(relatedArtifact.citation)
        assertNull(relatedArtifact.url)
        assertNull(relatedArtifact.document)
        assertNull(relatedArtifact.resource)
    }

    @Test
    fun `function works with parameters`() {
        val relatedArtifact =
            relatedArtifact {
                display of "My Artifact"
                citation of "This is the citation"
            }
        assertEquals(FHIRString("My Artifact"), relatedArtifact.display)
        assertEquals(Markdown("This is the citation"), relatedArtifact.citation)
    }
}
