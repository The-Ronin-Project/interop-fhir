package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ExtensionValidatorTest {
    @Test
    fun `fails if no url provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val extension = Extension(
                url = null,
                value = DynamicValue(DynamicValueType.CODE, Code("I"))
            )
            R4ExtensionValidator.validate(extension).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: url is a required element @ Extension.url",
            exception.message
        )
    }

    @Test
    fun `fails when both extensions and value are provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val extension = Extension(
                id = "12345",
                extension = listOf(
                    Extension(
                        url = Uri("http://localhost/extension"),
                        value = DynamicValue(DynamicValueType.STRING, "Value")
                    )
                ),
                url = Uri("http://hl7.org/fhir/StructureDefinition/iso-21090-EN-use"),
                value = DynamicValue(DynamicValueType.CODE, Code("I"))
            )
            R4ExtensionValidator.validate(extension).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_EXT_001: Extension must have either extensions or value[x], not both @ Extension",
            exception.message
        )
    }

    @Test
    fun `fails when neither extensions nor value are provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val extension = Extension(
                id = "12345",
                url = Uri("http://hl7.org/fhir/StructureDefinition/iso-21090-EN-use"),
            )
            R4ExtensionValidator.validate(extension).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_EXT_001: Extension must have either extensions or value[x], not both @ Extension",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val extension = Extension(
            url = Uri("http://hl7.org/fhir/StructureDefinition/iso-21090-EN-use"),
            value = DynamicValue(DynamicValueType.CODE, Code("I"))
        )
        R4ExtensionValidator.validate(extension).alertIfErrors()
    }
}
