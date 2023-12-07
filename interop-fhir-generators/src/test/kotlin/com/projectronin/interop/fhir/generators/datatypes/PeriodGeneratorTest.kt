package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.dateTime
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PeriodGeneratorTest {
    @Test
    fun `function works with default`() {
        val period = period {}
        assertNull(period.start)
        assertNull(period.end)
    }

    @Test
    fun `function works with parameters`() {
        val period =
            period {
                start of dateTime { year of 1990 }
                end of dateTime { year of 1990 }
            }
        assertTrue(period.start?.value?.startsWith("1990")!!)
        assertTrue(period.end?.value?.startsWith("1990")!!)
    }
}
