package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class NarrativeTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val narrative = Narrative(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            status = NarrativeStatus.GENERATED.asCode(),
            div = FHIRString("narrative text")
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(narrative)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "status" : "generated",
            |  "div" : "narrative text"
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedNarrative = objectMapper.readValue<Narrative>(json)
        assertEquals(narrative, deserializedNarrative)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val narrative = Narrative(status = NarrativeStatus.GENERATED.asCode(), div = FHIRString("narrative text"))
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(narrative)

        val expectedJson = """
            |{
            |  "status" : "generated",
            |  "div" : "narrative text"
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "status" : "generated",
            |  "div" : "narrative text"
            |}
        """.trimMargin()
        val narrative = objectMapper.readValue<Narrative>(json)

        assertNull(narrative.id)
        assertEquals(listOf<Extension>(), narrative.extension)
        assertEquals(NarrativeStatus.GENERATED.asCode(), narrative.status)
        assertEquals(FHIRString("narrative text"), narrative.div)
    }
}
