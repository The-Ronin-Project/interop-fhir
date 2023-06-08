package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class DurationGeneratorTest {
    @Test
    fun `function works with default`() {
        val duration = duration {}
        assertNull(duration.id)
        assertEquals(0, duration.extension.size)
        assertNull(duration.value)
        assertNull(duration.comparator)
        assertNull(duration.unit)
        assertNull(duration.system)
        assertNull(duration.code)
    }

    @Test
    fun `function works with parameters`() {
        val duration = duration {
            value of BigDecimal(12.5)
            unit of "cm"
        }
        assertEquals(Decimal(BigDecimal(12.5)), duration.value)
        assertEquals(FHIRString("cm"), duration.unit)
    }
}
