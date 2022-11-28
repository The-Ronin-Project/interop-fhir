package com.projectronin.interop.fhir.r4.datatype.medication

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class DispenseRequestTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val dispenseRequest = DispenseRequest(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            initialFill = InitialFill(
                id = FHIRString("67890"),
                extension = listOf(
                    Extension(
                        url = Uri("http://localhost/extension"),
                        value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                    )
                ),
                quantity = SimpleQuantity(value = Decimal(23.0)),
                duration = Duration(value = Decimal(5.5))
            ),
            dispenseInterval = Duration(value = Decimal(1.0)),
            validityPeriod = Period(start = DateTime("2022-11-03")),
            numberOfRepeatsAllowed = UnsignedInt(3),
            quantity = SimpleQuantity(value = Decimal(36.0)),
            expectedSupplyDuration = Duration(value = Decimal(14.0)),
            performer = Reference(reference = FHIRString("Practitioner/13579"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dispenseRequest)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "initialFill" : {
            |    "id" : "67890",
            |    "extension" : [ {
            |      "url" : "http://localhost/extension",
            |      "valueString" : "Value"
            |    } ],
            |    "quantity" : {
            |      "value" : 23.0
            |    },
            |    "duration" : {
            |      "value" : 5.5
            |    }
            |  },
            |  "dispenseInterval" : {
            |    "value" : 1.0
            |  },
            |  "validityPeriod" : {
            |    "start" : "2022-11-03"
            |  },
            |  "numberOfRepeatsAllowed" : 3,
            |  "quantity" : {
            |    "value" : 36.0
            |  },
            |  "expectedSupplyDuration" : {
            |    "value" : 14.0
            |  },
            |  "performer" : {
            |    "reference" : "Practitioner/13579"
            |  }
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedDispenseRequest = objectMapper.readValue<DispenseRequest>(json)
        assertEquals(dispenseRequest, deserializedDispenseRequest)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val dispenseRequest = DispenseRequest(
            quantity = SimpleQuantity(value = Decimal(36.0))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dispenseRequest)

        val expectedJson = """
            |{
            |  "quantity" : {
            |    "value" : 36.0
            |  }
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "numberOfRepeatsAllowed" : 3
            |}""".trimMargin()
        val dispenseRequest = objectMapper.readValue<DispenseRequest>(json)

        assertNull(dispenseRequest.id)
        assertEquals(listOf<Extension>(), dispenseRequest.extension)
        assertNull(dispenseRequest.initialFill)
        assertNull(dispenseRequest.dispenseInterval)
        assertNull(dispenseRequest.validityPeriod)
        assertEquals(UnsignedInt(3), dispenseRequest.numberOfRepeatsAllowed)
        assertNull(dispenseRequest.quantity)
        assertNull(dispenseRequest.expectedSupplyDuration)
        assertNull(dispenseRequest.performer)
    }
}
