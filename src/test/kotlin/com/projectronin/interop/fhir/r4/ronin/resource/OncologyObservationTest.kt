package com.projectronin.interop.fhir.r4.ronin.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.CodeableConcepts
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
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Range
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.resource.Observation
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.r4.valueset.ObservationStatus
import com.projectronin.interop.fhir.r4.valueset.QuantityComparator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OncologyObservationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val stringValue = "Value"
        val dateTimeValue = DateTime("2019-04-01")
        val timePeriodValue = Period(start = dateTimeValue, end = dateTimeValue)
        val codeableConceptValue = CodeableConcept(
            coding = listOf(
                Coding(
                    system = Uri("http://snomed.info/sct"),
                    code = Code("35748005"),
                    display = "Wine (substance)"
                )
            ),
            text = "Wine"
        )
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
                    system = Uri("http://type.com"),
                    code = Code("Value Type")
                )
            )
        )
        val componentList = listOf(
            ObservationComponent(
                code = valueType,
                value = DynamicValue(DynamicValueType.PERIOD, timePeriodValue)
            ),
            ObservationComponent(
                code = valueType,
                value = DynamicValue(DynamicValueType.QUANTITY, quantityValue)
            ),
            ObservationComponent(
                code = valueType,
                value = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, codeableConceptValue)
            ),
            ObservationComponent(
                code = valueType,
                value = DynamicValue(DynamicValueType.STRING, stringValue)
            )
        )
        val oncologyObservation = OncologyObservation(
            id = Id("component-answers"),
            meta = Meta(
                profile = listOf(Canonical("http://projectronin.com/fhir/us/ronin/StructureDefinition/oncology-condition"))
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
                )
            ),
            status = ObservationStatus.FINAL,
            category = listOf(
                CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("http://terminology.hl7.org/CodeSystem/observation-category"),
                            code = Code("social-history"),
                            display = "Social History"
                        )
                    ),
                    text = "Social History"
                )
            ),
            code = CodeableConcept(
                coding = listOf(
                    Coding(
                        system = Uri("http://type.org"),
                        code = Code("any type"),
                        display = "Type"
                    )
                )
            ),
            value = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2019-04-01")),
            component = componentList,
            subject = Reference(
                reference = "Patient/example"
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(oncologyObservation)

        val expectedJson = """
            |{
            |  "resourceType" : "Observation",
            |  "id" : "component-answers",
            |  "meta" : {
            |    "profile" : [ "http://projectronin.com/fhir/us/ronin/StructureDefinition/oncology-condition" ]
            |  },
            |  "implicitRules" : "implicit-rules",
            |  "language" : "en-US",
            |  "text" : {
            |    "status" : "generated",
            |    "div" : "div"
            |  },
            |  "contained" : [ {"resourceType":"Banana","field":"24680"} ],
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "modifierExtension" : [ {
            |    "url" : "http://localhost/modifier-extension",
            |    "valueString" : "Value"
            |  } ],
            |  "identifier" : [ {
            |    "type" : {
            |      "coding" : [ {
            |        "system" : "http://projectronin.com/id/tenantId",
            |        "code" : "TID",
            |        "display" : "Ronin-specified Tenant Identifier"
            |      } ],
            |      "text" : "Tenant ID"
            |    },
            |    "system" : "http://projectronin.com/id/tenantId",
            |    "value" : "tenantId"
            |  } ],
            |  "status" : "final",
            |  "category" : [ {
            |    "coding" : [ {
            |      "system" : "http://terminology.hl7.org/CodeSystem/observation-category",
            |      "code" : "social-history",
            |      "display" : "Social History"
            |    } ],
            |    "text" : "Social History"
            |  } ],
            |  "code" : {
            |    "coding" : [ {
            |      "system" : "http://type.org",
            |      "code" : "any type",
            |      "display" : "Type"
            |    } ]
            |  },
            |  "subject" : {
            |    "reference" : "Patient/example"
            |  },
            |  "valueDateTime" : "2019-04-01",
            |  "component" : [ {
            |    "code" : {
            |      "coding" : [ {
            |        "system" : "http://type.com",
            |        "code" : "Value Type"
            |      } ]
            |    },
            |    "valuePeriod" : {
            |      "start" : "2019-04-01",
            |      "end" : "2019-04-01"
            |    }
            |  }, {
            |    "code" : {
            |      "coding" : [ {
            |        "system" : "http://type.com",
            |        "code" : "Value Type"
            |      } ]
            |    },
            |    "valueQuantity" : {
            |      "value" : 17.5,
            |      "comparator" : ">=",
            |      "unit" : "years",
            |      "system" : "http://unitsofmeasure.org",
            |      "code" : "a"
            |    }
            |  }, {
            |    "code" : {
            |      "coding" : [ {
            |        "system" : "http://type.com",
            |        "code" : "Value Type"
            |      } ]
            |    },
            |    "valueCodeableConcept" : {
            |      "coding" : [ {
            |        "system" : "http://snomed.info/sct",
            |        "code" : "35748005",
            |        "display" : "Wine (substance)"
            |      } ],
            |      "text" : "Wine"
            |    }
            |  }, {
            |    "code" : {
            |      "coding" : [ {
            |        "system" : "http://type.com",
            |        "code" : "Value Type"
            |      } ]
            |    },
            |    "valueString" : "Value"
            |  } ]
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedOncologyObservation = JacksonManager.objectMapper.readValue<OncologyObservation>(json)
        assertEquals(oncologyObservation, deserializedOncologyObservation)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val oncologyObservation = OncologyObservation(
            identifier = listOf(
                Identifier(
                    system = CodeSystem.RONIN_TENANT.uri,
                    type = CodeableConcepts.RONIN_TENANT,
                    value = "tenantId"
                )
            ),
            status = ObservationStatus.FINAL,
            code = CodeableConcept(
                coding = listOf(
                    Coding(
                        system = Uri("http://acme-rehab.org"),
                        code = Code("alcohol-type"),
                        display = "Type of alcohol consumed"
                    )
                ),
                text = "Type of alcohol consumed"
            ),
            subject = Reference(
                reference = "Patient/example"
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(oncologyObservation)
        val expectedJson = """
            |{
            |  "resourceType" : "Observation",
            |  "identifier" : [ {
            |    "type" : {
            |      "coding" : [ {
            |        "system" : "http://projectronin.com/id/tenantId",
            |        "code" : "TID",
            |        "display" : "Ronin-specified Tenant Identifier"
            |      } ],
            |      "text" : "Tenant ID"
            |    },
            |    "system" : "http://projectronin.com/id/tenantId",
            |    "value" : "tenantId"
            |  } ],
            |  "status" : "final",
            |  "code" : {
            |    "coding" : [ {
            |      "system" : "http://acme-rehab.org",
            |      "code" : "alcohol-type",
            |      "display" : "Type of alcohol consumed"
            |    } ],
            |    "text" : "Type of alcohol consumed"
            |  },
            |  "subject" : {
            |    "reference" : "Patient/example"
            |  }
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "resourceType" : "Observation",
            |  "identifier" : [ {
            |    "type" : {
            |      "coding" : [ {
            |        "system" : "http://projectronin.com/id/tenantId",
            |        "code" : "TID",
            |        "display" : "Ronin-specified Tenant Identifier"
            |      } ],
            |      "text" : "Tenant ID"
            |    },
            |    "system" : "http://projectronin.com/id/tenantId",
            |    "value" : "tenantId"
            |  } ],
            |  "status" : "final",
            |  "code" : {
            |    "coding" : [ {
            |      "system" : "http://acme-rehab.org",
            |      "code" : "alcohol-type",
            |      "display" : "Type of alcohol consumed"
            |    } ],
            |    "text" : "Type of alcohol consumed"
            |  },
            |  "subject" : {
            |    "reference" : "Patient/example"
            |  }
            |}
        """.trimMargin()
        val observation = JacksonManager.objectMapper.readValue<OncologyObservation>(json)
        assertNull(observation.id)
        assertNull(observation.meta)
        assertNull(observation.implicitRules)
        assertNull(observation.language)
        assertNull(observation.text)
        assertEquals(listOf<Resource>(), observation.contained)
        assertEquals(listOf<Extension>(), observation.extension)
        assertEquals(listOf<Extension>(), observation.modifierExtension)
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
    fun `fails if no tenant identifier provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            OncologyObservation(
                identifier = listOf(
                    Identifier(
                        system = CodeSystem.MRN.uri,
                        type = CodeableConcepts.MRN,
                        value = "MRN"
                    ),
                ),
                status = ObservationStatus.FINAL,
                code = CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("http://acme-rehab.org"),
                            code = Code("alcohol-type"),
                            display = "Type of alcohol consumed"
                        )
                    )
                ),
                subject = Reference(
                    reference = "Patient/example"
                )
            )
        }
        assertEquals("Tenant identifier is required", exception.message)
    }

    @Test
    fun `cannot create dynamic value with bad type for effective time`() {
        val timePeriod = Period(start = DateTime("2019-04-01"), end = DateTime("2020-04-02"))
        val exception = assertThrows<IllegalArgumentException> {
            OncologyObservation(
                identifier = listOf(
                    Identifier(
                        system = CodeSystem.RONIN_TENANT.uri,
                        type = CodeableConcepts.RONIN_TENANT,
                        value = "tenantId"
                    )
                ),
                status = ObservationStatus.FINAL,
                code = CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("http://acme-rehab.org"),
                            code = Code("alcohol-type"),
                            display = "Type of alcohol consumed"
                        )
                    )
                ),
                subject = Reference(
                    reference = "Patient/example"
                ),
                effective = DynamicValue(DynamicValueType.PERIOD, timePeriod)
            )
        }
        assertEquals("Invalid dynamic type for Observation effective time", exception.message)
    }

    @Test
    fun `Validate Rule obs-3 - referenceRange must have at least a low or a high or text`() {
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
            "[obs-3](http://hl7.org/fhir/R4/observation.html#invs): Observation.referenceRange must have at least a low or a high or text",
            ex.message
        )

        val observationHasLow = Observation(
            status = ObservationStatus.ENTERED_IN_ERROR,
            code = CodeableConcept(text = "code"),
            subject = Reference(display = "Peter Chalmers"),
            value = DynamicValue(DynamicValueType.QUANTITY, quantity),
            referenceRange = listOf(ObservationReferenceRange(low = SimpleQuantity(value = 18.0)))
        )
        assertNull(observationHasLow.id)
        assertEquals("Peter Chalmers", observationHasLow.subject?.display)
        assertEquals(18.0, observationHasLow.referenceRange.first().low?.value)

        val observationHasHigh = Observation(
            status = ObservationStatus.REGISTERED,
            code = CodeableConcept(text = "code"),
            subject = Reference(display = "Peter Chalmers"),
            value = DynamicValue(DynamicValueType.QUANTITY, quantity),
            referenceRange = listOf(ObservationReferenceRange(high = SimpleQuantity(value = 11.0)))
        )
        assertNull(observationHasHigh.id)
        assertEquals("Peter Chalmers", observationHasHigh.subject?.display)
        assertEquals(11.0, observationHasHigh.referenceRange.first().high?.value)

        val observationHasText = Observation(
            status = ObservationStatus.PRELIMINARY,
            code = CodeableConcept(text = "code"),
            subject = Reference(display = "Peter Chalmers"),
            value = DynamicValue(DynamicValueType.QUANTITY, quantity),
            referenceRange = listOf(ObservationReferenceRange(text = "Range Boundary"))
        )
        assertNull(observationHasText.id)
        assertEquals("Peter Chalmers", observationHasText.subject?.display)
        assertEquals("Range Boundary", observationHasText.referenceRange.first().text)
    }

    @Test
    fun `Validate Rule obs-3 - component referenceRange must have at least a low or a high or text`() {
        val testCodeableConcept1 = CodeableConcept(coding = listOf(Coding(code = Code("code1"))))
        val testCodeableConcept2 = CodeableConcept(coding = listOf(Coding(code = Code("code2"))))
        val quantity = Quantity(
            value = 60.0,
            unit = "mL/min/1.73m2",
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )
        val referenceRangeBad = listOf(ObservationReferenceRange(age = Range(low = SimpleQuantity(value = 15.0))))
        val componentBad = listOf(
            ObservationComponent(
                code = testCodeableConcept1,
                value = DynamicValue(DynamicValueType.QUANTITY, quantity),
                referenceRange = referenceRangeBad
            )
        )
        val ex = assertThrows<IllegalArgumentException> {
            Observation(
                status = ObservationStatus.FINAL,
                code = testCodeableConcept2,
                subject = Reference(reference = "subject"),
                value = DynamicValue(DynamicValueType.QUANTITY, quantity),
                component = componentBad
            )
        }
        assertEquals(
            "[obs-3](http://hl7.org/fhir/R4/observation.html#invs): Observation.component.referenceRange must have at least a low or a high or text",
            ex.message
        )
    }

    @Test
    fun `Validate referenceRange succeeds with only a low`() {
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
    fun `Validate referenceRange succeeds with only a high`() {
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
    fun `Validate referenceRange succeeds with only text`() {
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
    fun `Validate Rule obs-6 - Observation dataAbsentReason SHALL only be present if value is not present`() {
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
            "[obs-6](http://hl7.org/fhir/R4/observation.html#invs): Observation.dataAbsentReason SHALL only be present if Observation.value[x] is not present",
            ex.message
        )

        val observation = Observation(
            status = ObservationStatus.CANCELLED,
            code = CodeableConcept(text = "code"),
            subject = Reference(display = "Peter Chalmers"),
            dataAbsentReason = CodeableConcept(text = "unable to reach vein"),
        )
        assertNull(observation.id)
        assertEquals("Peter Chalmers", observation.subject?.display)
        assertEquals("unable to reach vein", observation.dataAbsentReason?.text)
    }

    @Test
    fun `Validate Rule obs-7 - If Observation code is the same as an Observation component code then the Observation value SHALL NOT be present`() {
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
            "[obs-7](http://hl7.org/fhir/R4/observation.html#invs): If Observation.code is the same as an Observation.component.code then the Observation.value SHALL NOT be present",
            ex.message
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
}
