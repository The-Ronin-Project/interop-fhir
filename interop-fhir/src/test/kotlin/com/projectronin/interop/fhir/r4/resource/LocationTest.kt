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
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.ContactPointSystem
import com.projectronin.interop.fhir.r4.valueset.DayOfWeek
import com.projectronin.interop.fhir.r4.valueset.LocationMode
import com.projectronin.interop.fhir.r4.valueset.LocationStatus
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class LocationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val type = listOf(
            CodeableConcept(
                text = FHIRString("Diagnostic"),
                coding = listOf(
                    Coding(
                        code = Code("DX"),
                        system = Uri(value = "http://terminology.hl7.org/ValueSet/v3-ServiceDeliveryLocationRoleType")
                    )
                )
            )
        )
        val physicalType = CodeableConcept(
            text = FHIRString("Room"),
            coding = listOf(
                Coding(
                    code = Code("ro"),
                    system = Uri(value = "http://terminology.hl7.org/CodeSystem/location-physical-type")
                )
            )
        )
        val location = Location(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("https://www.hl7.org/fhir/location")),
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
            mode = LocationMode.INSTANCE.asCode(),
            status = LocationStatus.ACTIVE.asCode(),
            name = FHIRString("My Office"),
            alias = listOf(FHIRString("Guest Room")),
            description = FHIRString("Sun Room"),
            type = type,
            telecom = listOf(ContactPoint(value = FHIRString("8675309"), system = ContactPointSystem.PHONE.asCode())),
            address = Address(country = FHIRString("USA")),
            physicalType = physicalType,
            position = LocationPosition(longitude = Decimal(13.81531), latitude = Decimal(66.077132)),
            hoursOfOperation = listOf(
                LocationHoursOfOperation(
                    daysOfWeek = listOf(
                        DayOfWeek.SATURDAY.asCode(),
                        DayOfWeek.SUNDAY.asCode()
                    ),
                    allDay = FHIRBoolean.TRUE
                )
            ),
            availabilityExceptions = FHIRString("Call for details"),
            endpoint = listOf(Reference(reference = FHIRString("Endpoint/4321")))
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
            |    "system" : "phone",
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
        val location = Location(status = LocationStatus.ACTIVE.asCode())
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
        assertEquals(listOf<Resource<Nothing>>(), location.contained)
        assertEquals(listOf<Extension>(), location.extension)
        assertEquals(listOf<Extension>(), location.modifierExtension)
        assertEquals(listOf<Identifier>(), location.identifier)
        assertEquals(LocationMode.INSTANCE.asCode(), location.mode)
        assertNull(location.status)
        assertNull(location.name)
        assertEquals(listOf<String>(), location.alias)
        assertEquals(listOf<ContactPoint>(), location.telecom)
        assertNull(location.address)
    }
}

class LocationHoursOfOperationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val hoursOfOperation = LocationHoursOfOperation(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            daysOfWeek = listOf(DayOfWeek.SATURDAY.asCode(), DayOfWeek.SUNDAY.asCode()),
            allDay = FHIRBoolean.TRUE,
            openingTime = Time("06:30:00"),
            closingTime = Time("18:00:00")
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(hoursOfOperation)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "daysOfWeek" : [ "sat", "sun" ],
            |  "allDay" : true,
            |  "openingTime" : "06:30:00",
            |  "closingTime" : "18:00:00"
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedHoursOfOperation = objectMapper.readValue<LocationHoursOfOperation>(json)
        assertEquals(hoursOfOperation, deserializedHoursOfOperation)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val hoursOfOperation = LocationHoursOfOperation(id = FHIRString("12345"))
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(hoursOfOperation)

        val expectedJson = """
            |{
            |  "id" : "12345"
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "id" : "12345"
            |}""".trimMargin()
        val hoursOfOperation = objectMapper.readValue<LocationHoursOfOperation>(json)

        assertEquals(FHIRString("12345"), hoursOfOperation.id)
        assertEquals(listOf<Extension>(), hoursOfOperation.extension)
        assertEquals(listOf<Extension>(), hoursOfOperation.modifierExtension)
        assertEquals(listOf<DayOfWeek>(), hoursOfOperation.daysOfWeek)
        assertNull(hoursOfOperation.openingTime)
        assertNull(hoursOfOperation.closingTime)
    }
}

class LocationPositionTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val position = LocationPosition(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            longitude = Decimal(13.81531),
            latitude = Decimal(66.077132),
            altitude = Decimal(17.0)
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(position)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "longitude" : 13.81531,
            |  "latitude" : 66.077132,
            |  "altitude" : 17.0
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedPosition = objectMapper.readValue<LocationPosition>(json)
        assertEquals(position, deserializedPosition)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val position = LocationPosition(
            longitude = Decimal(13.81531),
            latitude = Decimal(66.077132)
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(position)

        val expectedJson = """
            |{
            |  "longitude" : 13.81531,
            |  "latitude" : 66.077132
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "longitude" : 13.81531,
            |  "latitude" : 66.077132
            |}""".trimMargin()
        val position = objectMapper.readValue<LocationPosition>(json)

        assertNull(position.id)
        assertEquals(listOf<Extension>(), position.extension)
        assertNull(position.altitude)
    }
}
