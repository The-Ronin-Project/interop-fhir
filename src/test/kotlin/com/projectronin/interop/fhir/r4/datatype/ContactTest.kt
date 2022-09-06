package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.AdministrativeGender
import com.projectronin.interop.fhir.r4.valueset.ContactPointSystem
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ContactTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val contact = Contact(
            id = "67890",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            relationship = listOf(
                CodeableConcept(text = "N")
            ),
            name = HumanName(text = "Jane Doe"),
            telecom = listOf(ContactPoint(value = "name@site.com", system = ContactPointSystem.EMAIL.asCode())),
            address = Address(text = "123 Sesame St"),
            gender = AdministrativeGender.MALE.asCode(),
            organization = Reference(reference = "Patient/123"),
            period = Period(start = DateTime("1998-08"))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(contact)

        val expectedJson = """
            {
              "id" : "67890",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "relationship" : [ {
                "text" : "N"
              } ],
              "name" : {
                "text" : "Jane Doe"
              },
              "telecom" : [ {
                "system" : "email",
                "value" : "name@site.com"
              } ],
              "address" : {
                "text" : "123 Sesame St"
              },
              "gender" : "male",
              "organization" : {
                "reference" : "Patient/123"
              },
              "period" : {
                "start" : "1998-08"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedContact = JacksonManager.objectMapper.readValue<Contact>(json)
        assertEquals(contact, deserializedContact)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val contact = Contact(
            name = HumanName(text = "Jane Doe"),
            telecom = listOf(ContactPoint(value = "name@site.com", system = ContactPointSystem.EMAIL.asCode())),
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(contact)

        val expectedJson = """
            {
              "name" : {
                "text" : "Jane Doe"
              },
              "telecom" : [ {
                "system" : "email",
                "value" : "name@site.com"
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedContact = JacksonManager.objectMapper.readValue<Contact>(json)
        assertEquals(contact, deserializedContact)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "name" : {
                "text" : "Jane Doe"
              },
              "telecom" : [ {
                "system" : "email",
                "value" : "name@site.com"
              } ]
            }
        """.trimIndent()
        val contact = JacksonManager.objectMapper.readValue<Contact>(json)

        assertNull(contact.id)
        assertEquals(listOf<Extension>(), contact.extension)
        assertEquals(listOf<Extension>(), contact.modifierExtension)
        assertEquals(listOf<CodeableConcept>(), contact.relationship)
        assertEquals("Jane Doe", contact.name?.text)
        assertNull(contact.address)
        assertNull(contact.gender)
        assertNull(contact.organization)
        assertNull(contact.period)
    }
}
