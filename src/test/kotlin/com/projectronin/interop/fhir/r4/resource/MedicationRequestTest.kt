package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Dosage
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.medication.DispenseRequest
import com.projectronin.interop.fhir.r4.datatype.medication.Substitution
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.MedicationRequestIntent
import com.projectronin.interop.fhir.r4.valueset.MedicationRequestStatus
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class MedicationRequestTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val medicationRequest = MedicationRequest(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("RoninMedicationRequest")),
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
            status = MedicationRequestStatus.CANCELLED.asCode(),
            statusReason = CodeableConcept(text = "statusReason"),
            intent = MedicationRequestIntent.PROPOSAL.asCode(),
            category = listOf(CodeableConcept(text = "category")),
            priority = Code("priority"),
            doNotPerform = false,
            reported = DynamicValue(DynamicValueType.BOOLEAN, true),
            medication = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = "medication")),
            subject = Reference(reference = "Patient/1234"),
            encounter = Reference(reference = "Encounter/1234"),
            supportingInformation = listOf(Reference(reference = "Condition/1234")),
            authoredOn = DateTime("2022-11-03"),
            requester = Reference(reference = "Practitioner/1234"),
            performer = Reference(reference = "Practitioner/5678"),
            performerType = CodeableConcept(text = "performer type"),
            recorder = Reference(reference = "Practitioner/3456"),
            reasonCode = listOf(CodeableConcept(text = "reason")),
            reasonReference = listOf(Reference(reference = "Condition/5678")),
            instantiatesCanonical = listOf(Canonical("canonical")),
            instantiatesUri = listOf(Uri("uri")),
            basedOn = listOf(Reference(reference = "CarePlan/1234")),
            groupIdentifier = Identifier(value = "group"),
            courseOfTherapyType = CodeableConcept(text = "therapy"),
            insurance = listOf(Reference(reference = "Coverage/1234")),
            note = listOf(Annotation(text = Markdown("note"))),
            dosageInformation = listOf(Dosage(text = "dosage")),
            dispenseRequest = DispenseRequest(numberOfRepeatsAllowed = UnsignedInt(2)),
            substitution = Substitution(allowed = DynamicValue(DynamicValueType.BOOLEAN, true)),
            priorPrescription = Reference(reference = "MedicationRequest/1234"),
            detectedIssue = listOf(Reference(reference = "DetectedIssue/1234")),
            eventHistory = listOf(Reference(reference = "Provenance/1234"))
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
              "dosageInformation" : [ {
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
            medication = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = "medication")),
            subject = Reference(reference = "Patient/1234")
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
            DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = "medication")),
            medicationRequest.medication
        )
        assertEquals(Reference(reference = "Patient/1234"), medicationRequest.subject)
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
        assertEquals(listOf<Dosage>(), medicationRequest.dosageInformation)
        assertNull(medicationRequest.dispenseRequest)
        assertNull(medicationRequest.substitution)
        assertNull(medicationRequest.priorPrescription)
        assertEquals(listOf<Reference>(), medicationRequest.detectedIssue)
        assertEquals(listOf<Reference>(), medicationRequest.eventHistory)
    }
}
