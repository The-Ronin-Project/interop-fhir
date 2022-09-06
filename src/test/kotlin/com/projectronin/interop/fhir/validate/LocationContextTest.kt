package com.projectronin.interop.fhir.validate

import com.projectronin.interop.fhir.r4.resource.Patient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class LocationContextTest {
    @Test
    fun `creates from element and field`() {
        val locationContext = LocationContext("Test", "field")
        assertEquals("Test", locationContext.element)
        assertEquals("field", locationContext.field)
    }

    @Test
    fun `creates from class`() {
        val locationContext = LocationContext(Patient::class)
        assertEquals("Patient", locationContext.element)
        assertNull(locationContext.field)
    }

    @Test
    fun `creates from method`() {
        val locationContext = LocationContext(Patient::identifier)
        assertEquals("Patient", locationContext.element)
        assertEquals("identifier", locationContext.field)
    }

    @Test
    fun `toString for context with field`() {
        val locationContext = LocationContext("Test", "field")
        assertEquals("Test.field", locationContext.toString())
    }

    @Test
    fun `toString for context with no field`() {
        val locationContext = LocationContext("Test", null)
        assertEquals("Test", locationContext.toString())
    }

    @Test
    fun `appends to null LocationContext`() {
        val newContext = LocationContext("Test", "field")
        val oldContext = null

        val appendedContext = oldContext.append(newContext)
        assertEquals("Test", appendedContext.element)
        assertEquals("field", appendedContext.field)
    }

    @Test
    fun `appends context with null field to context with null field`() {
        val newContext = LocationContext("Test", null)
        val oldContext = LocationContext("Sample", null)

        val appendedContext = oldContext.append(newContext)
        assertEquals("Sample", appendedContext.element)
        assertNull(appendedContext.field)
    }

    @Test
    fun `appends context with null field to context with field`() {
        val newContext = LocationContext("Test", null)
        val oldContext = LocationContext("Sample", "id")

        val appendedContext = oldContext.append(newContext)
        assertEquals("Sample", appendedContext.element)
        assertEquals("id", appendedContext.field)
    }

    @Test
    fun `appends context with field to context with null field`() {
        val newContext = LocationContext("Test", "field")
        val oldContext = LocationContext("Sample", null)

        val appendedContext = oldContext.append(newContext)
        assertEquals("Sample", appendedContext.element)
        assertEquals("field", appendedContext.field)
    }

    @Test
    fun `appends context with field to context with empty field`() {
        val newContext = LocationContext("Test", "field")
        val oldContext = LocationContext("Sample", "")

        val appendedContext = oldContext.append(newContext)
        assertEquals("Sample", appendedContext.element)
        assertEquals("field", appendedContext.field)
    }

    @Test
    fun `appends context with field to context with field`() {
        val newContext = LocationContext("Test", "field")
        val oldContext = LocationContext("Sample", "id")

        val appendedContext = oldContext.append(newContext)
        assertEquals("Sample", appendedContext.element)
        assertEquals("id.field", appendedContext.field)
    }
}
