package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.LocationHoursOfOperation
import com.projectronin.interop.fhir.r4.datatype.LocationPosition
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.DayOfWeek
import com.projectronin.interop.fhir.r4.valueset.LocationMode
import com.projectronin.interop.fhir.r4.valueset.LocationStatus
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class LocationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val type = listOf(CodeableConcept(text = "Diagnostic", coding = listOf(Coding(code = Code("DX"), system = Uri(value = "http://terminology.hl7.org/ValueSet/v3-ServiceDeliveryLocationRoleType")))))
        val physicalType = CodeableConcept(text = "Room", coding = listOf(Coding(code = Code("ro"), system = Uri(value = "http://terminology.hl7.org/CodeSystem/location-physical-type"))))
        val location = Location(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("https://www.hl7.org/fhir/location")),
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
            mode = LocationMode.INSTANCE,
            status = LocationStatus.ACTIVE,
            name = "My Office",
            alias = listOf("Guest Room"),
            description = "Sun Room",
            type = type,
            telecom = listOf(ContactPoint(value = "8675309")),
            address = Address(country = "USA"),
            physicalType = physicalType,
            position = LocationPosition(longitude = Decimal(13.81531), latitude = Decimal(66.077132)),
            hoursOfOperation = listOf(LocationHoursOfOperation(daysOfWeek = listOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY), allDay = true)),
            availabilityExceptions = "Call for details",
            endpoint = listOf(Reference(reference = "Endpoint/4321"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(location)

        val expectedJson = """
            |{
            |  "resourceType" : "Location",
            |  "id" : "12345",
            |  "meta" : {
            |    "profile" : [ "https://www.hl7.org/fhir/location" ]
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
            |    "value" : "id"
            |  } ],
            |  "status" : "active",
            |  "name" : "My Office",
            |  "alias" : [ "Guest Room" ],
            |  "description" : "Sun Room",
            |  "mode" : "instance",
            |  "type" : [ {
            |    "coding" : [ {
            |      "system" : "http://terminology.hl7.org/ValueSet/v3-ServiceDeliveryLocationRoleType",
            |      "code" : "DX"
            |    } ],
            |    "text" : "Diagnostic"
            |  } ],
            |  "telecom" : [ {
            |    "value" : "8675309"
            |  } ],
            |  "address" : {
            |    "country" : "USA"
            |  },
            |  "physicalType" : {
            |    "coding" : [ {
            |      "system" : "http://terminology.hl7.org/CodeSystem/location-physical-type",
            |      "code" : "ro"
            |    } ],
            |    "text" : "Room"
            |  },
            |  "position" : {
            |    "longitude" : 13.81531,
            |    "latitude" : 66.077132
            |  },
            |  "hoursOfOperation" : [ {
            |    "daysOfWeek" : [ "sat", "sun" ],
            |    "allDay" : true
            |  } ],
            |  "availabilityExceptions" : "Call for details",
            |  "endpoint" : [ {
            |    "reference" : "Endpoint/4321"
            |  } ]
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedLocation = objectMapper.readValue<Location>(json)
        assertEquals(location, deserializedLocation)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val location = Location(status = LocationStatus.ACTIVE)
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(location)

        val expectedJson = """
            |{
            |  "resourceType" : "Location",
            |  "status" : "active"
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "resourceType" : "Location",
            |  "mode" : "instance"
            |}""".trimMargin()
        val location = objectMapper.readValue<Location>(json)

        assertNull(location.id)
        assertNull(location.meta)
        assertNull(location.implicitRules)
        assertNull(location.language)
        assertNull(location.text)
        assertEquals(listOf<Resource>(), location.contained)
        assertEquals(listOf<Extension>(), location.extension)
        assertEquals(listOf<Extension>(), location.modifierExtension)
        assertEquals(listOf<Identifier>(), location.identifier)
        assertEquals(LocationMode.INSTANCE, location.mode)
        assertNull(location.status)
        assertNull(location.name)
        assertEquals(listOf<String>(), location.alias)
        assertEquals(listOf<ContactPoint>(), location.telecom)
        assertNull(location.address)
    }
}