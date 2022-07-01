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
import com.projectronin.interop.fhir.r4.datatype.ObservationComponent
import com.projectronin.interop.fhir.r4.datatype.ObservationReferenceRange
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Range
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.r4.valueset.ObservationStatus
import com.projectronin.interop.fhir.r4.valueset.QuantityComparator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ObservationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val quantityValue = Quantity(
            value = 17.5,
            comparator = QuantityComparator.GREATER_OR_EQUAL_TO,
            unit = "years",
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
            identifier = listOf(Identifier(value = "id")),
            status = ObservationStatus.FINAL,
            category = listOf(CodeableConcept(text = "category")),
            code = CodeableConcept(text = "code"),
            subject = Reference(reference = "subject"),
            performer = listOf(Reference(reference = "performer")),
            note = listOf(Annotation(text = Markdown("note"))),
            bodySite = CodeableConcept(text = "body site"),
            value = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2019-04-01")),
            component = listOf(
                ObservationComponent(
                    code = valueType,
                    value = DynamicValue(DynamicValueType.QUANTITY, quantityValue)
                )
            ),
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(observation)

        val expectedJson = """
            {
              "resourceType" : "Observation",
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
                "value" : "id"
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
              "performer" : [ {
                "reference" : "performer"
              } ],
              "valueDateTime" : "2019-04-01",
              "note" : [ {
                "text" : "note"
              } ],
              "bodySite" : {
                "text" : "body site"
              },
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
            status = ObservationStatus.FINAL,
            code = CodeableConcept(text = "code"),
            subject = Reference(reference = "subject"),
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
        assertEquals(listOf<Resource>(), observation.contained)
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

    @Test
    fun `referenceRange must have at least a low or a high or text`() {
        val quantity = Quantity(
            value = 60.0,
            unit = "mL/min/1.73m2",
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )
        val ex = assertThrows<IllegalArgumentException> {
            Observation(
                status = ObservationStatus.FINAL,
                code = CodeableConcept(text = "code"),
                subject = Reference(reference = "subject"),
                value = DynamicValue(DynamicValueType.QUANTITY, quantity),
                referenceRange = listOf(ObservationReferenceRange(age = Range(low = SimpleQuantity(value = 15.0))))
            )
        }
        assertEquals(
            "referenceRange must have at least a low or a high or text",
            ex.message
        )
    }

    @Test
    fun `referenceRange succeeds with only a low`() {
        val quantity = Quantity(
            value = 60.0,
            unit = "mL/min/1.73m2",
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )

        val referenceRangeHasLow = listOf(ObservationReferenceRange(low = SimpleQuantity(value = 18.0)))
        val observationHasLow = Observation(
            status = ObservationStatus.ENTERED_IN_ERROR,
            code = CodeableConcept(text = "code"),
            subject = Reference(display = "Peter Chalmers"),
            value = DynamicValue(DynamicValueType.QUANTITY, quantity),
            referenceRange = referenceRangeHasLow
        )

        assertNull(observationHasLow.id)
        assertEquals("Peter Chalmers", observationHasLow.subject?.display)
        assertEquals(18.0, observationHasLow.referenceRange.first().low?.value)
    }

    @Test
    fun `referenceRange succeeds with only a high`() {
        val quantity = Quantity(
            value = 60.0,
            unit = "mL/min/1.73m2",
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )

        val referenceRangeHasHigh = listOf(ObservationReferenceRange(high = SimpleQuantity(value = 11.0)))
        val observationHasHigh = Observation(
            status = ObservationStatus.REGISTERED,
            code = CodeableConcept(text = "code"),
            subject = Reference(display = "Peter Chalmers"),
            value = DynamicValue(DynamicValueType.QUANTITY, quantity),
            referenceRange = referenceRangeHasHigh
        )

        assertNull(observationHasHigh.id)
        assertEquals("Peter Chalmers", observationHasHigh.subject?.display)
        assertEquals(11.0, observationHasHigh.referenceRange.first().high?.value)
    }

    @Test
    fun `referenceRange succeeds with only text`() {
        val quantity = Quantity(
            value = 60.0,
            unit = "mL/min/1.73m2",
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )

        val referenceRangeHasText = listOf(ObservationReferenceRange(text = "Range Boundary"))
        val observationHasText = Observation(
            status = ObservationStatus.PRELIMINARY,
            code = CodeableConcept(text = "code"),
            subject = Reference(display = "Peter Chalmers"),
            value = DynamicValue(DynamicValueType.QUANTITY, quantity),
            referenceRange = referenceRangeHasText
        )

        assertNull(observationHasText.id)
        assertEquals("Peter Chalmers", observationHasText.subject?.display)
        assertEquals("Range Boundary", observationHasText.referenceRange.first().text)
    }

    @Test
    fun `component referenceRange must have at least a low or a high or text`() {
        val testCodeableConcept1 = CodeableConcept(coding = listOf(Coding(code = Code("code1"))))
        val testCodeableConcept2 = CodeableConcept(coding = listOf(Coding(code = Code("code2"))))
        val quantity = Quantity(
            value = 60.0,
            unit = "mL/min/1.73m2",
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )
        val ex = assertThrows<IllegalArgumentException> {
            Observation(
                status = ObservationStatus.FINAL,
                code = testCodeableConcept2,
                subject = Reference(reference = "subject"),
                value = DynamicValue(DynamicValueType.QUANTITY, quantity),
                component = listOf(
                    ObservationComponent(
                        code = testCodeableConcept1,
                        value = DynamicValue(DynamicValueType.QUANTITY, quantity),
                        referenceRange = listOf(
                            ObservationReferenceRange(
                                age = Range(
                                    low = SimpleQuantity(value = 15.0)
                                )
                            )
                        )
                    )
                )
            )
        }
        assertEquals(
            "referenceRange must have at least a low or a high or text",
            ex.message
        )
    }

    @Test
    fun `component referenceRange succeeds with only a low`() {
        val quantity = Quantity(
            value = 60.0,
            unit = "mL/min/1.73m2",
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )

        val referenceRangeHasLow = listOf(ObservationReferenceRange(low = SimpleQuantity(value = 18.0)))
        val componentHasLow = listOf(
            ObservationComponent(
                code = CodeableConcept(text = "another code"),
                value = DynamicValue(DynamicValueType.QUANTITY, quantity),
                referenceRange = referenceRangeHasLow
            )
        )
        val observationHasLow = Observation(
            status = ObservationStatus.ENTERED_IN_ERROR,
            code = CodeableConcept(text = "code"),
            subject = Reference(display = "Peter Chalmers"),
            value = DynamicValue(DynamicValueType.QUANTITY, quantity),
            component = componentHasLow
        )
        assertNull(observationHasLow.id)
        assertEquals("Peter Chalmers", observationHasLow.subject?.display)
        assertEquals(18.0, observationHasLow.component.first().referenceRange.first().low?.value)
    }

    @Test
    fun `component referenceRange succeeds with only a high`() {
        val quantity = Quantity(
            value = 60.0,
            unit = "mL/min/1.73m2",
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )

        val referenceRangeHasHigh = listOf(ObservationReferenceRange(high = SimpleQuantity(value = 11.0)))
        val componentHasHigh = listOf(
            ObservationComponent(
                code = CodeableConcept(text = "another code"),
                value = DynamicValue(DynamicValueType.QUANTITY, quantity),
                referenceRange = referenceRangeHasHigh
            )
        )
        val observationHasHigh = Observation(
            status = ObservationStatus.REGISTERED,
            code = CodeableConcept(text = "code"),
            subject = Reference(display = "Peter Chalmers"),
            value = DynamicValue(DynamicValueType.QUANTITY, quantity),
            component = componentHasHigh
        )
        assertNull(observationHasHigh.id)
        assertEquals("Peter Chalmers", observationHasHigh.subject?.display)
        assertEquals(11.0, observationHasHigh.component.first().referenceRange.first().high?.value)
    }

    @Test
    fun `component referenceRange succeeds with only text`() {
        val quantity = Quantity(
            value = 60.0,
            unit = "mL/min/1.73m2",
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )

        val referenceRangeHasText = listOf(ObservationReferenceRange(text = "Range Boundary"))
        val componentHasText = listOf(
            ObservationComponent(
                code = CodeableConcept(text = "another code"),
                value = DynamicValue(DynamicValueType.QUANTITY, quantity),
                referenceRange = referenceRangeHasText
            )
        )
        val observationHasText = Observation(
            status = ObservationStatus.PRELIMINARY,
            code = CodeableConcept(text = "code"),
            subject = Reference(display = "Peter Chalmers"),
            value = DynamicValue(DynamicValueType.QUANTITY, quantity),
            component = componentHasText
        )
        assertNull(observationHasText.id)
        assertEquals("Peter Chalmers", observationHasText.subject?.display)
        assertEquals("Range Boundary", observationHasText.component.first().referenceRange.first().text)
    }

    @Test
    fun `dataAbsentReason SHALL only be present if value is not present`() {
        val quantity = Quantity(
            value = 60.0,
            unit = "mL/min/1.73m2",
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )
        val ex = assertThrows<IllegalArgumentException> {
            Observation(
                status = ObservationStatus.CANCELLED,
                code = CodeableConcept(text = "code"),
                subject = Reference(display = "Peter Chalmers"),
                value = DynamicValue(DynamicValueType.QUANTITY, quantity),
                dataAbsentReason = CodeableConcept(text = "unable to reach vein"),
            )
        }
        assertEquals(
            "dataAbsentReason SHALL only be present if value[x] is not present",
            ex.message
        )
    }

    @Test
    fun `component dataAbsentReason SHALL only be present if value is not present`() {
        val quantity = Quantity(
            value = 60.0,
            unit = "mL/min/1.73m2",
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )
        val ex = assertThrows<IllegalArgumentException> {
            Observation(
                status = ObservationStatus.CANCELLED,
                code = CodeableConcept(text = "code"),
                subject = Reference(display = "Peter Chalmers"),
                component = listOf(
                    ObservationComponent(
                        code = CodeableConcept(text = "different from parent code"),
                        value = DynamicValue(DynamicValueType.QUANTITY, quantity),
                        dataAbsentReason = CodeableConcept(text = "unable to reach vein"),
                    )
                )
            )
        }
        assertEquals(
            "dataAbsentReason SHALL only be present if value[x] is not present",
            ex.message
        )
    }

    @Test
    fun `If Observation code is the same as an Observation component code then the Observation value SHALL NOT be present`() {
        val quantity = Quantity(
            value = 60.0,
            unit = "mL/min/1.73m2",
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )
        val testCodeableConcept1 = CodeableConcept(coding = listOf(Coding(code = Code("code1"))))
        val testCodeableConcept2 = CodeableConcept(coding = listOf(Coding(code = Code("code2"))))
        val component1 = ObservationComponent(
            code = testCodeableConcept1,
            value = DynamicValue(DynamicValueType.QUANTITY, quantity)
        )
        val component2 = ObservationComponent(
            code = testCodeableConcept2,
            value = DynamicValue(DynamicValueType.QUANTITY, quantity)
        )
        val ex = assertThrows<IllegalArgumentException> {
            Observation(
                status = ObservationStatus.FINAL,
                code = testCodeableConcept1,
                subject = Reference(display = "Peter Chalmers"),
                value = (DynamicValue(DynamicValueType.QUANTITY, quantity)),
                component = listOf(component1, component2)
            )
        }
        assertEquals(
            "If Observation.code is the same as an Observation.component.code then the Observation.value SHALL NOT be present",
            ex.message
        )
    }

    @Test
    fun `succeeds when there is a Observation value and no Observation code is the same as any Observation component code`() {
        val quantity = Quantity(
            value = 60.0,
            unit = "mL/min/1.73m2",
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )
        val testCodeableConcept1 = CodeableConcept(coding = listOf(Coding(code = Code("code1"))))
        val testCodeableConcept2 = CodeableConcept(coding = listOf(Coding(code = Code("code2"))))
        val component2 = ObservationComponent(
            code = testCodeableConcept2,
            value = DynamicValue(DynamicValueType.QUANTITY, quantity)
        )
        val observation = Observation(
            status = ObservationStatus.FINAL,
            code = testCodeableConcept1,
            subject = Reference(display = "Peter Chalmers"),
            value = (DynamicValue(DynamicValueType.QUANTITY, quantity)),
            component = listOf(component2, component2)
        )
        assertNull(observation.id)
        assertEquals("Peter Chalmers", observation.subject?.display)
        val observationValue = observation.value as DynamicValue<Quantity>
        assertEquals(60.0, observationValue.value.value)
        assertEquals("mL/min/1.73m2", observationValue.value.unit)
    }

    @Test
    fun `cannot create effective time with unsupported dynamic value type`() {
        val quantity = Quantity(
            value = 60.0,
            unit = "mL/min/1.73m2",
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )
        val ex = assertThrows<IllegalArgumentException> {
            Observation(
                status = ObservationStatus.FINAL,
                code = CodeableConcept(text = "code"),
                subject = Reference(reference = "subject"),
                effective = DynamicValue(type = DynamicValueType.BOOLEAN, value = false),
                value = (DynamicValue(DynamicValueType.QUANTITY, quantity)),
            )
        }
        assertEquals(
            "Observation effective can only be one of the following data types: DateTime, Period, Timing, Instant",
            ex.message
        )
    }
}
