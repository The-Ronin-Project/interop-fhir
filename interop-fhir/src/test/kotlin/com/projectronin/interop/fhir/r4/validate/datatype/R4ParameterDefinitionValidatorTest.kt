package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.ParameterDefinition
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.valueset.OperationParameterUse
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ParameterDefinitionValidatorTest {
    @Test
    fun `fails if no use provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val parameterDefinition =
                ParameterDefinition(
                    use = null,
                    type = Code("integer")
                )
            R4ParameterDefinitionValidator.validate(parameterDefinition).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: use is a required element @ ParameterDefinition.use",
            exception.message
        )
    }

    @Test
    fun `fails if use is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val parameterDefinition =
                ParameterDefinition(
                    use = Code("unsupported-use"),
                    type = Code("integer")
                )
            R4ParameterDefinitionValidator.validate(parameterDefinition).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-use' is outside of required value set @ ParameterDefinition.use",
            exception.message
        )
    }

    @Test
    fun `fails if no type provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val parameterDefinition =
                ParameterDefinition(
                    use = OperationParameterUse.OUTPUT.asCode(),
                    type = null
                )
            R4ParameterDefinitionValidator.validate(parameterDefinition).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: type is a required element @ ParameterDefinition.type",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val parameterDefinition =
            ParameterDefinition(
                use = OperationParameterUse.OUTPUT.asCode(),
                type = Code("integer")
            )
        R4ParameterDefinitionValidator.validate(parameterDefinition).alertIfErrors()
    }
}
