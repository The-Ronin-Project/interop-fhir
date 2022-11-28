package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CodeFilter
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4CodeFilterValidatorTest {
    @Test
    fun `fails for both path and searchParam provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val codeFilter = CodeFilter(path = FHIRString("path"), searchParam = FHIRString("search"))
                R4CodeFilterValidator.validate(codeFilter).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CDFILT_001: Either a path or a searchParam must be provided, but not both @ CodeFilter",
            exception.message
        )
    }

    @Test
    fun `fails for neither path nor searchParam provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val codeFilter = CodeFilter(id = FHIRString("filter"))
                R4CodeFilterValidator.validate(codeFilter).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CDFILT_001: Either a path or a searchParam must be provided, but not both @ CodeFilter",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val codeFilter = CodeFilter(path = FHIRString("path"))
        R4CodeFilterValidator.validate(codeFilter).alertIfErrors()
    }
}
