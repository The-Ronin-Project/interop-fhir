package com.projectronin.interop.fhir.stu3.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.Signature
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.BundleLink
import com.projectronin.interop.fhir.r4.resource.Participant
import com.projectronin.interop.fhir.r4.valueset.AppointmentStatus
import com.projectronin.interop.fhir.r4.valueset.BundleType
import com.projectronin.interop.fhir.r4.valueset.ParticipationStatus
import com.projectronin.interop.fhir.stu3.element.STU3BundleEntry
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class STU3BundleTest {
    private val miniAppt = STU3Appointment(
        status = AppointmentStatus.BOOKED.asCode(),
        participant = listOf(
            Participant(
                status = ParticipationStatus.ACCEPTED.asCode(),
                actor = Reference(reference = FHIRString("reference"))
            )
        )
    )

    private val goodBundle = STU3Bundle(
        id = Id("1234"),
        meta = Meta(profile = listOf(Canonical("STU3profile"))),
        implicitRules = Uri("implicit-rules"),
        language = Code("en-US"),
        identifier = Identifier(value = FHIRString("identifier")),
        type = BundleType.SEARCHSET.asCode(),
        timestamp = Instant("2017-01-01T00:00:00Z"),
        total = UnsignedInt(1),
        link = listOf(BundleLink(relation = FHIRString("next"), url = Uri("http://example.com"))),
        entry = listOf(STU3BundleEntry(resource = miniAppt)),
        signature = Signature(
            type = listOf(Coding(display = FHIRString("type"))),
            `when` = Instant("2017-01-01T00:00:00Z"),
            who = Reference(reference = FHIRString("who"))
        )
    )

    @Test
    fun `can serialize and deserialize JSON with known resource type`() {
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(goodBundle)

        val expectedJson = """
            {
              "resourceType" : "Bundle",
              "id" : "1234",
              "meta" : {
                "profile" : [ "STU3profile" ]
              },
              "implicitRules" : "implicit-rules",
              "language" : "en-US",
              "identifier" : {
                "value" : "identifier"
              },
              "type" : "searchset",
              "timestamp" : "2017-01-01T00:00:00Z",
              "total" : 1,
              "link" : [ {
                "relation" : "next",
                "url" : "http://example.com"
              } ],
              "entry" : [ {
                "resource" : {
                  "resourceType" : "Appointment",
                  "status" : "booked",
                  "participant" : [ {
                    "actor" : {
                      "reference" : "reference"
                    },
                    "status" : "accepted"
                  } ]
                }
              } ],
              "signature" : {
                "type" : [ {
                  "display" : "type"
                } ],
                "when" : "2017-01-01T00:00:00Z",
                "who" : {
                  "reference" : "who"
                }
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedBundle = objectMapper.readValue<STU3Bundle>(json)
        assertEquals(goodBundle, deserializedBundle)
    }

    @Test
    fun `test transform to R4`() {
        val r4Bundle = goodBundle.transformToR4()
        assertEquals(r4Bundle.entry.first().resource, goodBundle.entry.first().resource!!.transformToR4())
    }
}
