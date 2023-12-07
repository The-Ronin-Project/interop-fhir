package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.NameUse
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class HumanNameTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val humanName =
            HumanName(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                use = NameUse.OFFICIAL.asCode(),
                text = FHIRString("Jane Doe"),
                family = FHIRString("Doe"),
                given = listOf(FHIRString("Jane")),
                prefix = listOf(FHIRString("Dr")),
                suffix = listOf(FHIRString("M.D.")),
                period = Period(start = DateTime("1994-02-29")),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(humanName)

        val expectedJson =
            """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "use" : "official",
            |  "text" : "Jane Doe",
            |  "family" : "Doe",
            |  "given" : [ "Jane" ],
            |  "prefix" : [ "Dr" ],
            |  "suffix" : [ "M.D." ],
            |  "period" : {
            |    "start" : "1994-02-29"
            |  }
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedHumanName = objectMapper.readValue<HumanName>(json)
        assertEquals(humanName, deserializedHumanName)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val humanName =
            HumanName(
                text = FHIRString("Name"),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(humanName)

        val expectedJson =
            """
            |{
            |  "text" : "Name"
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            |{
            |  "use" : "nickname"
            |}
            """.trimMargin()
        val humanName = objectMapper.readValue<HumanName>(json)

        assertNull(humanName.id)
        assertEquals(listOf<Extension>(), humanName.extension)
        assertEquals(NameUse.NICKNAME.asCode(), humanName.use)
        assertNull(humanName.text)
        assertNull(humanName.family)
        assertEquals(listOf<String>(), humanName.given)
        assertEquals(listOf<String>(), humanName.prefix)
        assertEquals(listOf<String>(), humanName.suffix)
        assertNull(humanName.period)
    }
}
