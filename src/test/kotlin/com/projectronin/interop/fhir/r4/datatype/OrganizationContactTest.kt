package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.ContactPointSystem
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class OrganizationContactTest {

    @Test
    fun `can serialize and deserialize JSON`() {
        val organizationContact = OrganizationContact(
            id = "12345",
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
            purpose = CodeableConcept(
                coding = listOf(
                    Coding(
                        system = Uri("http://terminology.hl7.org/CodeSystem/contactentity-type"),
                        code = Code("ADMIN")
                    )
                )
            ),
            name = HumanName(text = "Jane Doe"),
            telecom = listOf(
                ContactPoint(
                    system = ContactPointSystem.PHONE.asCode(),
                    value = "8675309"
                )
            ),
            address = Address(country = "USA"),
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(organizationContact)

        val expectedJson = """
            {
              "id" : "12345",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "purpose" : {
                "coding" : [ {
                  "system" : "http://terminology.hl7.org/CodeSystem/contactentity-type",
                  "code" : "ADMIN"
                } ]
              },
              "name" : {
                "text" : "Jane Doe"
              },
              "telecom" : [ {
                "system" : "phone",
                "value" : "8675309"
              } ],
              "address" : {
                "country" : "USA"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedOrgContact = JacksonManager.objectMapper.readValue<OrganizationContact>(json)
        assertEquals(organizationContact, deserializedOrgContact)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val organizationContact = OrganizationContact(
            telecom = listOf(
                ContactPoint(
                    system = ContactPointSystem.PHONE.asCode(),
                    value = "8675309"
                )
            ),
            address = Address(country = "USA")
        )

        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(organizationContact)

        val expectedJson = """
            {
              "telecom" : [ {
                "system" : "phone",
                "value" : "8675309"
              } ],
              "address" : {
                "country" : "USA"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedOrgContact = JacksonManager.objectMapper.readValue<OrganizationContact>(json)
        assertEquals(organizationContact, deserializedOrgContact)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "telecom" : [ {
                "system" : "phone",
                "value" : "8675309"
              } ],
              "address" : {
                "country" : "USA"
              }
            }
        """.trimIndent()
        val organizationContact = JacksonManager.objectMapper.readValue<OrganizationContact>(json)
        assertNull(organizationContact.id)
        assertEquals(listOf<Extension>(), organizationContact.extension)
        assertEquals(listOf<Extension>(), organizationContact.modifierExtension)
        assertNull(organizationContact.purpose)
        assertEquals(
            listOf(
                ContactPoint(
                    system = ContactPointSystem.PHONE.asCode(),
                    value = "8675309"
                )
            ),
            organizationContact.telecom
        )
        assertNull(organizationContact.name)
        assertEquals(
            Address(
                country = "USA"
            ),
            organizationContact.address
        )
    }
}
