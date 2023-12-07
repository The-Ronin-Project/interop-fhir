package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Expression
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ExpressionValidatorTest {
    @Test
    fun `fails if no language`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val expression =
                    Expression(
                        language = null,
                        expression = FHIRString("expression"),
                    )
                R4ExpressionValidator.validate(expression).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: language is a required element @ Expression.language",
            exception.message,
        )
    }

    @Test
    fun `fails if no expression or reference`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val expression = Expression(language = Code("en-US"))
                R4ExpressionValidator.validate(expression).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_EXPR_001: An expression or a reference must be provided @ Expression",
            exception.message,
        )
    }

    @Test
    fun `validates successfully`() {
        val expression =
            Expression(
                language = Code("en-US"),
                expression = FHIRString("expression"),
            )
        R4ExpressionValidator.validate(expression).alertIfErrors()
    }
}
