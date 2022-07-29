package com.projectronin.interop.fhir.r4.ronin.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.CodeableConcepts
import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Communication
import com.projectronin.interop.fhir.r4.datatype.Contact
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.PatientLink
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Base64Binary
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.valueset.AdministrativeGender
import com.projectronin.interop.fhir.r4.valueset.ContactPointSystem
import com.projectronin.interop.fhir.r4.valueset.ContactPointUse
import com.projectronin.interop.fhir.r4.valueset.LinkType
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OncologyPatientTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val deceased = DynamicValue(type = DynamicValueType.BOOLEAN, value = false)
        val multipleBirth = DynamicValue(type = DynamicValueType.INTEGER, value = 2)
        val oncologyPatient = OncologyPatient(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("http://projectronin.com/fhir/us/ronin/StructureDefinition/oncology-practitioner")),
            ),
            implicitRules = Uri("implicit-rules"),
            language = Code("en-US"),
            text = Narrative(
                status = NarrativeStatus.GENERATED,
                div = "div"
            ),
            contained = listOf(ContainedResource("""{"resourceType":"Banana","field":"24680"}""")),
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
            identifier = listOf(
                Identifier(
                    system = CodeSystem.RONIN_TENANT.uri,
                    type = CodeableConcepts.RONIN_TENANT,
                    value = "tenantId"
                ),
                Identifier(
                    system = CodeSystem.MRN.uri,
                    type = CodeableConcepts.MRN,
                    value = "MRN"
                ),
                Identifier(
                    system = CodeSystem.FHIR_STU3_ID.uri,
                    type = CodeableConcepts.FHIR_STU3_ID,
                    value = "fhirId"
                )
            ),
            active = true,
            name = listOf(HumanName(family = "Doe")),
            telecom = listOf(
                ContactPoint(
                    system = ContactPointSystem.PHONE,
                    value = "8675309",
                    use = ContactPointUse.MOBILE
                )
            ),
            gender = AdministrativeGender.FEMALE,
            birthDate = Date("1975-07-05"),
            deceased = deceased,
            address = listOf(Address(country = "USA")),
            maritalStatus = CodeableConcept(text = "M"),
            multipleBirth = multipleBirth,
            photo = listOf(Attachment(contentType = Code("text"), data = Base64Binary("abcd"))),
            contact = listOf(Contact(name = HumanName(text = "Jane Doe"))),
            communication = listOf(Communication(language = CodeableConcept(text = "English"))),
            generalPractitioner = listOf(Reference(display = "GP")),
            managingOrganization = Reference(display = "organization"),
            link = listOf(PatientLink(other = Reference(display = "other patient"), type = LinkType.REPLACES))
        )
        oncologyPatient.validate().alertIfErrors()
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(oncologyPatient)

        val expectedJson = """
            {
              "resourceType" : "Patient",
              "id" : "12345",
              "meta" : {
                "profile" : [ "http://projectronin.com/fhir/us/ronin/StructureDefinition/oncology-practitioner" ]
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
                "type" : {
                  "coding" : [ {
                    "system" : "http://projectronin.com/id/tenantId",
                    "code" : "TID",
                    "display" : "Ronin-specified Tenant Identifier"
                  } ],
                  "text" : "Tenant ID"
                },
                "system" : "http://projectronin.com/id/tenantId",
                "value" : "tenantId"
              }, {
                "type" : {
                  "coding" : [ {
                    "system" : "http://projectronin.com/id/mrn",
                    "code" : "MR",
                    "display" : "Medical Record Number"
                  } ],
                  "text" : "MRN"
                },
                "system" : "http://projectronin.com/id/mrn",
                "value" : "MRN"
              }, {
                "type" : {
                  "coding" : [ {
                    "system" : "http://projectronin.com/id/fhir",
                    "code" : "STU3",
                    "display" : "FHIR STU3 ID"
                  } ],
                  "text" : "FHIR STU3"
                },
                "system" : "http://projectronin.com/id/fhir",
                "value" : "fhirId"
              } ],
              "active" : true,
              "name" : [ {
                "family" : "Doe"
              } ],
              "telecom" : [ {
                "system" : "phone",
                "value" : "8675309",
                "use" : "mobile"
              } ],
              "gender" : "female",
              "birthDate" : "1975-07-05",
              "deceasedBoolean" : false,
              "address" : [ {
                "country" : "USA"
              } ],
              "maritalStatus" : {
                "text" : "M"
              },
              "multipleBirthInteger" : 2,
              "photo" : [ {
                "contentType" : "text",
                "data" : "abcd"
              } ],
              "contact" : [ {
                "name" : {
                  "text" : "Jane Doe"
                }
              } ],
              "communication" : [ {
                "language" : {
                  "text" : "English"
                }
              } ],
              "generalPractitioner" : [ {
                "display" : "GP"
              } ],
              "managingOrganization" : {
                "display" : "organization"
              },
              "link" : [ {
                "other" : {
                  "display" : "other patient"
                },
                "type" : "replaces"
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedOncologyPatient = JacksonManager.objectMapper.readValue<OncologyPatient>(json)
        assertEquals(oncologyPatient, deserializedOncologyPatient)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val oncologyPatient = OncologyPatient(
            identifier = listOf(
                Identifier(
                    system = CodeSystem.RONIN_TENANT.uri,
                    type = CodeableConcepts.RONIN_TENANT,
                    value = "tenantId"
                ),
                Identifier(
                    system = CodeSystem.MRN.uri,
                    type = CodeableConcepts.MRN,
                    value = "MRN"
                ),
                Identifier(
                    system = CodeSystem.FHIR_STU3_ID.uri,
                    type = CodeableConcepts.FHIR_STU3_ID,
                    value = "fhirId"
                )
            ),
            name = listOf(HumanName(family = "Doe")),
            telecom = listOf(
                ContactPoint(
                    system = ContactPointSystem.PHONE,
                    value = "8675309",
                    use = ContactPointUse.MOBILE
                )
            ),
            gender = AdministrativeGender.FEMALE,
            birthDate = Date("1975-07-05"),
            address = listOf(Address(country = "USA")),
            maritalStatus = CodeableConcept(text = "M")
        )
        oncologyPatient.validate().alertIfErrors()
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(oncologyPatient)

        val expectedJson = """
            {
              "resourceType" : "Patient",
              "identifier" : [ {
                "type" : {
                  "coding" : [ {
                    "system" : "http://projectronin.com/id/tenantId",
                    "code" : "TID",
                    "display" : "Ronin-specified Tenant Identifier"
                  } ],
                  "text" : "Tenant ID"
                },
                "system" : "http://projectronin.com/id/tenantId",
                "value" : "tenantId"
              }, {
                "type" : {
                  "coding" : [ {
                    "system" : "http://projectronin.com/id/mrn",
                    "code" : "MR",
                    "display" : "Medical Record Number"
                  } ],
                  "text" : "MRN"
                },
                "system" : "http://projectronin.com/id/mrn",
                "value" : "MRN"
              }, {
                "type" : {
                  "coding" : [ {
                    "system" : "http://projectronin.com/id/fhir",
                    "code" : "STU3",
                    "display" : "FHIR STU3 ID"
                  } ],
                  "text" : "FHIR STU3"
                },
                "system" : "http://projectronin.com/id/fhir",
                "value" : "fhirId"
              } ],
              "name" : [ {
                "family" : "Doe"
              } ],
              "telecom" : [ {
                "system" : "phone",
                "value" : "8675309",
                "use" : "mobile"
              } ],
              "gender" : "female",
              "birthDate" : "1975-07-05",
              "address" : [ {
                "country" : "USA"
              } ],
              "maritalStatus" : {
                "text" : "M"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "resourceType" : "Patient",
              "identifier" : [ {
                "type" : {
                  "coding" : [ {
                    "system" : "http://projectronin.com/id/tenantId",
                    "code" : "TID",
                    "display" : "Ronin-specified Tenant Identifier"
                  } ],
                  "text" : "Tenant ID"
                },
                "system" : "http://projectronin.com/id/tenantId",
                "value" : "tenantId"
              }, {
                "type" : {
                  "coding" : [ {
                    "system" : "http://projectronin.com/id/mrn",
                    "code" : "MR",
                    "display" : "Medical Record Number"
                  } ],
                  "text" : "MRN"
                },
                "system" : "http://projectronin.com/id/mrn",
                "value" : "MRN"
              }, {
                "type" : {
                  "coding" : [ {
                    "system" : "http://projectronin.com/id/fhir",
                    "code" : "STU3",
                    "display" : "FHIR STU3 ID"
                  } ],
                  "text" : "FHIR STU3"
                },
                "system" : "http://projectronin.com/id/fhir",
                "value" : "fhirId"
              } ],
              "name" : [ {
                "family" : "Doe"
              } ],
              "telecom" : [ {
                "system" : "phone",
                "value" : "8675309",
                "use" : "mobile"
              } ],
              "gender" : "female",
              "birthDate" : "1975-07-05",
              "address" : [ {
                "country" : "USA"
              } ],
              "maritalStatus" : {
                "text" : "M"
              }
            }
        """.trimIndent()
        val oncologyPatient = JacksonManager.objectMapper.readValue<OncologyPatient>(json)

        assertNull(oncologyPatient.id)
        assertNull(oncologyPatient.meta)
        assertNull(oncologyPatient.implicitRules)
        assertNull(oncologyPatient.language)
        assertNull(oncologyPatient.text)
        assertEquals(listOf<RoninResource>(), oncologyPatient.contained)
        assertEquals(listOf<Extension>(), oncologyPatient.extension)
        assertEquals(listOf<Extension>(), oncologyPatient.modifierExtension)
        assertNull(oncologyPatient.active)
        assertNull(oncologyPatient.deceased)
        assertNull(oncologyPatient.multipleBirth)
        assertEquals(listOf<Attachment>(), oncologyPatient.photo)
        assertEquals(listOf<Contact>(), oncologyPatient.contact)
        assertEquals(listOf<Communication>(), oncologyPatient.communication)
        assertEquals(listOf<Reference>(), oncologyPatient.generalPractitioner)
        assertNull(oncologyPatient.managingOrganization)
        assertEquals(listOf<PatientLink>(), oncologyPatient.link)
    }

    @Test
    fun `fails if no tenant identifier provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyPatient(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.MRN.uri,
                            type = CodeableConcepts.MRN,
                            value = "MRN"
                        ),
                        Identifier(
                            system = CodeSystem.FHIR_STU3_ID.uri,
                            type = CodeableConcepts.FHIR_STU3_ID,
                            value = "fhirId"
                        )
                    ),
                    name = listOf(HumanName(family = "Doe")),
                    telecom = listOf(
                        ContactPoint(
                            system = ContactPointSystem.PHONE,
                            value = "8675309",
                            use = ContactPointUse.MOBILE
                        )
                    ),
                    gender = AdministrativeGender.FEMALE,
                    birthDate = Date("1975-07-05"),
                    address = listOf(Address(country = "USA")),
                    maritalStatus = CodeableConcept(text = "M")
                ).validate().alertIfErrors()
            }
        assertEquals("Tenant identifier is required", exception.message)
    }

    @Test
    fun `fails if tenant does not have tenant codeable concept`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyPatient(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.MRN,
                            value = "tenantId"
                        ),
                        Identifier(
                            system = CodeSystem.MRN.uri,
                            type = CodeableConcepts.MRN,
                            value = "MRN"
                        ),
                        Identifier(
                            system = CodeSystem.FHIR_STU3_ID.uri,
                            type = CodeableConcepts.FHIR_STU3_ID,
                            value = "fhirId"
                        )
                    ),
                    name = listOf(HumanName(family = "Doe")),
                    telecom = listOf(
                        ContactPoint(
                            system = ContactPointSystem.PHONE,
                            value = "8675309",
                            use = ContactPointUse.MOBILE
                        )
                    ),
                    gender = AdministrativeGender.FEMALE,
                    birthDate = Date("1975-07-05"),
                    address = listOf(Address(country = "USA")),
                    maritalStatus = CodeableConcept(text = "M")
                ).validate().alertIfErrors()
            }
        assertEquals("Tenant identifier provided without proper CodeableConcept defined", exception.message)
    }

    @Test
    fun `fails if tenant does not have value`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyPatient(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.RONIN_TENANT
                        ),
                        Identifier(
                            system = CodeSystem.MRN.uri,
                            type = CodeableConcepts.MRN,
                            value = "MRN"
                        ),
                        Identifier(
                            system = CodeSystem.FHIR_STU3_ID.uri,
                            type = CodeableConcepts.FHIR_STU3_ID,
                            value = "fhirId"
                        )
                    ),
                    name = listOf(HumanName(family = "Doe")),
                    telecom = listOf(
                        ContactPoint(
                            system = ContactPointSystem.PHONE,
                            value = "8675309",
                            use = ContactPointUse.MOBILE
                        )
                    ),
                    gender = AdministrativeGender.FEMALE,
                    birthDate = Date("1975-07-05"),
                    address = listOf(Address(country = "USA")),
                    maritalStatus = CodeableConcept(text = "M")
                ).validate().alertIfErrors()
            }
        assertEquals("tenant value is required", exception.message)
    }

    @Test
    fun `fails if no mrn identifier provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyPatient(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "tenantId"
                        ),
                        Identifier(
                            system = CodeSystem.FHIR_STU3_ID.uri,
                            type = CodeableConcepts.FHIR_STU3_ID,
                            value = "fhirId"
                        )
                    ),
                    name = listOf(HumanName(family = "Doe")),
                    telecom = listOf(
                        ContactPoint(
                            system = ContactPointSystem.PHONE,
                            value = "8675309",
                            use = ContactPointUse.MOBILE
                        )
                    ),
                    gender = AdministrativeGender.FEMALE,
                    birthDate = Date("1975-07-05"),
                    address = listOf(Address(country = "USA")),
                    maritalStatus = CodeableConcept(text = "M")
                ).validate().alertIfErrors()
            }
        assertEquals("mrn identifier is required", exception.message)
    }

    @Test
    fun `fails if mrn has a bad codeable concept`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyPatient(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "tenantId"
                        ),
                        Identifier(
                            system = CodeSystem.MRN.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "MRN"
                        ),
                        Identifier(
                            system = CodeSystem.FHIR_STU3_ID.uri,
                            type = CodeableConcepts.FHIR_STU3_ID,
                            value = "fhirId"
                        )
                    ),
                    name = listOf(HumanName(family = "Doe")),
                    telecom = listOf(
                        ContactPoint(
                            system = ContactPointSystem.PHONE,
                            value = "8675309",
                            use = ContactPointUse.MOBILE
                        )
                    ),
                    gender = AdministrativeGender.FEMALE,
                    birthDate = Date("1975-07-05"),
                    address = listOf(Address(country = "USA")),
                    maritalStatus = CodeableConcept(text = "M")
                ).validate().alertIfErrors()
            }
        assertEquals("mrn identifier type defined without proper CodeableConcept", exception.message)
    }

    @Test
    fun `fails if mrn does not have value`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyPatient(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "tenantId"
                        ),
                        Identifier(
                            system = CodeSystem.MRN.uri,
                            type = CodeableConcepts.MRN
                        ),
                        Identifier(
                            system = CodeSystem.FHIR_STU3_ID.uri,
                            type = CodeableConcepts.FHIR_STU3_ID,
                            value = "fhirId"
                        )
                    ),
                    name = listOf(HumanName(family = "Doe")),
                    telecom = listOf(
                        ContactPoint(
                            system = ContactPointSystem.PHONE,
                            value = "8675309",
                            use = ContactPointUse.MOBILE
                        )
                    ),
                    gender = AdministrativeGender.FEMALE,
                    birthDate = Date("1975-07-05"),
                    address = listOf(Address(country = "USA")),
                    maritalStatus = CodeableConcept(text = "M")
                ).validate().alertIfErrors()
            }
        assertEquals("mrn value is required", exception.message)
    }

    @Test
    fun `fails if no fhir_stu3_id identifier provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyPatient(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "tenantId"
                        ),
                        Identifier(
                            system = CodeSystem.MRN.uri,
                            type = CodeableConcepts.MRN,
                            value = "MRN"
                        )
                    ),
                    name = listOf(HumanName(family = "Doe")),
                    telecom = listOf(
                        ContactPoint(
                            system = ContactPointSystem.PHONE,
                            value = "8675309",
                            use = ContactPointUse.MOBILE
                        )
                    ),
                    gender = AdministrativeGender.FEMALE,
                    birthDate = Date("1975-07-05"),
                    address = listOf(Address(country = "USA")),
                    maritalStatus = CodeableConcept(text = "M")
                ).validate().alertIfErrors()
            }
        assertEquals("fhir_stu3_id identifier is required", exception.message)
    }

    @Test
    fun `fails if fhir_stu3_id has a bad codeable concept`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyPatient(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "tenantId"
                        ),
                        Identifier(
                            system = CodeSystem.MRN.uri,
                            type = CodeableConcepts.MRN,
                            value = "MRN"
                        ),
                        Identifier(
                            system = CodeSystem.FHIR_STU3_ID.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "fhirId"
                        )
                    ),
                    name = listOf(HumanName(family = "Doe")),
                    telecom = listOf(
                        ContactPoint(
                            system = ContactPointSystem.PHONE,
                            value = "8675309",
                            use = ContactPointUse.MOBILE
                        )
                    ),
                    gender = AdministrativeGender.FEMALE,
                    birthDate = Date("1975-07-05"),
                    address = listOf(Address(country = "USA")),
                    maritalStatus = CodeableConcept(text = "M")
                ).validate().alertIfErrors()
            }
        assertEquals("fhir_stu3_id identifier type defined without proper CodeableConcept", exception.message)
    }

    @Test
    fun `fails if fhir_stu3_id does not have value`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyPatient(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "tenantId"
                        ),
                        Identifier(
                            system = CodeSystem.MRN.uri,
                            type = CodeableConcepts.MRN,
                            value = "MRN"
                        ),
                        Identifier(
                            system = CodeSystem.FHIR_STU3_ID.uri,
                            type = CodeableConcepts.FHIR_STU3_ID
                        )
                    ),
                    name = listOf(HumanName(family = "Doe")),
                    telecom = listOf(
                        ContactPoint(
                            system = ContactPointSystem.PHONE,
                            value = "8675309",
                            use = ContactPointUse.MOBILE
                        )
                    ),
                    gender = AdministrativeGender.FEMALE,
                    birthDate = Date("1975-07-05"),
                    address = listOf(Address(country = "USA")),
                    maritalStatus = CodeableConcept(text = "M")
                ).validate().alertIfErrors()
            }
        assertEquals("fhir_stu3_id value is required", exception.message)
    }

    @Test
    fun `fails if no name provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyPatient(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "tenantId"
                        ),
                        Identifier(
                            system = CodeSystem.MRN.uri,
                            type = CodeableConcepts.MRN,
                            value = "MRN"
                        ),
                        Identifier(
                            system = CodeSystem.FHIR_STU3_ID.uri,
                            type = CodeableConcepts.FHIR_STU3_ID,
                            value = "fhirId"
                        )
                    ),
                    name = listOf(),
                    telecom = listOf(
                        ContactPoint(
                            system = ContactPointSystem.PHONE,
                            value = "8675309",
                            use = ContactPointUse.MOBILE
                        )
                    ),
                    gender = AdministrativeGender.FEMALE,
                    birthDate = Date("1975-07-05"),
                    address = listOf(Address(country = "USA")),
                    maritalStatus = CodeableConcept(text = "M")
                ).validate().alertIfErrors()
            }
        assertEquals("At least one name must be provided", exception.message)
    }

    @Test
    fun `can deserialize from Aidbox-style JSON`() {
        val json = """
            {
              "resourceType" : "Patient",
              "identifier" : [ {
                "type" : {
                  "coding" : [ {
                    "system" : "http://projectronin.com/id/tenantId",
                    "code" : "TID",
                    "display" : "Ronin-specified Tenant Identifier"
                  } ],
                  "text" : "Tenant ID"
                },
                "system" : "http://projectronin.com/id/tenantId",
                "value" : "tenantId"
              }, {
                "type" : {
                  "coding" : [ {
                    "system" : "http://projectronin.com/id/mrn",
                    "code" : "MR",
                    "display" : "Medical Record Number"
                  } ],
                  "text" : "MRN"
                },
                "system" : "http://projectronin.com/id/mrn",
                "value" : "MRN"
              }, {
                "type" : {
                  "coding" : [ {
                    "system" : "http://projectronin.com/id/fhir",
                    "code" : "STU3",
                    "display" : "FHIR STU3 ID"
                  } ],
                  "text" : "FHIR STU3"
                },
                "system" : "http://projectronin.com/id/fhir",
                "value" : "fhirId"
              } ],
              "name" : [ {
                "family" : "Doe"
              } ],
              "telecom" : [ {
                "system" : "phone",
                "value" : "8675309",
                "use" : "mobile"
              } ],
              "gender" : "female",
              "birthDate" : "1975-07-05",
              "address" : [ {
                "country" : "USA"
              } ],
              "maritalStatus" : {
                "text" : "M"
              },
              "deceased" : {
                "boolean" : true
              },
              "multipleBirth" : {
                "integer" : 2
              }
            }
        """.trimIndent()
        val oncologyPatient = JacksonManager.objectMapper.readValue<OncologyPatient>(json)

        val expectedPatient = OncologyPatient(
            identifier = listOf(
                Identifier(
                    type = CodeableConcepts.RONIN_TENANT,
                    system = Uri("http://projectronin.com/id/tenantId"),
                    value = "tenantId"
                ),
                Identifier(type = CodeableConcepts.MRN, system = Uri("http://projectronin.com/id/mrn"), value = "MRN"),
                Identifier(
                    type = CodeableConcepts.FHIR_STU3_ID,
                    system = Uri("http://projectronin.com/id/fhir"),
                    value = "fhirId"
                )
            ),
            name = listOf(HumanName(family = "Doe")),
            telecom = listOf(
                ContactPoint(
                    system = ContactPointSystem.PHONE,
                    value = "8675309",
                    use = ContactPointUse.MOBILE
                )
            ),
            gender = AdministrativeGender.FEMALE,
            birthDate = Date("1975-07-05"),
            address = listOf(Address(country = "USA")),
            maritalStatus = CodeableConcept(text = "M"),
            deceased = DynamicValue(DynamicValueType.BOOLEAN, true),
            multipleBirth = DynamicValue(DynamicValueType.INTEGER, 2)
        )
        assertEquals(expectedPatient, oncologyPatient)
    }

    @Test
    fun `base validate() is inherited - Patient multipleBirth as an example`() {
        val exception = assertThrows<IllegalArgumentException> {
            OncologyPatient(
                identifier = listOf(
                    Identifier(
                        system = CodeSystem.RONIN_TENANT.uri,
                        type = CodeableConcepts.RONIN_TENANT,
                        value = "tenantId"
                    ),
                    Identifier(
                        system = CodeSystem.MRN.uri,
                        type = CodeableConcepts.MRN,
                        value = "MRN"
                    ),
                    Identifier(
                        system = CodeSystem.FHIR_STU3_ID.uri,
                        type = CodeableConcepts.FHIR_STU3_ID,
                        value = "fhirId"
                    )
                ),
                name = listOf(HumanName(family = "Doe")),
                telecom = listOf(
                    ContactPoint(
                        system = ContactPointSystem.PHONE,
                        value = "8675309",
                        use = ContactPointUse.MOBILE
                    )
                ),
                gender = AdministrativeGender.FEMALE,
                birthDate = Date("1975-07-05"),
                address = listOf(Address(country = "USA")),
                maritalStatus = CodeableConcept(text = "M"),
                multipleBirth = DynamicValue(type = DynamicValueType.BASE_64_BINARY, value = 2)
            ).validate().alertIfErrors()
        }
        assertEquals(
            "Patient multipleBirth can only be one of the following data types: Boolean, Integer",
            exception.message
        )
    }

    @Test
    fun `fails for multiple issues`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                OncologyPatient(
                    identifier = listOf(
                        Identifier(
                            system = CodeSystem.RONIN_TENANT.uri,
                            type = CodeableConcepts.RONIN_TENANT,
                            value = "tenantId"
                        )
                    ),
                    name = listOf(HumanName(family = "Doe")),
                    telecom = listOf(
                        ContactPoint(
                            system = ContactPointSystem.PHONE,
                            value = "8675309",
                            use = ContactPointUse.MOBILE
                        )
                    ),
                    gender = AdministrativeGender.FEMALE,
                    birthDate = Date("1975-07-05"),
                    address = listOf(Address(country = "USA")),
                    maritalStatus = CodeableConcept(text = "M")
                ).validate().alertIfErrors()
            }
        assertEquals(
            "Encountered multiple validation errors:\nmrn identifier is required\nfhir_stu3_id identifier is required",
            exception.message
        )
    }
}
