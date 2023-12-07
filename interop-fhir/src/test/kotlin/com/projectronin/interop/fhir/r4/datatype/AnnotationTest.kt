package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

@Suppress("ktlint:standard:max-line-length")
class AnnotationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val annotation =
            Annotation(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                author = DynamicValue(DynamicValueType.STRING, FHIRString("Author")),
                time = DateTime("2021-11-17"),
                text = Markdown("Markdown text"),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(annotation)

        val expectedJson =
            """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "authorString" : "Author",
            |  "time" : "2021-11-17",
            |  "text" : "Markdown text"
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedAnnotation = objectMapper.readValue<Annotation>(json)
        assertEquals(annotation, deserializedAnnotation)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val annotation =
            Annotation(
                text = Markdown("Markdown text"),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(annotation)

        val expectedJson =
            """
            |{
            |  "text" : "Markdown text"
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            |{
            |  "text" : "Markdown text"
            |}
            """.trimMargin()
        val annotation = objectMapper.readValue<Annotation>(json)

        assertNull(annotation.id)
        assertEquals(listOf<Extension>(), annotation.extension)
        assertNull(annotation.author)
        assertNull(annotation.time)
        assertEquals(Markdown("Markdown text"), annotation.text)
    }

    @Test
    fun `can serialize and deserialize JSON with markdown extensions`() {
        val annotation =
            Annotation(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                author = DynamicValue(DynamicValueType.STRING, FHIRString("Author")),
                time = DateTime("2021-11-17"),
                text =
                    Markdown(
                        value = "Markdown text",
                        id = FHIRString("67890"),
                        extension =
                            listOf(
                                Extension(
                                    url =
                                        Uri(
                                            "http://projectronin.io/fhir/ronin.common-fhir-model.uscore-r4/StructureDefinition/Extension/tenant-sourceTelecomSystem",
                                        ),
                                    value = DynamicValue(DynamicValueType.CODE, Code("e-mail")),
                                ),
                            ),
                    ),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(annotation)

        val expectedJson =
            """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "authorString" : "Author",
            |  "time" : "2021-11-17",
            |  "text" : "Markdown text",
            |  "_text" : {
            |    "id" : "67890",
            |    "extension" : [ {
            |      "url" : "http://projectronin.io/fhir/ronin.common-fhir-model.uscore-r4/StructureDefinition/Extension/tenant-sourceTelecomSystem",
            |      "valueCode" : "e-mail"
            |    } ]
            |  }
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedAnnotation = objectMapper.readValue<Annotation>(json)
        assertEquals(annotation, deserializedAnnotation)
    }
}
