package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.TriggerType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TriggerDefinitionTest {
    @Test
    fun `Fails when both timing and data requirement are provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                TriggerDefinition(
                    type = TriggerType.PERIODIC,
                    timing = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2022-02-22")),
                    data = listOf(DataRequirement(type = Code("type")))
                )
            }
        assertEquals("Either timing, or a data requirement, but not both", exception.message)
    }

    @Test
    fun `Fails when neither timing nor data requirement are provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                TriggerDefinition(
                    type = TriggerType.NAMED_EVENT,
                    name = "any"
                )
            }
        assertEquals("Either timing, or a data requirement, but not both", exception.message)
    }

    @Test
    fun `fails for unsupported dynamic value type`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                TriggerDefinition(
                    type = TriggerType.PERIODIC,
                    timing = DynamicValue(DynamicValueType.INTEGER, 1)
                )
            }
        assertEquals(
            "timing can only be one of the following: Timing, Reference, Date, DateTime",
            exception.message
        )
    }

    @Test
    fun `can serialize and deserialize JSON`() {
        val triggerDefinition = TriggerDefinition(
            id = "12345",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            type = TriggerType.DATA_MODIFIED,
            name = "trigger name",
            data = listOf(DataRequirement(type = Code("data-type-code"))),
            condition = Expression(language = Code("en-US"), expression = "Expression")
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
            type = TriggerType.NAMED_EVENT,
            name = "any",
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
        assertEquals(TriggerType.PERIODIC, triggerDefinition.type)
        assertNull(triggerDefinition.name)
        assertEquals(listOf<DataRequirement>(), triggerDefinition.data)
        assertNull(triggerDefinition.condition)
    }

    @Test
    fun `A condition only if there is a data requirement`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                TriggerDefinition(
                    type = TriggerType.PERIODIC,
                    timing = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2022-02-22")),
                    condition = Expression(expression = "where", language = Code("py"))
                )
            }
        assertEquals("A condition only if there is a data requirement", exception.message)
    }

    @Test
    fun `A named event requires a name`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                TriggerDefinition(
                    type = TriggerType.NAMED_EVENT,
                    timing = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2022-02-22")),
                )
            }
        assertEquals("A named event requires a name", exception.message)
    }

    @Test
    fun `periodic event requires timing`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                TriggerDefinition(
                    type = TriggerType.PERIODIC,
                    data = listOf(DataRequirement(type = Code("type")))
                )
            }
        assertEquals("A periodic event requires timing", exception.message)
    }

    @Test
    fun `A data event requires data`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                TriggerDefinition(
                    type = TriggerType.DATA_ADDED,
                    timing = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2022-02-22")),
                )
            }
        assertEquals("A data event requires data", exception.message)
    }
}
