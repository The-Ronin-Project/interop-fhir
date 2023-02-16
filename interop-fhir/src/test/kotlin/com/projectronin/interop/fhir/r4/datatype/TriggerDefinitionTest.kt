package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.TriggerType
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class TriggerDefinitionTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val triggerDefinition = TriggerDefinition(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            type = TriggerType.DATA_MODIFIED.asCode(),
            name = FHIRString("trigger name"),
            data = listOf(DataRequirement(type = Code("data-type-code"))),
            condition = Expression(language = Code("en-US"), expression = FHIRString("Expression"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(triggerDefinition)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "type" : "data-modified",
            |  "name" : "trigger name",
            |  "data" : [ {
            |    "type" : "data-type-code"
            |  } ],
            |  "condition" : {
            |    "language" : "en-US",
            |    "expression" : "Expression"
            |  }
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedTriggerDefinition = objectMapper.readValue<TriggerDefinition>(json)
        assertEquals(triggerDefinition, deserializedTriggerDefinition)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val triggerDefinition = TriggerDefinition(
            type = TriggerType.NAMED_EVENT.asCode(),
            name = FHIRString("any"),
            data = listOf(DataRequirement(type = Code("data-type-code"))),
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(triggerDefinition)

        val expectedJson = """
            |{
            |  "type" : "named-event",
            |  "name" : "any",
            |  "data" : [ {
            |    "type" : "data-type-code"
            |  } ]
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "type" : "periodic",
            |  "timingDate" : "2022-02-22"
            |}""".trimMargin()
        val triggerDefinition = objectMapper.readValue<TriggerDefinition>(json)

        assertNull(triggerDefinition.id)
        assertEquals(listOf<Extension>(), triggerDefinition.extension)
        assertEquals(TriggerType.PERIODIC.asCode(), triggerDefinition.type)
        assertNull(triggerDefinition.name)
        assertEquals(listOf<DataRequirement>(), triggerDefinition.data)
        assertNull(triggerDefinition.condition)
    }
}
