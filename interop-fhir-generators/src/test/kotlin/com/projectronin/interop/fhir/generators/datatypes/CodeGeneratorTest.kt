package com.projectronin.interop.fhir.generators.datatypes

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class CodeGeneratorTest {
    @Test
    fun `generator works`() {
        val generator = CodeGenerator()
        val code = generator.generate()
        assertNull(code)
    }

    @Test
    fun `infix works`() {
        val code = CodeGenerator()
        code of "123"
        assertEquals("123", code.generate()?.value)
    }
}
