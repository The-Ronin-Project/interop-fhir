package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class SimpleQuantityGeneratorTest {
    @Test
    fun `function works with default`() {
        val quantity = simpleQuantity {}
        assertNull(quantity.value)
        assertNull(quantity.unit)
        assertNull(quantity.system)
        assertNull(quantity.code)
    }

    @Test
    fun `function works with parameters`() {
        val quantity = simpleQuantity {
            value of 1.23
            unit of "centimeters"
            system of Uri("mySystem")
            code of Code("myCode")
        }
        assertEquals(Decimal(1.23), quantity.value)
        assertEquals("centimeters".asFHIR(), quantity.unit)
        assertEquals(Uri("mySystem"), quantity.system)
        assertEquals(Code("myCode"), quantity.code)
    }
}
