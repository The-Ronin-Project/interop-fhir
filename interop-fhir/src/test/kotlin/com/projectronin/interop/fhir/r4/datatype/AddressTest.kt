package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.AddressType
import com.projectronin.interop.fhir.r4.valueset.AddressUse
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class AddressTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val address = Address(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            use = AddressUse.HOME.asCode(),
            type = AddressType.POSTAL.asCode(),
            text = FHIRString("925 Powder Springs St, Smyrna, GA 30080"),
            line = listOf(FHIRString("925 Powder Springs St")),
            city = FHIRString("Smyrna"),
            district = FHIRString("Cobb"),
            state = FHIRString("GA"),
            postalCode = FHIRString("30080"),
            country = FHIRString("USA"),
            period = Period(start = DateTime("1998-08"), end = DateTime("2002-05"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(address)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "use" : "home",
            |  "type" : "postal",
            |  "text" : "925 Powder Springs St, Smyrna, GA 30080",
            |  "line" : [ "925 Powder Springs St" ],
            |  "city" : "Smyrna",
            |  "district" : "Cobb",
            |  "state" : "GA",
            |  "postalCode" : "30080",
            |  "country" : "USA",
            |  "period" : {
            |    "start" : "1998-08",
            |    "end" : "2002-05"
            |  }
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedAddress = objectMapper.readValue<Address>(json)
        assertEquals(address, deserializedAddress)
    }

    @Test
    fun `can serialize and deserialize JSON with string extensions`() {
        val extension = Extension(
            url = Uri("http://localhost/string-extension"),
            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
        )

        val address = Address(
            id = FHIRString("12345", null, listOf(extension)),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            use = AddressUse.HOME.asCode(),
            type = AddressType.POSTAL.asCode(),
            text = FHIRString("925 Powder Springs St, Smyrna, GA 30080", null, listOf(extension)),
            line = listOf(FHIRString("925 Powder Springs St", null, listOf(extension))),
            city = FHIRString("Smyrna", null, listOf(extension)),
            district = FHIRString("Cobb", null, listOf(extension)),
            state = FHIRString("GA", null, listOf(extension)),
            postalCode = FHIRString("30080", null, listOf(extension)),
            country = FHIRString("USA", null, listOf(extension)),
            period = Period(start = DateTime("1998-08"), end = DateTime("2002-05"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(address)

        val stringExtensionJSON = """{
            |    "extension" : [ {
            |      "url" : "http://localhost/string-extension",
            |      "valueString" : "Value"
            |    } ]
            |  }
        """.trimMargin()

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "_id" : $stringExtensionJSON,
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "use" : "home",
            |  "type" : "postal",
            |  "text" : "925 Powder Springs St, Smyrna, GA 30080",
            |  "_text" : $stringExtensionJSON,
            |  "line" : [ "925 Powder Springs St" ],
            |  "_line" : [ $stringExtensionJSON ],
            |  "city" : "Smyrna",
            |  "_city" : $stringExtensionJSON,
            |  "district" : "Cobb",
            |  "_district" : $stringExtensionJSON,
            |  "state" : "GA",
            |  "_state" : $stringExtensionJSON,
            |  "postalCode" : "30080",
            |  "_postalCode" : $stringExtensionJSON,
            |  "country" : "USA",
            |  "_country" : $stringExtensionJSON,
            |  "period" : {
            |    "start" : "1998-08",
            |    "end" : "2002-05"
            |  }
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedAddress = objectMapper.readValue<Address>(json)
        assertEquals(address, deserializedAddress)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val address = Address(type = AddressType.POSTAL.asCode())

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(address)

        val expectedJson = """
            |{
            |  "type" : "postal"
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "use" : "home"
            |}
        """.trimMargin()
        val address = objectMapper.readValue<Address>(json)

        assertNull(address.id)
        assertEquals(listOf<Extension>(), address.extension)
        assertEquals(AddressUse.HOME.asCode(), address.use)
        assertNull(address.type)
        assertNull(address.text)
        assertEquals(listOf<String>(), address.line)
        assertNull(address.city)
        assertNull(address.district)
        assertNull(address.state)
        assertNull(address.postalCode)
        assertNull(address.country)
        assertNull(address.period)
    }

    @Test
    fun `can deserialize from JSON with an empty line`() {
        // Previously this test expected us to filter out the empty line; however, due to the way that primitive extensions
        // work within a List, this could cause problems. So we will now return this empty line encapsulated by the FHIRString.
        val json = """
            |{
            |  "line" : [ "925 Powder Springs St", "" ]
            |}
        """.trimMargin()

        val address = objectMapper.readValue<Address>(json)

        assertEquals(listOf(FHIRString("925 Powder Springs St"), FHIRString("")), address.line)
    }

    @Test
    fun `can deserialize from JSON with a blank line`() {
        // Previously this test expected us to filter out the blank line; however, due to the way that primitive extensions
        // work within a List, this could cause problems. So we will now return this blank line encapsulated by the FHIRString.
        val json = """
            |{
            |  "line" : [ "925 Powder Springs St", "   " ]
            |}
        """.trimMargin()

        val address = objectMapper.readValue<Address>(json)

        assertEquals(listOf(FHIRString("925 Powder Springs St"), FHIRString("   ")), address.line)
    }
}
