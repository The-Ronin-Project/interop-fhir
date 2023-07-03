package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ExpressionGeneratorTest {
    @Test
    fun `function works with default`() {
        val expression = expression {}
        assertNull(expression.id)
        assertEquals(0, expression.extension.size)
        assertNull(expression.description)
        assertNull(expression.name)
        assertNotNull(expression.language)
        assertNull(expression.expression)
        assertNull(expression.reference)
    }

    @Test
    fun `function works with parameters`() {
        val expression = expression {
            expression of "example expression".asFHIR()
        }
        assertEquals(FHIRString("example expression"), expression.expression)
    }
}
