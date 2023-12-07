package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class UsageContextTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val usageContext =
            UsageContext(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                code = Coding(display = FHIRString("code")),
                value = DynamicValue(DynamicValueType.QUANTITY, Quantity(value = Decimal(1.0))),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(usageContext)

        val expectedJson =
            """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "code" : {
            |    "display" : "code"
            |  },
            |  "valueQuantity" : {
            |    "value" : 1.0
            |  }
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedUsageContext = objectMapper.readValue<UsageContext>(json)
        assertEquals(usageContext, deserializedUsageContext)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val usageContext =
            UsageContext(
                code = Coding(display = FHIRString("code")),
                value = DynamicValue(DynamicValueType.QUANTITY, Quantity(value = Decimal(1.0))),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(usageContext)

        val expectedJson =
            """
            |{
            |  "code" : {
            |    "display" : "code"
            |  },
            |  "valueQuantity" : {
            |    "value" : 1.0
            |  }
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            |{
            |  "code" : {
            |    "display" : "code"
            |  },
            |  "valueQuantity" : {
            |    "value" : 1.0
            |  }
            |}
            """.trimMargin()
        val usageContext = objectMapper.readValue<UsageContext>(json)

        assertNull(usageContext.id)
        assertEquals(listOf<Extension>(), usageContext.extension)
        assertEquals(Coding(display = FHIRString("code")), usageContext.code)
        assertEquals(DynamicValue(DynamicValueType.QUANTITY, Quantity(value = Decimal(1.0))), usageContext.value)
    }
}
