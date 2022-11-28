package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.IdentifierUse
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class IdentifierTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val identifier = Identifier(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            use = IdentifierUse.OFFICIAL.asCode(),
            type = CodeableConcept(text = FHIRString("concept")),
            system = Uri("identifier-system"),
            value = FHIRString("identifier value"),
            period = Period(start = DateTime("2021-11-01")),
            assigner = Reference(
                reference = FHIRString("Organization/123"),
                type = Uri("Organization")
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(identifier)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "use" : "official",
            |  "type" : {
            |    "text" : "concept"
            |  },
            |  "system" : "identifier-system",
            |  "value" : "identifier value",
            |  "period" : {
            |    "start" : "2021-11-01"
            |  },
            |  "assigner" : {
            |    "reference" : "Organization/123",
            |    "type" : "Organization"
            |  }
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedIdentifier = JacksonManager.objectMapper.readValue<Identifier>(json)
        assertEquals(identifier, deserializedIdentifier)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val identifier = Identifier(use = IdentifierUse.OFFICIAL.asCode())
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(identifier)

        val expectedJson = """
            |{
            |  "use" : "official"
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "value" : "identifier"
            |}""".trimMargin()
        val identifier = JacksonManager.objectMapper.readValue<Identifier>(json)

        assertNull(identifier.id)
        assertEquals(listOf<Extension>(), identifier.extension)
        assertNull(identifier.use)
        assertNull(identifier.type)
        assertNull(identifier.system)
        assertEquals(FHIRString("identifier"), identifier.value)
        assertNull(identifier.period)
        assertNull(identifier.assigner)
    }

    @Test
    fun `can serialize and deserialize JSON with value primitive data`() {
        val identifier = Identifier(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            use = IdentifierUse.OFFICIAL.asCode(),
            type = CodeableConcept(text = FHIRString("concept")),
            system = Uri("identifier-system"),
            value = FHIRString(
                value = "identifier value",
                id = FHIRString("valueDataId"),
                extension = listOf(
                    Extension(
                        url = Uri("http://localhost/primitiveExtension"),
                        value = DynamicValue(DynamicValueType.STRING, FHIRString("value"))
                    )
                )
            ),
            period = Period(start = DateTime("2021-11-01")),
            assigner = Reference(
                reference = FHIRString("Organization/123"),
                type = Uri("Organization")
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(identifier)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "use" : "official",
            |  "type" : {
            |    "text" : "concept"
            |  },
            |  "system" : "identifier-system",
            |  "value" : "identifier value",
            |  "_value" : {
            |    "id" : "valueDataId",
            |    "extension" : [ {
            |      "url" : "http://localhost/primitiveExtension",
            |      "valueString" : "value"
            |    } ]
            |  },
            |  "period" : {
            |    "start" : "2021-11-01"
            |  },
            |  "assigner" : {
            |    "reference" : "Organization/123",
            |    "type" : "Organization"
            |  }
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedIdentifier = JacksonManager.objectMapper.readValue<Identifier>(json)
        assertEquals(identifier, deserializedIdentifier)
    }

    @Test
    fun `can deserialize from JSON with value data and no value`() {
        val json = """
            |{
            |  "use" : "usual",
            |  "system" : "urn:oid:2.16.840.1.113883.4.1",
            |  "_value" : {
            |    "extension" : [ {
            |      "valueString" : "xxx-xx-1234",
            |      "url" : "http://hl7.org/fhir/StructureDefinition/rendered-value"
            |    } ]
            |  }
            |}""".trimMargin()
        val identifier = JacksonManager.objectMapper.readValue<Identifier>(json)

        assertNull(identifier.id)
        assertEquals(listOf<Extension>(), identifier.extension)
        assertEquals(Code("usual"), identifier.use)
        assertNull(identifier.type)
        assertEquals(Uri("urn:oid:2.16.840.1.113883.4.1"), identifier.system)
        assertEquals(
            FHIRString(
                value = null,
                extension = listOf(
                    Extension(
                        url = Uri("http://hl7.org/fhir/StructureDefinition/rendered-value"),
                        value = DynamicValue(DynamicValueType.STRING, FHIRString("xxx-xx-1234"))
                    )
                )
            ),
            identifier.value
        )
        assertNull(identifier.period)
        assertNull(identifier.assigner)
    }
}
