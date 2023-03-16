package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Range
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.r4.valueset.ObservationStatus
import com.projectronin.interop.fhir.r4.valueset.QuantityComparator
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ObservationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val quantityValue = Quantity(
            value = Decimal(17.5),
            comparator = QuantityComparator.GREATER_OR_EQUAL_TO.asCode(),
            unit = FHIRString("years"),
            system = CodeSystem.UCUM.uri,
            code = Code("a")
        )
        val valueType = CodeableConcept(
            coding = listOf(
                Coding(
                    system = Uri("http://value.org"),
                    code = Code("Quantity value")
                )
            )
        )
        val observation = Observation(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("RoninObservationHeartRate"))
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
            basedOn = listOf(Reference(reference = FHIRString("CarePlan/123"))),
            partOf = listOf(Reference(reference = FHIRString("Procedure/123"))),
            status = ObservationStatus.FINAL.asCode(),
            category = listOf(CodeableConcept(text = FHIRString("category"))),
            code = CodeableConcept(text = FHIRString("code")),
            subject = Reference(reference = FHIRString("subject")),
            focus = listOf(Reference(reference = FHIRString("Condition/123"))),
            encounter = Reference(reference = FHIRString("Encounter/123")),
            effective = DynamicValue(DynamicValueType.INSTANT, Instant("2017-01-01T00:00:00Z")),
            issued = Instant("2017-01-01T00:00:00Z"),
            performer = listOf(Reference(reference = FHIRString("performer"))),
            note = listOf(Annotation(text = Markdown("note"))),
            value = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2019-04-01")),
            dataAbsentReason = CodeableConcept(text = FHIRString("data absent reason")),
            interpretation = listOf(CodeableConcept(text = FHIRString("interpretation"))),
            bodySite = CodeableConcept(text = FHIRString("body site")),
            method = CodeableConcept(text = FHIRString("method")),
            specimen = Reference(reference = FHIRString("Specimen/123")),
            device = Reference(reference = FHIRString("Device/123")),
            referenceRange = listOf(
                ObservationReferenceRange(
                    low = SimpleQuantity(value = Decimal(1.0)),
                    high = SimpleQuantity(value = Decimal(10.0)),
                    type = CodeableConcept(text = FHIRString("type")),
                    appliesTo = listOf(CodeableConcept(text = FHIRString("applies to"))),
                    age = Range(low = SimpleQuantity(value = Decimal(13.5))),
                    text = FHIRString("reference range")
                )
            ),
            hasMember = listOf(Reference(reference = FHIRString("Observation/789"))),
            derivedFrom = listOf(Reference(reference = FHIRString("DocumentReference/123"))),
            component = listOf(
                ObservationComponent(
                    code = valueType,
                    value = DynamicValue(DynamicValueType.QUANTITY, quantityValue)
                )
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(observation)

        val expectedJson = """
            {
              "resourceType" : "Observation",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninObservationHeartRate" ]
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
              "basedOn" : [ {
                "reference" : "CarePlan/123"
              } ],
              "partOf" : [ {
                "reference" : "Procedure/123"
              } ],
              "status" : "final",
              "category" : [ {
                "text" : "category"
              } ],
              "code" : {
                "text" : "code"
              },
              "subject" : {
                "reference" : "subject"
              },
              "focus" : [ {
                "reference" : "Condition/123"
              } ],
              "encounter" : {
                "reference" : "Encounter/123"
              },
              "effectiveInstant" : "2017-01-01T00:00:00Z",
              "issued" : "2017-01-01T00:00:00Z",
              "performer" : [ {
                "reference" : "performer"
              } ],
              "note" : [ {
                "text" : "note"
              } ],
              "valueDateTime" : "2019-04-01",
              "dataAbsentReason" : {
                "text" : "data absent reason"
              },
              "interpretation" : [ {
                "text" : "interpretation"
              } ],
              "bodySite" : {
                "text" : "body site"
              },
              "method" : {
                "text" : "method"
              },
              "specimen" : {
                "reference" : "Specimen/123"
              },
              "device" : {
                "reference" : "Device/123"
              },
              "referenceRange" : [ {
                "low" : {
                  "value" : 1.0
                },
                "high" : {
                  "value" : 10.0
                },
                "type" : {
                  "text" : "type"
                },
                "appliesTo" : [ {
                  "text" : "applies to"
                } ],
                "age" : {
                  "low" : {
                    "value" : 13.5
                  }
                },
                "text" : "reference range"
              } ],
              "hasMember" : [ {
                "reference" : "Observation/789"
              } ],
              "derivedFrom" : [ {
                "reference" : "DocumentReference/123"
              } ],
              "component" : [ {
                "code" : {
                  "coding" : [ {
                    "system" : "http://value.org",
                    "code" : "Quantity value"
                  } ]
                },
                "valueQuantity" : {
                  "value" : 17.5,
                  "comparator" : ">=",
                  "unit" : "years",
                  "system" : "http://unitsofmeasure.org",
                  "code" : "a"
                }
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedObservation = JacksonManager.objectMapper.readValue<Observation>(json)
        assertEquals(observation, deserializedObservation)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val observation = Observation(
            status = ObservationStatus.FINAL.asCode(),
            code = CodeableConcept(text = FHIRString("code")),
            subject = Reference(reference = FHIRString("subject"))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(observation)

        val expectedJson = """
            {
              "resourceType" : "Observation",
              "status" : "final",
              "code" : {
                "text" : "code"
              },
              "subject" : {
                "reference" : "subject"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "resourceType" : "Observation",
              "status" : "final",
              "code" : {
                "text" : "code"
              },
              "subject" : {
                "reference" : "subject"
              }
            }
        """.trimIndent()
        val observation = JacksonManager.objectMapper.readValue<Observation>(json)
        assertNull(observation.id)
        assertNull(observation.meta)
        assertNull(observation.implicitRules)
        assertNull(observation.language)
        assertNull(observation.text)
        assertEquals(listOf<Resource<Nothing>>(), observation.contained)
        assertEquals(listOf<Extension>(), observation.extension)
        assertEquals(listOf<Extension>(), observation.modifierExtension)
        assertEquals(listOf<Identifier>(), observation.identifier)
        assertEquals(listOf<Reference>(), observation.basedOn)
        assertEquals(listOf<Reference>(), observation.partOf)
        assertEquals(listOf<CodeableConcept>(), observation.category)
        assertEquals(listOf<Reference>(), observation.focus)
        assertNull(observation.encounter)
        assertNull(observation.effective)
        assertNull(observation.issued)
        assertEquals(listOf<Reference>(), observation.performer)
        assertNull(observation.value)
        assertNull(observation.dataAbsentReason)
        assertEquals(listOf<CodeableConcept>(), observation.interpretation)
        assertNull(observation.bodySite)
        assertNull(observation.method)
        assertNull(observation.specimen)
        assertNull(observation.device)
        assertEquals(listOf<ObservationReferenceRange>(), observation.referenceRange)
        assertEquals(listOf<Reference>(), observation.hasMember)
        assertEquals(listOf<Reference>(), observation.derivedFrom)
        assertEquals(listOf<ObservationComponent>(), observation.component)
        assertEquals(listOf<Annotation>(), observation.note)
    }
}
