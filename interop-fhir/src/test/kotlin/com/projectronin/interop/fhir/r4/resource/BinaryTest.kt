package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Base64Binary
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class BinaryTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val binary =
            Binary(
                id = Id("12345"),
                meta =
                    Meta(
                        profile = listOf(Canonical("Binary")),
                    ),
                implicitRules = Uri("implicit-rules"),
                language = Code("en-US"),
                contentType = Code("text/plain"),
                securityContent = Reference(display = FHIRString("securityContent")),
                data = Base64Binary("zyxw"),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(binary)

        val expectedJson =
            """
            {
              "resourceType" : "Binary",
              "id" : "12345",
              "meta" : {
                "profile" : [ "Binary" ]
              },
              "implicitRules" : "implicit-rules",
              "language" : "en-US",
              "contentType" : "text/plain",
              "securityContent" : {
                "display" : "securityContent"
              },
              "data" : "zyxw"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedBinary = objectMapper.readValue<Binary>(json)
        assertEquals(binary, deserializedBinary)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val binary =
            Binary(
                contentType = Code("text/plain"),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(binary)

        val expectedJson =
            """
            {
              "resourceType" : "Binary",
              "contentType" : "text/plain"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "resourceType" : "Binary",
              "contentType" : "text/plain"
            }
            """.trimIndent()
        val binary = objectMapper.readValue<Binary>(json)

        assertNull(binary.id)
        assertNull(binary.meta)
        assertNull(binary.implicitRules)
        assertNull(binary.language)
        assertEquals(Code("text/plain"), binary.contentType)
        assertNull(binary.securityContent)
        assertNull(binary.data)
    }
}
