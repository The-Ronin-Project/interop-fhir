package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Dosage
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.MedicationRequestIntent
import com.projectronin.interop.fhir.r4.valueset.MedicationRequestStatus
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class MedicationRequestTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val medicationRequest = MedicationRequest(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("RoninMedicationRequest"))
            ),
            implicitRules = Uri("implicit-rules"),
            language = Code("en-US"),
            text = Narrative(
                status = NarrativeStatus.GENERATED.asCode(),
                div = FHIRString("div")
            ),
            contained = listOf(Location(id = Id("1234"), name = FHIRString("Contained Location"))),
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
            status = MedicationRequestStatus.CANCELLED.asCode(),
            statusReason = CodeableConcept(text = FHIRString("statusReason")),
            intent = MedicationRequestIntent.PROPOSAL.asCode(),
            category = listOf(CodeableConcept(text = FHIRString("category"))),
            priority = Code("priority"),
            doNotPerform = FHIRBoolean.FALSE,
            reported = DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.TRUE),
            medication = DynamicValue(
                DynamicValueType.CODEABLE_CONCEPT,
                CodeableConcept(text = FHIRString("medication"))
            ),
            subject = Reference(reference = FHIRString("Patient/1234")),
            encounter = Reference(reference = FHIRString("Encounter/1234")),
            supportingInformation = listOf(Reference(reference = FHIRString("Condition/1234"))),
            authoredOn = DateTime("2022-11-03"),
            requester = Reference(reference = FHIRString("Practitioner/1234")),
            performer = Reference(reference = FHIRString("Practitioner/5678")),
            performerType = CodeableConcept(text = FHIRString("performer type")),
            recorder = Reference(reference = FHIRString("Practitioner/3456")),
            reasonCode = listOf(CodeableConcept(text = FHIRString("reason"))),
            reasonReference = listOf(Reference(reference = FHIRString("Condition/5678"))),
            instantiatesCanonical = listOf(Canonical("canonical")),
            instantiatesUri = listOf(Uri("uri")),
            basedOn = listOf(Reference(reference = FHIRString("CarePlan/1234"))),
            groupIdentifier = Identifier(value = FHIRString("group")),
            courseOfTherapyType = CodeableConcept(text = FHIRString("therapy")),
            insurance = listOf(Reference(reference = FHIRString("Coverage/1234"))),
            note = listOf(Annotation(text = Markdown("note"))),
            dosageInstruction = listOf(Dosage(text = FHIRString("dosage"))),
            dispenseRequest = DispenseRequest(numberOfRepeatsAllowed = UnsignedInt(2)),
            substitution = Substitution(allowed = DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.TRUE)),
            priorPrescription = Reference(reference = FHIRString("MedicationRequest/1234")),
            detectedIssue = listOf(Reference(reference = FHIRString("DetectedIssue/1234"))),
            eventHistory = listOf(Reference(reference = FHIRString("Provenance/1234")))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(medicationRequest)

        val expectedJson = """
            {
              "resourceType" : "MedicationRequest",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninMedicationRequest" ]
              },
              "implicitRules" : "implicit-rules",
              "language" : "en-US",
              "text" : {
                "status" : "generated",
                "div" : "div"
              },
              "contained" : [ {
                "resourceType" : "Location",
                "id" : "1234",
                "name" : "Contained Location"
              } ],
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
              "status" : "cancelled",
              "statusReason" : {
                "text" : "statusReason"
              },
              "intent" : "proposal",
              "category" : [ {
                "text" : "category"
              } ],
              "priority" : "priority",
              "doNotPerform" : false,
              "reportedBoolean" : true,
              "medicationCodeableConcept" : {
                "text" : "medication"
              },
              "subject" : {
                "reference" : "Patient/1234"
              },
              "encounter" : {
                "reference" : "Encounter/1234"
              },
              "supportingInformation" : [ {
                "reference" : "Condition/1234"
              } ],
              "authoredOn" : "2022-11-03",
              "requester" : {
                "reference" : "Practitioner/1234"
              },
              "performer" : {
                "reference" : "Practitioner/5678"
              },
              "performerType" : {
                "text" : "performer type"
              },
              "recorder" : {
                "reference" : "Practitioner/3456"
              },
              "reasonCode" : [ {
                "text" : "reason"
              } ],
              "reasonReference" : [ {
                "reference" : "Condition/5678"
              } ],
              "instantiatesCanonical" : [ "canonical" ],
              "instantiatesUri" : [ "uri" ],
              "basedOn" : [ {
                "reference" : "CarePlan/1234"
              } ],
              "groupIdentifier" : {
                "value" : "group"
              },
              "courseOfTherapyType" : {
                "text" : "therapy"
              },
              "insurance" : [ {
                "reference" : "Coverage/1234"
              } ],
              "note" : [ {
                "text" : "note"
              } ],
              "dosageInstruction" : [ {
                "text" : "dosage"
              } ],
              "dispenseRequest" : {
                "numberOfRepeatsAllowed" : 2
              },
              "substitution" : {
                "allowedBoolean" : true
              },
              "priorPrescription" : {
                "reference" : "MedicationRequest/1234"
              },
              "detectedIssue" : [ {
                "reference" : "DetectedIssue/1234"
              } ],
              "eventHistory" : [ {
                "reference" : "Provenance/1234"
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedMedicationRequest = JacksonManager.objectMapper.readValue<MedicationRequest>(json)
        assertEquals(medicationRequest, deserializedMedicationRequest)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val medicationRequest = MedicationRequest(
            status = MedicationRequestStatus.COMPLETED.asCode(),
            intent = MedicationRequestIntent.OPTION.asCode(),
            medication = DynamicValue(
                DynamicValueType.CODEABLE_CONCEPT,
                CodeableConcept(text = FHIRString("medication"))
            ),
            subject = Reference(reference = FHIRString("Patient/1234"))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(medicationRequest)

        val expectedJson = """
            {
              "resourceType" : "MedicationRequest",
              "status" : "completed",
              "intent" : "option",
              "medicationCodeableConcept" : {
                "text" : "medication"
              },
              "subject" : {
                "reference" : "Patient/1234"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "resourceType" : "MedicationRequest",
              "status" : "completed",
              "intent" : "option",
              "medicationCodeableConcept" : {
                "text" : "medication"
              },
              "subject" : {
                "reference" : "Patient/1234"
              }
            }
        """.trimIndent()
        val medicationRequest = JacksonManager.objectMapper.readValue<MedicationRequest>(json)

        assertNull(medicationRequest.id)
        assertNull(medicationRequest.meta)
        assertNull(medicationRequest.implicitRules)
        assertNull(medicationRequest.language)
        assertNull(medicationRequest.text)
        assertEquals(listOf<Resource<Nothing>>(), medicationRequest.contained)
        assertEquals(listOf<Extension>(), medicationRequest.extension)
        assertEquals(listOf<Extension>(), medicationRequest.modifierExtension)
        assertEquals(listOf<Identifier>(), medicationRequest.identifier)
        assertEquals(MedicationRequestStatus.COMPLETED.asCode(), medicationRequest.status)
        assertNull(medicationRequest.statusReason)
        assertEquals(MedicationRequestIntent.OPTION.asCode(), medicationRequest.intent)
        assertEquals(listOf<CodeableConcept>(), medicationRequest.category)
        assertNull(medicationRequest.priority)
        assertNull(medicationRequest.doNotPerform)
        assertNull(medicationRequest.reported)
        assertEquals(
            DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = FHIRString("medication"))),
            medicationRequest.medication
        )
        assertEquals(Reference(reference = FHIRString("Patient/1234")), medicationRequest.subject)
        assertNull(medicationRequest.encounter)
        assertEquals(listOf<Reference>(), medicationRequest.supportingInformation)
        assertNull(medicationRequest.authoredOn)
        assertNull(medicationRequest.requester)
        assertNull(medicationRequest.performer)
        assertNull(medicationRequest.performerType)
        assertNull(medicationRequest.recorder)
        assertEquals(listOf<CodeableConcept>(), medicationRequest.reasonCode)
        assertEquals(listOf<Reference>(), medicationRequest.reasonReference)
        assertEquals(listOf<Canonical>(), medicationRequest.instantiatesCanonical)
        assertEquals(listOf<Uri>(), medicationRequest.instantiatesUri)
        assertEquals(listOf<Reference>(), medicationRequest.basedOn)
        assertNull(medicationRequest.groupIdentifier)
        assertNull(medicationRequest.courseOfTherapyType)
        assertEquals(listOf<Reference>(), medicationRequest.insurance)
        assertEquals(listOf<Annotation>(), medicationRequest.note)
        assertEquals(listOf<Dosage>(), medicationRequest.dosageInstruction)
        assertNull(medicationRequest.dispenseRequest)
        assertNull(medicationRequest.substitution)
        assertNull(medicationRequest.priorPrescription)
        assertEquals(listOf<Reference>(), medicationRequest.detectedIssue)
        assertEquals(listOf<Reference>(), medicationRequest.eventHistory)
    }

    @Test
    fun `can deserialize MedicationRequest with contained Medication`() {
        // This is a real example from Cerner Code Sandbox, but this is technically the post-transform, but our models should still handle it.
        val json =
            """{"resourceType":"MedicationRequest","id":"ejh3j95h-306923901","meta":{"versionId":"0","lastUpdated":"2019-12-26T20:37:08Z","source":"https://objectstorage.us-phoenix-1.oraclecloud.com/n/idoll6i6jmjd/b/dev-data-lake-bronze/o/raw_data_response/tenant_id=ejh3j95h/transaction_id/75fc98f0-ed43-457f-8143-42e2809168d2","profile":["http://projectronin.io/fhir/StructureDefinition/ronin-medicationRequest"]},"text":{"status":"generated","div":"<div xmlns=\"http://www.w3.org/1999/xhtml\"><p><b>Medication Request</b></p><p><b>Status</b>: Stopped</p><p><b>Intent</b>: Order</p><p><b>Medication</b>: insulin regular (human) IV additive 100 units [1 units/hr] + sodium chloride 0.9% drip 100 mL</p><p><b>Dosage Instructions</b>: 1 mL/hr, IV</p><p><b>Patient</b>: Wolf, Big</p><p><b>Authored On</b>: Dec 26, 2019  8:34 P.M. UTC</p></div>"},"contained":[{"resourceType":"Medication","id":"25023091","code":{"text":"insulin regular (human) IV additive 100 units [1 units/hr] + sodium chloride 0.9% drip 100 mL"},"ingredient":[{"itemCodeableConcept":{"coding":[{"system":"http://www.nlm.nih.gov/research/umls/rxnorm","code":"311034","display":"Regular Insulin, Human 100 UNT/ML Injectable Solution","userSelected":false},{"system":"https://fhir.cerner.com/ec2458f2-1e24-41c8-b71b-0e701af7583d/synonym","code":"273286231","display":"insulin regular (human) IV additive","userSelected":true}],"text":"insulin regular (human) IV additive"},"strength":{"numerator":{"value":100.0,"unit":"units","system":"http://unitsofmeasure.org","code":"U"},"denominator":{"value":100.0,"unit":"mL","system":"http://unitsofmeasure.org","code":"mL"}}},{"itemCodeableConcept":{"coding":[{"system":"https://fhir.cerner.com/ec2458f2-1e24-41c8-b71b-0e701af7583d/synonym","code":"273249901","display":"sodium chloride 0.9% drip","userSelected":true}],"text":"sodium chloride 0.9% drip"},"strength":{"numerator":{"value":100.0,"unit":"mL","system":"http://unitsofmeasure.org","code":"mL"},"denominator":{"value":100.0,"unit":"mL","system":"http://unitsofmeasure.org","code":"mL"}}}]}],"extension":[{"url":"http://electronichealth.se/fhir/StructureDefinition/NLLPrescriptionFormat","valueCoding":{"system":"http://electronichealth.se/fhir/ValueSet/prescription-format","code":"ELECTRONIC","display":"Electronic"}},{"url":"http://electronichealth.se/fhir/StructureDefinition/NLLRegistrationBasis","valueCoding":{"system":"http://ehalsomyndigheten.se/fhir/ValueSet/registration-basis-codes","code":"ELECTRONIC","display":"Electronic"}},{"url":"https://fhir-ehr.cerner.com/r4/StructureDefinition/pharmacy-verification-status","valueCodeableConcept":{"text":"Needs pharmacy verification"}}],"identifier":[{"type":{"coding":[{"system":"http://projectronin.com/id/fhir","code":"FHIR ID","display":"FHIR Identifier"}],"text":"FHIR Identifier"},"system":"http://projectronin.com/id/fhir","value":"306923901"},{"type":{"coding":[{"system":"http://projectronin.com/id/tenantId","code":"TID","display":"Ronin-specified Tenant Identifier"}],"text":"Ronin-specified Tenant Identifier"},"system":"http://projectronin.com/id/tenantId","value":"ejh3j95h"},{"type":{"coding":[{"system":"http://projectronin.com/id/dataAuthorityId","code":"DAID","display":"Data Authority Identifier"}],"text":"Data Authority Identifier"},"system":"http://projectronin.com/id/dataAuthorityId","value":"EHR Data Authority"}],"status":"stopped","intent":"order","category":[{"coding":[{"system":"http://terminology.hl7.org/CodeSystem/medicationrequest-category","code":"inpatient","display":"Inpatient","userSelected":false}],"text":"Inpatient"}],"reportedBoolean":false,"medicationReference":{"reference":"#25023091","display":"insulin regular (human) IV additive 100 units [1 units/hr] + sodium chloride 0.9% drip 100 mL"},"subject":{"reference":"Patient/ejh3j95h-12724070","type":"Patient","_type":{"extension":[{"url":"http://projectronin.io/fhir/StructureDefinition/Extension/ronin-dataAuthorityIdentifier","valueIdentifier":{"type":{"coding":[{"system":"http://projectronin.com/id/dataAuthorityId","code":"DAID","display":"Data Authority Identifier"}],"text":"Data Authority Identifier"},"system":"http://projectronin.com/id/dataAuthorityId","value":"EHR Data Authority"}}]},"display":"Wolf, Big"},"encounter":{"reference":"Encounter/ejh3j95h-97939530","type":"Encounter","_type":{"extension":[{"url":"http://projectronin.io/fhir/StructureDefinition/Extension/ronin-dataAuthorityIdentifier","valueIdentifier":{"type":{"coding":[{"system":"http://projectronin.com/id/dataAuthorityId","code":"DAID","display":"Data Authority Identifier"}],"text":"Data Authority Identifier"},"system":"http://projectronin.com/id/dataAuthorityId","value":"EHR Data Authority"}}]}},"authoredOn":"2019-12-26T20:34:30Z","requester":{"reference":"Practitioner/ejh3j95h-4122622","type":"Practitioner","_type":{"extension":[{"url":"http://projectronin.io/fhir/StructureDefinition/Extension/ronin-dataAuthorityIdentifier","valueIdentifier":{"type":{"coding":[{"system":"http://projectronin.com/id/dataAuthorityId","code":"DAID","display":"Data Authority Identifier"}],"text":"Data Authority Identifier"},"system":"http://projectronin.com/id/dataAuthorityId","value":"EHR Data Authority"}}]},"display":"Cerner Test, Physician - Hospitalist Cerner"},"dosageInstruction":[{"extension":[{"url":"https://fhir-ehr.cerner.com/r4/StructureDefinition/clinical-instruction","valueString":"Total Volume (mL): 100, IV, 1 mL/hr, Start Date: 12/26/19 14:34:00 CST, Titration range: 1 - 50 units/hr, Populate Charting Weight From Order"}],"text":"1 mL/hr, IV","patientInstruction":"1 Milliliters/hour Intravenous. Refills: 0.","timing":{"repeat":{"boundsPeriod":{"start":"2019-12-26T20:34:00Z"},"duration":100.0,"durationUnit":"h"}},"route":{"coding":[{"system":"https://fhir.cerner.com/ec2458f2-1e24-41c8-b71b-0e701af7583d/codeSet/4001","code":"318170","display":"IV","userSelected":true},{"system":"http://snomed.info/sct","code":"47625008","display":"Intravenous route (qualifier value)","userSelected":false}],"text":"IV"},"doseAndRate":[{"doseQuantity":{"value":100.0,"unit":"mL","system":"http://unitsofmeasure.org","code":"mL"},"rateQuantity":{"value":1.0,"unit":"mL/hr","system":"http://unitsofmeasure.org","code":"mL/h"}}]}],"dispenseRequest":{"extension":[{"url":"http://electronichealth.se/fhir/StructureDefinition/NLLDosePackaging","valueBoolean":false}],"validityPeriod":{"start":"2019-12-26T20:34:30Z"}}}"""
        val appointment = JacksonManager.objectMapper.readValue<MedicationRequest>(json)

        assertEquals(1, appointment.contained.size)
        val contained1 = appointment.contained[0]
        assertInstanceOf(Medication::class.java, contained1)
        assertEquals("Medication", contained1.resourceType)
        assertEquals("25023091", contained1.id!!.value)
        assertEquals(2, (contained1 as Medication).ingredient.size)
    }
}

class DispenseRequestTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val dispenseRequest = DispenseRequest(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            initialFill = InitialFill(
                id = FHIRString("67890"),
                extension = listOf(
                    Extension(
                        url = Uri("http://localhost/extension"),
                        value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                    )
                ),
                quantity = SimpleQuantity(value = Decimal(23.0)),
                duration = Duration(value = Decimal(5.5))
            ),
            dispenseInterval = Duration(value = Decimal(1.0)),
            validityPeriod = Period(start = DateTime("2022-11-03")),
            numberOfRepeatsAllowed = UnsignedInt(3),
            quantity = SimpleQuantity(value = Decimal(36.0)),
            expectedSupplyDuration = Duration(value = Decimal(14.0)),
            performer = Reference(reference = FHIRString("Practitioner/13579"))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dispenseRequest)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "initialFill" : {
            |    "id" : "67890",
            |    "extension" : [ {
            |      "url" : "http://localhost/extension",
            |      "valueString" : "Value"
            |    } ],
            |    "quantity" : {
            |      "value" : 23.0
            |    },
            |    "duration" : {
            |      "value" : 5.5
            |    }
            |  },
            |  "dispenseInterval" : {
            |    "value" : 1.0
            |  },
            |  "validityPeriod" : {
            |    "start" : "2022-11-03"
            |  },
            |  "numberOfRepeatsAllowed" : 3,
            |  "quantity" : {
            |    "value" : 36.0
            |  },
            |  "expectedSupplyDuration" : {
            |    "value" : 14.0
            |  },
            |  "performer" : {
            |    "reference" : "Practitioner/13579"
            |  }
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedDispenseRequest = JacksonManager.objectMapper.readValue<DispenseRequest>(json)
        assertEquals(dispenseRequest, deserializedDispenseRequest)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val dispenseRequest = DispenseRequest(
            quantity = SimpleQuantity(value = Decimal(36.0))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dispenseRequest)

        val expectedJson = """
            |{
            |  "quantity" : {
            |    "value" : 36.0
            |  }
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "numberOfRepeatsAllowed" : 3
            |}
        """.trimMargin()
        val dispenseRequest = JacksonManager.objectMapper.readValue<DispenseRequest>(json)

        assertNull(dispenseRequest.id)
        assertEquals(listOf<Extension>(), dispenseRequest.extension)
        assertNull(dispenseRequest.initialFill)
        assertNull(dispenseRequest.dispenseInterval)
        assertNull(dispenseRequest.validityPeriod)
        assertEquals(UnsignedInt(3), dispenseRequest.numberOfRepeatsAllowed)
        assertNull(dispenseRequest.quantity)
        assertNull(dispenseRequest.expectedSupplyDuration)
        assertNull(dispenseRequest.performer)
    }
}

class SubstitutionTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val substitution = Substitution(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            allowed = DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.TRUE),
            reason = CodeableConcept(text = FHIRString("reason"))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(substitution)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "allowedBoolean" : true,
            |  "reason" : {
            |    "text" : "reason"
            |  }
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedSubstitution = JacksonManager.objectMapper.readValue<Substitution>(json)
        assertEquals(substitution, deserializedSubstitution)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val substitution = Substitution(
            allowed = DynamicValue(
                DynamicValueType.CODEABLE_CONCEPT,
                CodeableConcept(
                    text = FHIRString("allowed")
                )
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(substitution)

        val expectedJson = """
            |{
            |  "allowedCodeableConcept" : {
            |    "text" : "allowed"
            |  }
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "allowedBoolean" : true
            |}
        """.trimMargin()
        val substitution = JacksonManager.objectMapper.readValue<Substitution>(json)

        assertNull(substitution.id)
        assertEquals(listOf<Extension>(), substitution.extension)
        assertEquals(DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.TRUE), substitution.allowed)
        assertNull(substitution.reason)
    }
}
