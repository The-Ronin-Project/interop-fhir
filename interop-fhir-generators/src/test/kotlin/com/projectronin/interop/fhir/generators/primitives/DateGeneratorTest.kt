package com.projectronin.interop.fhir.generators.primitives

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class DateGeneratorTest {
    @Test
    fun `function works with default`() {
        val date = date {}
        assertNotNull(date)
    }

    @Test
    fun `function works with parameters`() {
        val date =
            date {
                year of 1990
                day of 3
                month of 1
            }
        assertNotNull(date)
        assertEquals("1990-01-03", date.value)
    }

    @Test
    fun `function works with invalid dates`() {
        val date =
            date {
                year of 2019
                day of 30
                month of 2
            }
        assertNotNull(date)
        assertEquals("2019-02-28", date.value)
    }
}
