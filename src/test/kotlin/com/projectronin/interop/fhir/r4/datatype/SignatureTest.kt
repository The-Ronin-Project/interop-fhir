package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.Base64Binary
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class SignatureTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val signature = Signature(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            type = listOf(Coding(display = FHIRString("coding-type"))),
            `when` = Instant("2017-01-01T00:00:00Z"),
            who = Reference(display = FHIRString("Reference")),
            onBehalfOf = Reference(display = FHIRString("onBehalfOf Reference")),
            targetFormat = Code("target-format"),
            sigFormat = Code("sig-format"),
            data = Base64Binary("1234")
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(signature)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "type" : [ {
            |    "display" : "coding-type"
            |  } ],
            |  "when" : "2017-01-01T00:00:00Z",
            |  "who" : {
            |    "display" : "Reference"
            |  },
            |  "onBehalfOf" : {
            |    "display" : "onBehalfOf Reference"
            |  },
            |  "targetFormat" : "target-format",
            |  "sigFormat" : "sig-format",
            |  "data" : "1234"
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedSignature = objectMapper.readValue<Signature>(json)
        assertEquals(signature, deserializedSignature)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val signature = Signature(
            type = listOf(Coding(display = FHIRString("coding-type"))),
            `when` = Instant("2017-01-01T00:00:00Z"),
            who = Reference(display = FHIRString("Reference"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(signature)

        val expectedJson = """
            |{
            |  "type" : [ {
            |    "display" : "coding-type"
            |  } ],
            |  "when" : "2017-01-01T00:00:00Z",
            |  "who" : {
            |    "display" : "Reference"
            |  }
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "type" : [ {
            |    "display" : "coding-type"
            |  } ],
            |  "when" : "2017-01-01T00:00:00Z",
            |  "who" : {
            |    "display" : "Reference"
            |  }
            |}""".trimMargin()
        val signature = objectMapper.readValue<Signature>(json)

        assertNull(signature.id)
        assertEquals(listOf<Extension>(), signature.extension)

        val coding = Coding(display = FHIRString("coding-type"))
        assertEquals(listOf(coding), signature.type)

        assertEquals(Instant("2017-01-01T00:00:00Z"), signature.`when`)
        assertEquals(Reference(display = FHIRString("Reference")), signature.who)
        assertNull(signature.onBehalfOf)
        assertNull(signature.targetFormat)
        assertNull(signature.sigFormat)
        assertNull(signature.data)
    }
}
