package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.r4.valueset.RequestIntent
import com.projectronin.interop.fhir.r4.valueset.RequestPriority
import com.projectronin.interop.fhir.r4.valueset.RequestStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ServiceRequestTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val serviceRequest = ServiceRequest(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("RoninServiceRequest"))
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
            instantiatesCanonical = listOf(Canonical("instantiates-canonical")),
            instantiatesUri = listOf(Uri("instantiates-uri")),
            basedOn = listOf(Reference(reference = FHIRString("CarePlan/1234"))),
            replaces = listOf(Reference(reference = FHIRString("ServiceRequest/4321"))),
            requisition = Identifier(value = FHIRString("requisition")),
            status = RequestStatus.ACTIVE.asCode(),
            intent = RequestIntent.ORDER.asCode(),
            category = listOf(CodeableConcept(text = FHIRString("category"))),
            priority = RequestPriority.ROUTINE.asCode(),
            doNotPerform = FHIRBoolean.FALSE,
            code = CodeableConcept(text = FHIRString("code")),
            orderDetail = listOf(CodeableConcept(text = FHIRString("order-detail"))),
            quantity = DynamicValue(DynamicValueType.QUANTITY, Quantity(value = Decimal(2.0))),
            subject = Reference(reference = FHIRString("Patient/1234")),
            encounter = Reference(reference = FHIRString("Encounter/1234")),
            occurrence = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2015-02-07T13:28:17-05:00")),
            asNeeded = DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.TRUE),
            authoredOn = DateTime("2017-01-01T00:00:00.000Z"),
            requester = Reference(reference = FHIRString("Practitioner/1234")),
            performerType = CodeableConcept(text = FHIRString("performer-type")),
            performer = listOf(Reference(reference = FHIRString("Practitioner/5678"))),
            locationCode = listOf(CodeableConcept(text = FHIRString("location-code"))),
            locationReference = listOf(Reference(reference = FHIRString("Location/1234"))),
            reasonCode = listOf(CodeableConcept(text = FHIRString("reason-code"))),
            reasonReference = listOf(Reference(reference = FHIRString("Condition/1234"))),
            insurance = listOf(Reference(reference = FHIRString("Coverage/1234"))),
            supportingInfo = listOf(Reference(reference = FHIRString("Observation/1234"))),
            specimen = listOf(Reference(reference = FHIRString("Specimen/1234"))),
            bodySite = listOf(CodeableConcept(text = FHIRString("body-site"))),
            note = listOf(Annotation(text = Markdown("note"))),
            patientInstruction = FHIRString("patient-instruction"),
            relevantHistory = listOf(Reference(reference = FHIRString("Provenance/1234")))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(serviceRequest)

        val expectedJson = """
            {
              "resourceType" : "ServiceRequest",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninServiceRequest" ]
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
              "instantiatesCanonical" : [ "instantiates-canonical" ],
              "instantiatesUri" : [ "instantiates-uri" ],
              "basedOn" : [ {
                "reference" : "CarePlan/1234"
              } ],
              "replaces" : [ {
                "reference" : "ServiceRequest/4321"
              } ],
              "requisition" : {
                "value" : "requisition"
              },
              "status" : "active",
              "intent" : "order",
              "category" : [ {
                "text" : "category"
              } ],
              "priority" : "routine",
              "doNotPerform" : false,
              "code" : {
                "text" : "code"
              },
              "orderDetail" : [ {
                "text" : "order-detail"
              } ],
              "quantityQuantity" : {
                "value" : 2.0
              },
              "subject" : {
                "reference" : "Patient/1234"
              },
              "encounter" : {
                "reference" : "Encounter/1234"
              },
              "occurrenceDateTime" : "2015-02-07T13:28:17-05:00",
              "asNeededBoolean" : true,
              "authoredOn" : "2017-01-01T00:00:00.000Z",
              "requester" : {
                "reference" : "Practitioner/1234"
              },
              "performerType" : {
                "text" : "performer-type"
              },
              "performer" : [ {
                "reference" : "Practitioner/5678"
              } ],
              "locationCode" : [ {
                "text" : "location-code"
              } ],
              "locationReference" : [ {
                "reference" : "Location/1234"
              } ],
              "reasonCode" : [ {
                "text" : "reason-code"
              } ],
              "reasonReference" : [ {
                "reference" : "Condition/1234"
              } ],
              "insurance" : [ {
                "reference" : "Coverage/1234"
              } ],
              "supportingInfo" : [ {
                "reference" : "Observation/1234"
              } ],
              "specimen" : [ {
                "reference" : "Specimen/1234"
              } ],
              "bodySite" : [ {
                "text" : "body-site"
              } ],
              "note" : [ {
                "text" : "note"
              } ],
              "patientInstruction" : "patient-instruction",
              "relevantHistory" : [ {
                "reference" : "Provenance/1234"
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedServiceRequest = JacksonManager.objectMapper.readValue<ServiceRequest>(json)
        assertEquals(serviceRequest, deserializedServiceRequest)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val serviceRequest = ServiceRequest(
            status = RequestStatus.COMPLETED.asCode(),
            intent = RequestIntent.DIRECTIVE.asCode(),
            subject = Reference(reference = FHIRString("Patient/5678"))
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(serviceRequest)

        val expectedJson = """
            {
              "resourceType" : "ServiceRequest",
              "status" : "completed",
              "intent" : "directive",
              "subject" : {
                "reference" : "Patient/5678"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "resourceType" : "ServiceRequest",
              "status" : "completed",
              "intent" : "directive",
              "subject" : {
                "reference" : "Patient/5678"
              }
              } ]
            }
        """.trimIndent()
        val serviceRequest = JacksonManager.objectMapper.readValue<ServiceRequest>(json)

        assertNull(serviceRequest.id)
        assertNull(serviceRequest.meta)
        assertNull(serviceRequest.implicitRules)
        assertNull(serviceRequest.language)
        assertNull(serviceRequest.text)
        assertEquals(listOf<Resource<Nothing>>(), serviceRequest.contained)
        assertEquals(listOf<Extension>(), serviceRequest.extension)
        assertEquals(listOf<Extension>(), serviceRequest.modifierExtension)
        assertEquals(listOf<Identifier>(), serviceRequest.identifier)
        assertEquals(listOf<Canonical>(), serviceRequest.instantiatesCanonical)
        assertEquals(listOf<Uri>(), serviceRequest.instantiatesUri)
        assertEquals(listOf<Reference>(), serviceRequest.basedOn)
        assertEquals(listOf<Reference>(), serviceRequest.replaces)
        assertNull(serviceRequest.requisition)
        assertEquals(RequestStatus.COMPLETED.asCode(), serviceRequest.status)
        assertEquals(RequestIntent.DIRECTIVE.asCode(), serviceRequest.intent)
        assertEquals(listOf<CodeableConcept>(), serviceRequest.category)
        assertNull(serviceRequest.priority)
        assertNull(serviceRequest.doNotPerform)
        assertNull(serviceRequest.code)
        assertEquals(listOf<CodeableConcept>(), serviceRequest.orderDetail)
        assertNull(serviceRequest.quantity)
        assertEquals(Reference(reference = FHIRString("Patient/5678")), serviceRequest.subject)
        assertNull(serviceRequest.encounter)
        assertNull(serviceRequest.occurrence)
        assertNull(serviceRequest.asNeeded)
        assertNull(serviceRequest.authoredOn)
        assertNull(serviceRequest.requester)
        assertNull(serviceRequest.performerType)
        assertEquals(listOf<Reference>(), serviceRequest.performer)
        assertEquals(listOf<CodeableConcept>(), serviceRequest.locationCode)
        assertEquals(listOf<Reference>(), serviceRequest.locationReference)
        assertEquals(listOf<CodeableConcept>(), serviceRequest.reasonCode)
        assertEquals(listOf<Reference>(), serviceRequest.reasonReference)
        assertEquals(listOf<Reference>(), serviceRequest.insurance)
        assertEquals(listOf<Reference>(), serviceRequest.supportingInfo)
        assertEquals(listOf<Reference>(), serviceRequest.specimen)
        assertEquals(listOf<CodeableConcept>(), serviceRequest.bodySite)
        assertEquals(listOf<Annotation>(), serviceRequest.note)
        assertNull(serviceRequest.text)
        assertEquals(listOf<Reference>(), serviceRequest.relevantHistory)
    }
}
