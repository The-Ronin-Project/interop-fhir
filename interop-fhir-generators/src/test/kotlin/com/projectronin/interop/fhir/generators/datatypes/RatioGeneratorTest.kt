package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class RatioGeneratorTest {
    @Test
    fun `function works with default`() {
        val ratio = ratio {}
        assertNull(ratio.numerator)
        assertNull(ratio.denominator)
    }

    @Test
    fun `function works with parameters`() {
        val ratio =
            ratio {
                numerator of Quantity(value = Decimal(BigDecimal(2.0)))
                denominator of Quantity(value = Decimal(BigDecimal(3.5)))
            }
        assertEquals(Quantity(value = Decimal(BigDecimal(2.0))), ratio.numerator)
        assertEquals(Quantity(value = Decimal(BigDecimal(3.5))), ratio.denominator)
    }
}
