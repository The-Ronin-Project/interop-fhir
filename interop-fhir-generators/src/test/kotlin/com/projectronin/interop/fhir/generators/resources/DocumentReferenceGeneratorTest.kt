package com.projectronin.interop.fhir.generators.resources

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class DocumentReferenceGeneratorTest {
    @Test
    fun `resource generated with all required elements`() {
        val docRef = documentReference { }
        assertNotNull(docRef.status)
        assertTrue(docRef.content.isNotEmpty())
    }

    @Test
    fun `context generates correctly`() {
        val docRef = documentReference { context of documentReferenceContext { } }
        assertNotNull(docRef.context)
    }

    @Test
    fun `content generates correctly`() {
        val docRef = documentReference { content of listOf(documentReferenceContent { }) }
        assertNotNull(docRef.content)
        assertEquals(1, docRef.content.size)
    }

    @Test
    fun `relates to generates correctly`() {
        val docRef = documentReference { relatesTo of listOf(documentReferenceRelatesTo { }) }
        assertTrue(docRef.relatesTo.isNotEmpty())
        assertNotNull(docRef.relatesTo.first().code)
        assertNotNull(docRef.relatesTo.first().target)
    }
}
