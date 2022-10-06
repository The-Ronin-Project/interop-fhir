package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
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
import com.projectronin.interop.fhir.r4.valueset.AdministrativeGender
import com.projectronin.interop.fhir.r4.valueset.ContactPointSystem
import com.projectronin.interop.fhir.r4.valueset.LinkType
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class PatientTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val deceased = DynamicValue(type = DynamicValueType.BOOLEAN, value = false)
        val multipleBirth = DynamicValue(type = DynamicValueType.INTEGER, value = 2)
        val patient = Patient(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("RoninPatient")),
            ),
            implicitRules = Uri("implicit-rules"),
            language = Code("en-US"),
            text = Narrative(
                status = NarrativeStatus.GENERATED.asCode(),
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
            identifier = listOf(Identifier(value = "id")),
            active = true,
            name = listOf(HumanName(family = "Doe")),
            telecom = listOf(ContactPoint(value = "8675309", system = ContactPointSystem.PHONE.asCode())),
            gender = AdministrativeGender.FEMALE.asCode(),
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
            link = listOf(PatientLink(other = Reference(display = "other patient"), type = LinkType.REPLACES.asCode()))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(patient)

        val expectedJson = """
            {
              "resourceType" : "Patient",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninPatient" ]
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
              "name" : [ {
                "family" : "Doe"
              } ],
              "telecom" : [ {
                "system" : "phone",
                "value" : "8675309"
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

        val deserializedPatient = JacksonManager.objectMapper.readValue<Patient>(json)
        assertEquals(patient, deserializedPatient)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val patient = Patient()
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(patient)

        val expectedJson = """
            {
              "resourceType" : "Patient"
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "resourceType" : "Patient"
            }
        """.trimIndent()
        val patient = JacksonManager.objectMapper.readValue<Patient>(json)

        assertNull(patient.id)
        assertNull(patient.meta)
        assertNull(patient.implicitRules)
        assertNull(patient.language)
        assertNull(patient.text)
        assertEquals(listOf<Resource<Nothing>>(), patient.contained)
        assertEquals(listOf<Extension>(), patient.extension)
        assertEquals(listOf<Extension>(), patient.modifierExtension)
        assertEquals(listOf<Identifier>(), patient.identifier)
        assertNull(patient.active)
        assertEquals(listOf<HumanName>(), patient.name)
        assertEquals(listOf<ContactPoint>(), patient.telecom)
        assertNull(patient.gender)
        assertNull(patient.birthDate)
        assertNull(patient.deceased)
        assertEquals(listOf<Address>(), patient.address)
        assertNull(patient.maritalStatus)
        assertNull(patient.multipleBirth)
        assertEquals(listOf<Attachment>(), patient.photo)
        assertEquals(listOf<Contact>(), patient.contact)
        assertEquals(listOf<Communication>(), patient.communication)
        assertEquals(listOf<Reference>(), patient.generalPractitioner)
        assertNull(patient.managingOrganization)
        assertEquals(listOf<PatientLink>(), patient.link)
    }
}
