package com.projectronin.interop.fhir.stu3.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class STU3UnknownResourceTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val unknownResource =
            STU3UnknownResource(
                resourceType = "Banana",
                id = Id("12345"),
                meta =
                    Meta(
                        profile = listOf(Canonical("RoninUnknown")),
                    ),
                implicitRules = Uri("implicit-rules"),
                language = Code("en-US"),
                otherData =
                    mapOf(
                        "field1" to "value1",
                        "field2" to 2,
                        "field3" to BigDecimal.valueOf(3.0),
                        "field4" to false,
                        "field5" to listOf("val1", "val2"),
                    ),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(unknownResource)

        val expectedJson =
            """
            {
              "resourceType" : "Banana",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninUnknown" ]
              },
              "implicitRules" : "implicit-rules",
              "language" : "en-US",
              "field1" : "value1",
              "field2" : 2,
              "field3" : 3.0,
              "field4" : false,
              "field5" : [ "val1", "val2" ]
            }
            """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedUnknownResource = objectMapper.readValue<STU3UnknownResource>(json)
        assertEquals(unknownResource, deserializedUnknownResource)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val unknownResource =
            STU3UnknownResource(
                resourceType = "Banana",
                otherData = mapOf("field1" to "value1"),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(unknownResource)

        val expectedJson =
            """
            {
              "resourceType" : "Banana",
              "field1" : "value1"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "resourceType" : "Banana",
              "field1" : "value1"
            }
            """.trimIndent()
        val unknownResource = objectMapper.readValue<STU3UnknownResource>(json)

        assertEquals("Banana", unknownResource.resourceType)
        assertNull(unknownResource.id)
        assertNull(unknownResource.meta)
        assertNull(unknownResource.implicitRules)
        assertNull(unknownResource.language)
        assertEquals(mapOf("field1" to "value1"), unknownResource.otherData)
    }
}
