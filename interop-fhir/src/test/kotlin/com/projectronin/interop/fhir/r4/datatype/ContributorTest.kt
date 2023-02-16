package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.ContactPointSystem
import com.projectronin.interop.fhir.r4.valueset.ContributorType
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ContributorTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val contributor = Contributor(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            type = ContributorType.AUTHOR.asCode(),
            name = FHIRString("Josh Smith"),
            contact = listOf(
                ContactDetail(
                    telecom = listOf(
                        ContactPoint(
                            value = FHIRString("josh@projectronin.com"),
                            system = ContactPointSystem.EMAIL.asCode()
                        )
                    )
                )
            )
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(contributor)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "type" : "author",
            |  "name" : "Josh Smith",
            |  "contact" : [ {
            |    "telecom" : [ {
            |      "system" : "email",
            |      "value" : "josh@projectronin.com"
            |    } ]
            |  } ]
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedExtension = objectMapper.readValue<Contributor>(json)
        assertEquals(contributor, deserializedExtension)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val contributor = Contributor(
            type = ContributorType.AUTHOR.asCode(),
            name = FHIRString("Josh Smith")
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(contributor)

        val expectedJson = """
            |{
            |  "type" : "author",
            |  "name" : "Josh Smith"
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "type" : "author",
            |  "name" : "Josh Smith"
            |}""".trimMargin()
        val contributor = objectMapper.readValue<Contributor>(json)

        assertNull(contributor.id)
        assertEquals(listOf<Extension>(), contributor.extension)
        assertEquals(ContributorType.AUTHOR.asCode(), contributor.type)
        assertEquals(FHIRString("Josh Smith"), contributor.name)
        assertEquals(listOf<ContactDetail>(), contributor.contact)
    }
}
