package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.fhir.r4.valueset.AdministrativeGender
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CodeGeneratorTest {
    @Test
    fun `generator with no possible values works`() {
        val generator = CodeGenerator()
        val code = generator.generate()
        assertNull(code)
    }

    @Test
    fun `generator with possible values works`() {
        val possible = listOf("a", "b", "c")
        val generator = CodeGenerator(possible)
        assertTrue(generator.generate()?.value in possible)
    }

    @Test
    fun `generator with enum class works`() {
        val generator = CodeGenerator(AdministrativeGender::class)
        val code = generator.generate()
        val enum = CodedEnum.byCode<AdministrativeGender>(code!!.value!!)
        assertNotNull(enum)
    }

    @Test
    fun `infix extension works`() {
        val code = CodeGenerator()
        code of "123"
        assertEquals("123", code.generate()?.value)
    }
}
