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
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.ContainedResource
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
        oncologyObservation.validate().alertIfErrors()
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
        oncologyObservation.validate().alertIfErrors()
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
            ).validate().alertIfErrors()
        }
        assertEquals("Tenant identifier is required", exception.message)
    }

    @Test
    fun `cannot create dynamic value with bad type for effective time`() {
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
                effective = DynamicValue(DynamicValueType.STRING, "bad")
            ).validate().alertIfErrors()
        }
        assertEquals(
            "Observation effective can only be one of the following data types: DateTime, Period, Timing, Instant",
            exception.message
        )
    }

    @Test
    fun `base validate() is inherited - dataAbsentReason as an example`() {
        val quantity = Quantity(
            value = 60.0,
            unit = "mL/min/1.73m2",
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )
        val ex = assertThrows<IllegalArgumentException> {
            OncologyObservation(
                identifier = listOf(
                    Identifier(
                        system = CodeSystem.RONIN_TENANT.uri,
                        type = CodeableConcepts.RONIN_TENANT,
                        value = "tenantId"
                    )
                ),
                status = ObservationStatus.CANCELLED,
                code = CodeableConcept(text = "code"),
                subject = Reference(display = "Peter Chalmers"),
                value = DynamicValue(DynamicValueType.QUANTITY, quantity),
                dataAbsentReason = CodeableConcept(text = "unable to reach vein")
            ).validate().alertIfErrors()
        }
        assertEquals(
            "dataAbsentReason SHALL only be present if value[x] is not present",
            ex.message
        )
    }

    @Test
    fun `base validate() is inherited - component dataAbsentReason as an example`() {
        val quantity = Quantity(
            value = 60.0,
            unit = "mL/min/1.73m2",
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )
        val ex = assertThrows<IllegalArgumentException> {
            OncologyObservation(
                identifier = listOf(
                    Identifier(
                        system = CodeSystem.RONIN_TENANT.uri,
                        type = CodeableConcepts.RONIN_TENANT,
                        value = "tenantId"
                    )
                ),
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
            ).validate().alertIfErrors()
        }
        assertEquals(
            "dataAbsentReason SHALL only be present if value[x] is not present",
            ex.message
        )
    }

    @Test
    fun `fails for multiple issues`() {
        val quantity = Quantity(
            value = 60.0,
            unit = "mL/min/1.73m2",
            system = Uri("http://unitsofmeasure.org"),
            code = Code("mL/min/{1.73_m2}")
        )
        val ex = assertThrows<IllegalArgumentException> {
            OncologyObservation(
                identifier = listOf(),
                status = ObservationStatus.CANCELLED,
                code = CodeableConcept(text = "code"),
                subject = Reference(display = "Peter Chalmers"),
                value = DynamicValue(DynamicValueType.QUANTITY, quantity),
                dataAbsentReason = CodeableConcept(text = "unable to reach vein")
            ).validate().alertIfErrors()
        }
        assertEquals(
            "Encountered multiple validation errors:\ndataAbsentReason SHALL only be present if value[x] is not present\nTenant identifier is required",
            ex.message
        )
    }
}
