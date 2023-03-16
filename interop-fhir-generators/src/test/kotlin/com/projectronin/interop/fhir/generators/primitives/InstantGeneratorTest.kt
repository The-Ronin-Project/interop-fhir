package com.projectronin.interop.fhir.generators.primitives

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class InstantGeneratorTest {

    @Test
    fun `function works with default`() {
        val instant = instant {}
        assertNotNull(instant.value)
    }

    @Test
    fun `function works with parameters`() {
        val instant = instant {
            year of 1990
            day of 3
            month of 1
            hour of 12
            minute of 11
            second of 1
        }
        assertNotNull(instant)
        assertEquals("1990-01-03T12:11:01Z", instant.value)
    }

    @Test
    fun `days ago works`() {
        val instant = 2.daysAgo()
        assertNotNull(instant.value)
    }

    @Test
    fun `days from now works`() {
        val instant = 2.daysFromNow()
        assertNotNull(instant.value)
    }
}
