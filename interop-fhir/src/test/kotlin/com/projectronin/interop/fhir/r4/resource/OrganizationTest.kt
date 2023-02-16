package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.ContactPointSystem
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class OrganizationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val organization = Organization(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("RoninOrganization")),
            ),
            implicitRules = Uri("implicit-rules"),
            language = Code("en-US"),
            text = Narrative(
                status = NarrativeStatus.GENERATED.asCode(),
                div = FHIRString("div")
            ),
            contained = listOf(ContainedResource("""{"resourceType":"Banana","field":"24680"}""")),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            identifier = listOf(Identifier(value = FHIRString("id"))),
            active = FHIRBoolean.TRUE,
            type = listOf(
                CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("http://terminology.hl7.org/CodeSystem/organization-type"),
                            code = Code("dept"),
                            display = FHIRString("Hospital Department")
                        )
                    )
                )
            ),
            name = FHIRString("Ronin Fake Hospital Name"),
            alias = listOf(FHIRString("Fake Other Org Name")),
            telecom = listOf(ContactPoint(value = FHIRString("8675309"), system = ContactPointSystem.PHONE.asCode())),
            address = listOf(Address(country = FHIRString("USA"))),
            partOf = Reference(reference = FHIRString("Organization/super")),
            contact = listOf(
                OrganizationContact(
                    name = HumanName(text = FHIRString("Jane Doe")),
                    telecom = listOf(
                        ContactPoint(
                            system = ContactPointSystem.PHONE.asCode(),
                            value = FHIRString("8675309")
                        )
                    ),
                    address = Address(country = FHIRString("USA")),
                    purpose = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/contactentity-type"),
                                code = Code("ADMIN")
                            )
                        )
                    ),
                )
            ),
            endpoint = listOf(Reference(reference = FHIRString("Endpoint/4321")))
        )

        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(organization)

        val expectedJson = """
          {
            "resourceType" : "Organization",
            "id" : "12345",
            "meta" : {
              "profile" : [ "RoninOrganization" ]
            },
            "implicitRules" : "implicit-rules",
            "language" : "en-US",
            "text" : {
              "status" : "generated",
              "div" : "div"
            },
            "contained" : [ {"resourceType":"Banana","field":"24680"} ],
            "extension" : [ {
              "url" : "http://localhost/extension",
              "valueString" : "Value"
            } ],
            "modifierExtension" : [ {
              "url" : "http://localhost/modifier-extension",
              "valueString" : "Value"
            } ],
            "identifier" : [ {
              "value" : "id"
            } ],
            "active" : true,
            "type" : [ {
              "coding" : [ {
                "system" : "http://terminology.hl7.org/CodeSystem/organization-type",
                "code" : "dept",
                "display" : "Hospital Department"
              } ]
            } ],
            "name" : "Ronin Fake Hospital Name",
            "alias" : [ "Fake Other Org Name" ],
            "telecom" : [ {
              "system" : "phone",
              "value" : "8675309"
            } ],
            "address" : [ {
              "country" : "USA"
            } ],
            "partOf" : {
              "reference" : "Organization/super"
            },
            "contact" : [ {
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
            } ],
            "endpoint" : [ {
              "reference" : "Endpoint/4321"
            } ]
          }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedOrganization = JacksonManager.objectMapper.readValue<Organization>(json)
        assertEquals(organization, deserializedOrganization)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "resourceType" : "Organization",
              "active" : true,
              "name" : "Ronin Fake Hospital Name",
              "telecom" : [ {
                "system" : "phone",
                "value" : "8675309"
              } ],
              "contact": [ {
                "purpose" : {
                  "coding" : [ {
                    "system" : "http://terminology.hl7.org/CodeSystem/contactentity-type",
                    "code" : "ADMIN"
                  } ]
                },
                "address" : {
                  "country" : "USA"
                }
              } ]
            }
        """.trimIndent()
        val organization = JacksonManager.objectMapper.readValue<Organization>(json)

        assertNull(organization.id)
        assertNull(organization.meta)
        assertNull(organization.implicitRules)
        assertNull(organization.language)
        assertNull(organization.text)
        assertEquals(listOf<Resource<Nothing>>(), organization.contained)
        assertEquals(listOf<Extension>(), organization.extension)
        assertEquals(listOf<Extension>(), organization.modifierExtension)
        assertEquals(listOf<Identifier>(), organization.identifier)
        assertEquals(listOf<CodeableConcept>(), organization.type)
        assertEquals(FHIRString("Ronin Fake Hospital Name"), organization.name)
        assertEquals(listOf<String>(), organization.alias)
        assertEquals(
            listOf(
                ContactPoint(
                    system = ContactPointSystem.PHONE.asCode(),
                    value = FHIRString("8675309")
                )
            ),
            organization.telecom
        )
        assertEquals(listOf<Address>(), organization.address)
        assertNull(organization.partOf)
        assertEquals(
            listOf(
                OrganizationContact(
                    purpose = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/contactentity-type"),
                                code = Code("ADMIN")
                            )
                        )
                    ),
                    address = Address(country = FHIRString("USA"))
                )
            ),
            organization.contact
        )
        assertEquals(listOf<Reference>(), organization.endpoint)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val organization = Organization(
            active = FHIRBoolean.TRUE,
            name = FHIRString("Ronin Fake Hospital Name"),
            telecom = listOf(
                ContactPoint(
                    system = ContactPointSystem.PHONE.asCode(),
                    value = FHIRString("8675309")
                )
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(organization)

        val expectedJson = """
            {
              "resourceType" : "Organization",
              "active" : true,
              "name" : "Ronin Fake Hospital Name",
              "telecom" : [ {
                "system" : "phone",
                "value" : "8675309"
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `serialized JSON without name, but with identifier`() {
        val organization = Organization(
            identifier = listOf(
                Identifier(
                    use = Code("official"),
                    system = Uri("http://www.fakefakefakefake.com/"),
                    value = FHIRString("Ronin Fake Hospital Center")
                )
            ),
            active = FHIRBoolean.TRUE,
            telecom = listOf(
                ContactPoint(
                    system = ContactPointSystem.PHONE.asCode(),
                    value = FHIRString("8675309")
                )
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(organization)

        val expectedJson = """
            {
              "resourceType" : "Organization",
              "identifier" : [ {
                "use" : "official",
                "system" : "http://www.fakefakefakefake.com/",
                "value" : "Ronin Fake Hospital Center"
              } ],
              "active" : true,
              "telecom" : [ {
                "system" : "phone",
                "value" : "8675309"
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }
}

class OrganizationContactTest {

    @Test
    fun `can serialize and deserialize JSON`() {
        val organizationContact = OrganizationContact(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
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
            name = HumanName(text = FHIRString("Jane Doe")),
            telecom = listOf(
                ContactPoint(
                    system = ContactPointSystem.PHONE.asCode(),
                    value = FHIRString("8675309")
                )
            ),
            address = Address(country = FHIRString("USA")),
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
                    value = FHIRString("8675309")
                )
            ),
            address = Address(country = FHIRString("USA"))
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
                    value = FHIRString("8675309")
                )
            ),
            organizationContact.telecom
        )
        assertNull(organizationContact.name)
        assertEquals(
            Address(
                country = FHIRString("USA")
            ),
            organizationContact.address
        )
    }
}
