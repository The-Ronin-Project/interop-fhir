package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
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

class PractitionerRoleTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val practitionerRole =
            PractitionerRole(
                id = Id("12345"),
                meta =
                    Meta(
                        profile = listOf(Canonical("RoninPractitionerRole")),
                    ),
                implicitRules = Uri("implicit-rules"),
                language = Code("en-US"),
                text =
                    Narrative(
                        status = NarrativeStatus.GENERATED.asCode(),
                        div = FHIRString("div"),
                    ),
                contained = listOf(Location(id = Id("1234"), name = FHIRString("Contained Location"))),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                identifier = listOf(Identifier(value = FHIRString("id"))),
                active = FHIRBoolean.TRUE,
                period = Period(end = DateTime("2022")),
                practitioner = Reference(reference = FHIRString("Practitioner/1234")),
                organization = Reference(reference = FHIRString("Organization/5678")),
                code = listOf(CodeableConcept(text = FHIRString("code"))),
                specialty = listOf(CodeableConcept(text = FHIRString("specialty"))),
                location = listOf(Reference(reference = FHIRString("Location/9012"))),
                healthcareService = listOf(Reference(reference = FHIRString("HealthcareService/3456"))),
                telecom = listOf(ContactPoint(value = FHIRString("8675309"), system = ContactPointSystem.PHONE.asCode())),
                availableTime = listOf(AvailableTime(allDay = FHIRBoolean.FALSE)),
                notAvailable = listOf(NotAvailable(description = FHIRString("Not available now"))),
                availabilityExceptions = FHIRString("exceptions"),
                endpoint = listOf(Reference(reference = FHIRString("Endpoint/1357"))),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(practitionerRole)

        val expectedJson =
            """
            |{
            |  "resourceType" : "PractitionerRole",
            |  "id" : "12345",
            |  "meta" : {
            |    "profile" : [ "RoninPractitionerRole" ]
            |  },
            |  "implicitRules" : "implicit-rules",
            |  "language" : "en-US",
            |  "text" : {
            |    "status" : "generated",
            |    "div" : "div"
            |  },
            |  "contained" : [ {
            |    "resourceType" : "Location",
            |    "id" : "1234",
            |    "name" : "Contained Location"
            |  } ],
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "modifierExtension" : [ {
            |    "url" : "http://localhost/modifier-extension",
            |    "valueString" : "Value"
            |  } ],
            |  "identifier" : [ {
            |    "value" : "id"
            |  } ],
            |  "active" : true,
            |  "period" : {
            |    "end" : "2022"
            |  },
            |  "practitioner" : {
            |    "reference" : "Practitioner/1234"
            |  },
            |  "organization" : {
            |    "reference" : "Organization/5678"
            |  },
            |  "code" : [ {
            |    "text" : "code"
            |  } ],
            |  "specialty" : [ {
            |    "text" : "specialty"
            |  } ],
            |  "location" : [ {
            |    "reference" : "Location/9012"
            |  } ],
            |  "healthcareService" : [ {
            |    "reference" : "HealthcareService/3456"
            |  } ],
            |  "telecom" : [ {
            |    "system" : "phone",
            |    "value" : "8675309"
            |  } ],
            |  "availableTime" : [ {
            |    "allDay" : false
            |  } ],
            |  "notAvailable" : [ {
            |    "description" : "Not available now"
            |  } ],
            |  "availabilityExceptions" : "exceptions",
            |  "endpoint" : [ {
            |    "reference" : "Endpoint/1357"
            |  } ]
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedPractitionerRole = objectMapper.readValue<PractitionerRole>(json)
        assertEquals(practitionerRole, deserializedPractitionerRole)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val practitionerRole =
            PractitionerRole(
                identifier = listOf(Identifier(value = FHIRString("id"))),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(practitionerRole)

        val expectedJson =
            """
            |{
            |  "resourceType" : "PractitionerRole",
            |  "identifier" : [ {
            |    "value" : "id"
            |  } ]
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            |{
            |  "resourceType" : "PractitionerRole",
            |  "active" : true
            |}
            """.trimMargin()
        val practitionerRole = objectMapper.readValue<PractitionerRole>(json)

        assertNull(practitionerRole.id)
        assertNull(practitionerRole.meta)
        assertNull(practitionerRole.implicitRules)
        assertNull(practitionerRole.language)
        assertNull(practitionerRole.text)
        assertEquals(listOf<Resource<Nothing>>(), practitionerRole.contained)
        assertEquals(listOf<Extension>(), practitionerRole.extension)
        assertEquals(listOf<Extension>(), practitionerRole.modifierExtension)
        assertEquals(listOf<Identifier>(), practitionerRole.identifier)
        assertEquals(FHIRBoolean.TRUE, practitionerRole.active)
        assertNull(practitionerRole.period)
        assertNull(practitionerRole.practitioner)
        assertNull(practitionerRole.organization)
        assertEquals(listOf<CodeableConcept>(), practitionerRole.code)
        assertEquals(listOf<CodeableConcept>(), practitionerRole.specialty)
        assertEquals(listOf<Reference>(), practitionerRole.location)
        assertEquals(listOf<Reference>(), practitionerRole.healthcareService)
        assertEquals(listOf<ContactPoint>(), practitionerRole.telecom)
        assertEquals(listOf<AvailableTime>(), practitionerRole.availableTime)
        assertEquals(listOf<NotAvailable>(), practitionerRole.notAvailable)
        assertNull(practitionerRole.availabilityExceptions)
        assertEquals(listOf<Reference>(), practitionerRole.endpoint)
    }
}

class NotAvailableTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val notAvailable =
            NotAvailable(
                id = FHIRString("67890"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                description = FHIRString("Not available now"),
                during = Period(start = DateTime("2021-12-01"), end = DateTime("2021-12-08")),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(notAvailable)

        val expectedJson =
            """
            |{
            |  "id" : "67890",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "modifierExtension" : [ {
            |    "url" : "http://localhost/modifier-extension",
            |    "valueString" : "Value"
            |  } ],
            |  "description" : "Not available now",
            |  "during" : {
            |    "start" : "2021-12-01",
            |    "end" : "2021-12-08"
            |  }
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedNotAvailable = objectMapper.readValue<NotAvailable>(json)
        assertEquals(notAvailable, deserializedNotAvailable)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val notAvailable =
            NotAvailable(
                description = FHIRString("Vacation"),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(notAvailable)

        val expectedJson =
            """
            |{
            |  "description" : "Vacation"
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            |{
            |  "description" : "Vacation"
            |}
            """.trimMargin()
        val notAvailable = objectMapper.readValue<NotAvailable>(json)

        assertNull(notAvailable.id)
        assertEquals(listOf<Extension>(), notAvailable.extension)
        assertEquals(listOf<Extension>(), notAvailable.modifierExtension)
        assertEquals(FHIRString("Vacation"), notAvailable.description)
        assertNull(notAvailable.during)
    }
}
