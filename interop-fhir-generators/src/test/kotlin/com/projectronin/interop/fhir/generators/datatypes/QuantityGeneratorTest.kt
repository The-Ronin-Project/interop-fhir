package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class QuantityGeneratorTest {
    @Test
    fun `function works with default`() {
        val quantity = quantity {}
        assertNull(quantity.value)
        assertNull(quantity.comparator)
        assertNull(quantity.unit)
        assertNull(quantity.system)
        assertNull(quantity.code)
    }

    @Test
    fun `function works with parameters`() {
        val quantity =
            quantity {
                value of BigDecimal(1.2345)
                comparator of Code("compCode")
                unit of "centimeters"
                system of Uri("system")
                code of Code("code")
            }
        assertEquals(BigDecimal(1.2345), quantity.value?.value)
        assertEquals("compCode", quantity.comparator?.value)
        assertEquals("centimeters", quantity.unit?.value)
        assertEquals("system", quantity.system?.value)
        assertEquals("code", quantity.code?.value)
    }
}
