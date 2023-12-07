package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.OperationParameterUse
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ParameterDefinitionTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val parameterDefinition =
            ParameterDefinition(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                name = Code("parameter name"),
                use = OperationParameterUse.INPUT.asCode(),
                min = FHIRInteger(1),
                max = FHIRString("3"),
                documentation = FHIRString("parameter documentation"),
                type = Code("integer"),
                profile = Canonical("profile-canonical"),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(parameterDefinition)

        val expectedJson =
            """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "name" : "parameter name",
            |  "use" : "in",
            |  "min" : 1,
            |  "max" : "3",
            |  "documentation" : "parameter documentation",
            |  "type" : "integer",
            |  "profile" : "profile-canonical"
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedParameterDefinition = objectMapper.readValue<ParameterDefinition>(json)
        assertEquals(parameterDefinition, deserializedParameterDefinition)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val parameterDefinition =
            ParameterDefinition(use = OperationParameterUse.OUTPUT.asCode(), type = Code("integer"))
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(parameterDefinition)

        val expectedJson =
            """
            |{
            |  "use" : "out",
            |  "type" : "integer"
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            |{
            |  "use" : "out",
            |  "type" : "integer"
            |}
            """.trimMargin()
        val parameterDefinition = objectMapper.readValue<ParameterDefinition>(json)

        assertNull(parameterDefinition.id)
        assertEquals(listOf<Extension>(), parameterDefinition.extension)
        assertNull(parameterDefinition.name)
        assertEquals(OperationParameterUse.OUTPUT.asCode(), parameterDefinition.use)
        assertNull(parameterDefinition.min)
        assertNull(parameterDefinition.max)
        assertNull(parameterDefinition.documentation)
        assertEquals(Code("integer"), parameterDefinition.type)
        assertNull(parameterDefinition.profile)
    }
}
