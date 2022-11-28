package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CareTeamParticipant
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
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.CareTeamStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class CareTeamTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val careTeam = CareTeam(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("RoninCareTeam")),
            ),
            implicitRules = Uri("implicit-rules"),
            language = Code("en-US"),
            text = Narrative(
                status = com.projectronin.interop.fhir.r4.valueset.NarrativeStatus.GENERATED.asCode(),
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
            status = CareTeamStatus.ENTERED_IN_ERROR.asCode(),
            category = listOf(CodeableConcept(text = "care team category")),
            name = "group",
            subject = Reference(type = Uri("Patient"), reference = "Reference/pat1", display = "Elaine Benes"),
            encounter = Reference(reference = "Encounter/123"),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            ),
            participant = listOf(
                CareTeamParticipant(
                    member = Reference(
                        reference = "Practitioner/f001",
                        display = "Dr. Van Nostrand"
                    ),
                    onBehalfOf = Reference(
                        reference = "Organization/org1",
                        display = "Hoffermanndale Clinic"
                    ),
                    period = Period(
                        start = DateTime(value = "2021-11-17T08:00:00Z"),
                        end = DateTime(value = "2021-11-17T09:00:00Z")
                    ),
                )
            ),
            reasonCode = listOf(CodeableConcept(text = "reason code")),
            reasonReference = listOf(Reference(reference = "Conditioner/cond1")),
            managingOrganization = listOf(Reference(reference = "Organization/org2", display = "Juilliard")),
            telecom = listOf(ContactPoint()),
            note = listOf(Annotation(text = Markdown("Difficult patient")))

        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(careTeam)

        val expectedJson = """
            {
              "resourceType" : "CareTeam",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninCareTeam" ]
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
              "status" : "entered-in-error",
              "category" : [ {
                "text" : "care team category"
              } ],
              "name" : "group",
              "subject" : {
                "reference" : "Reference/pat1",
                "type" : "Patient",
                "display" : "Elaine Benes"
              },
              "encounter" : {
                "reference" : "Encounter/123"
              },
              "period" : {
                "start" : "2021-11-17T08:00:00Z",
                "end" : "2021-11-17T09:00:00Z"
              },
              "participant" : [ {
                "member" : {
                  "reference" : "Practitioner/f001",
                  "display" : "Dr. Van Nostrand"
                },
                "onBehalfOf" : {
                  "reference" : "Organization/org1",
                  "display" : "Hoffermanndale Clinic"
                },
                "period" : {
                  "start" : "2021-11-17T08:00:00Z",
                  "end" : "2021-11-17T09:00:00Z"
                }
              } ],
              "reasonCode" : [ {
                "text" : "reason code"
              } ],
              "reasonReference" : [ {
                "reference" : "Conditioner/cond1"
              } ],
              "managingOrganization" : [ {
                "reference" : "Organization/org2",
                "display" : "Juilliard"
              } ],
              "telecom" : [ { } ],
              "note" : [ {
                "text" : "Difficult patient"
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedCareTeam = JacksonManager.objectMapper.readValue<CareTeam>(json)
        assertEquals(careTeam, deserializedCareTeam)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val careTeam = CareTeam()
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(careTeam)

        val expectedJson = """
            {
              "resourceType" : "CareTeam"
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "resourceType" : "CareTeam"
            }
        """.trimIndent()
        val careTeam = JacksonManager.objectMapper.readValue<CareTeam>(json)

        assertNull(careTeam.id)
        assertNull(careTeam.meta)
        assertNull(careTeam.implicitRules)
        assertNull(careTeam.language)
        assertNull(careTeam.text)
        assertEquals(listOf<Resource<Nothing>>(), careTeam.contained)
        assertEquals(listOf<Extension>(), careTeam.extension)
        assertEquals(listOf<Extension>(), careTeam.modifierExtension)
        assertEquals(listOf<Identifier>(), careTeam.identifier)
        assertNull(careTeam.status)
        assertEquals(listOf<CodeableConcept>(), careTeam.category)
        assertNull(careTeam.name)
        assertNull(careTeam.subject)
        assertNull(careTeam.encounter)
        assertNull(careTeam.period)
        assertEquals(listOf<CareTeamParticipant>(), careTeam.participant)
        assertEquals(listOf<CodeableConcept>(), careTeam.reasonCode)
        assertEquals(listOf<Reference>(), careTeam.reasonReference)
        assertEquals(listOf<Reference>(), careTeam.managingOrganization)
        assertEquals(listOf<ContactPoint>(), careTeam.telecom)
        assertEquals(listOf<Annotation>(), careTeam.note)
    }
}
