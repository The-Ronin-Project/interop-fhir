package com.projectronin.interop.fhir.generators.primitives

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class DateTimeGeneratorTest {
    @Test
    fun `function works with default`() {
        val date = dateTime {}
        assertNotNull(date)
    }

    @Test
    fun `function works with parameters`() {
        val date =
            dateTime {
                year of 1990
                day of 3
                month of 1
            }
        assertNotNull(date)
        assertEquals("1990-01-03", date.value)
    }
}
