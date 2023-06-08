package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class DecimalGeneratorTest {
    @Test
    fun `generates null by default`() {
        val generator = DecimalGenerator()
        val decimal = generator.generate()
        assertNull(decimal)
    }

    @Test
    fun `supports infix extension for Decimal`() {
        val decimal = DecimalGenerator()
        decimal of BigDecimal(3407.5)
        assertEquals(BigDecimal(3407.5), decimal.generate()?.value)
    }

    @Test
    fun `supports infix extension for Decimal Double`() {
        val decimal = DecimalGenerator()
        decimal of 3407.5
        assertEquals(BigDecimal(3407.5), decimal.generate()?.value)
    }

    @Test
    fun `supports infix setter for Decimal`() {
        val generator = DecimalGenerator()
        generator of Decimal(BigDecimal(150.932))
        val decimal = generator.generate()
        assertEquals(Decimal(BigDecimal(150.932)), decimal)
    }
}
