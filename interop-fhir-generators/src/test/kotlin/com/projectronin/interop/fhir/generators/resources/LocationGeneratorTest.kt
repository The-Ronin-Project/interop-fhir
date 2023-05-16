package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.generators.datatypes.address
import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.coding
import com.projectronin.interop.fhir.generators.datatypes.contactPoint
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import com.projectronin.interop.fhir.r4.valueset.ContactPointSystem
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LocationGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val location = location {}
        assertNull(location.id)
        assertNull(location.meta)
        assertNull(location.implicitRules)
        assertNull(location.language)
        assertNull(location.text)
        assertEquals(0, location.contained.size)
        assertEquals(0, location.extension.size)
        assertEquals(0, location.modifierExtension.size)
        assertTrue(location.identifier.isEmpty())
        assertNull(location.status)
        assertNull(location.operationalStatus)
        assertNotNull(location.name)
        assertEquals(0, location.alias.size)
        assertNull(location.description)
        assertNull(location.mode)
        assertEquals(0, location.type.size)
        assertEquals(0, location.telecom.size)
        assertNull(location.address)
        assertNull(location.physicalType)
        assertNull(location.position)
        assertNull(location.managingOrganization)
        assertNull(location.partOf)
        assertEquals(0, location.hoursOfOperation.size)
        assertNull(location.availabilityExceptions)
        assertEquals(0, location.endpoint.size)
    }

    @Test
    fun `function works with parameters`() {
        val location = location {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            status of "Active"
            operationalStatus of coding {
                code of "codeZ"
                system of "systemZ"
                display of "displayZ"
            }
            name of "Prairie"
            alias of listOf("A", "B", "C")
            description of "Flat"
            mode of Code("codeA")
            type of listOf(
                codeableConcept {
                    coding of listOf(
                        coding {
                            code of "codeB"
                            system of "systemB"
                        }
                    )
                    text of "textB"
                }
            )
            telecom of listOf(
                contactPoint {
                    system of ContactPointSystem.PHONE.asCode()
                    value of "123-456-7890"
                    use of "official"
                },
                contactPoint {
                    system of ContactPointSystem.EMAIL.asCode()
                    value of "clinic.com"
                }
            )
            address of address {}
            physicalType of codeableConcept {
                coding of listOf(
                    coding {
                        code of "codeC"
                        system of "systemC"
                    }
                )
                text of "textC"
            }
            position of locationPosition {
                longitude of 1.45
                latitude of 3.78
                altitude of 6.02
            }
            managingOrganization of reference("Organization", "123")
            partOf of reference("Location", "999")
            hoursOfOperation of listOf(
                locationHoursOfOperation {
                    daysOfWeek of listOf(Code("Mon"), Code("Tue"), Code("Wed"))
                    allDay of true
                    openingTime of "9:00"
                    closingTime of "14:00"
                },
                locationHoursOfOperation {
                    daysOfWeek of listOf(Code("Thu"), Code("Fri"))
                    allDay of false
                },
                locationHoursOfOperation {}
            )
            availabilityExceptions of "Closed Sat, Sun"
            endpoint of listOf(
                reference("Endpoint", "123")
            )
        }
        assertEquals("id", location.id?.value)
        assertEquals(1, location.identifier.size)
        assertEquals("Active", location.status?.value)
        assertEquals("systemZ", location.operationalStatus!!.system?.value)
        assertEquals("codeZ", location.operationalStatus!!.code?.value)
        assertEquals("displayZ", location.operationalStatus?.display?.value)
        assertEquals("Prairie", location.name?.value)
        assertEquals("C", location.alias[2].value)
        assertEquals("Flat", location.description?.value)
        assertEquals("codeA", location.mode?.value)
        assertEquals("systemB", location.type.first().coding.first().system?.value)
        assertEquals("codeB", location.type.first().coding.first().code?.value)
        assertEquals("textB", location.type.first().text?.value)
        assertEquals(ContactPointSystem.PHONE.code, location.telecom[0].system?.value)
        assertEquals(ContactPointSystem.EMAIL.code, location.telecom[1].system?.value)
        assertNotNull(location.address?.city)
        assertEquals("systemC", location.physicalType?.coding!!.first().system?.value)
        assertEquals("codeC", location.physicalType?.coding!!.first().code?.value)
        assertEquals("textC", location.physicalType?.text?.value)
        assertEquals(1.45, location.position?.longitude?.value)
        assertEquals(3.78, location.position?.latitude?.value)
        assertEquals(6.02, location.position?.altitude?.value)
        assertEquals("Organization/123", location.managingOrganization?.reference?.value)
        assertEquals("Location/999", location.partOf?.reference?.value)
        assertEquals(
            listOf(Code("Mon"), Code("Tue"), Code("Wed")),
            location.hoursOfOperation[0].daysOfWeek
        )
        assertEquals(true.asFHIR(), location.hoursOfOperation[0].allDay)
        assertEquals("9:00", location.hoursOfOperation[0].openingTime?.value)
        assertEquals("14:00", location.hoursOfOperation[0].closingTime?.value)
        assertEquals(
            listOf(Code("Thu"), Code("Fri")),
            location.hoursOfOperation[1].daysOfWeek
        )
        assertEquals(false.asFHIR(), location.hoursOfOperation[1].allDay)
        assertNull(location.hoursOfOperation[1].openingTime)
        assertNull(location.hoursOfOperation[1].closingTime)
        assertNotNull(location.hoursOfOperation[2])
        assertNotNull(location.hoursOfOperation[2].daysOfWeek)
        assertEquals("Closed Sat, Sun", location.availabilityExceptions?.value)
        assertEquals("Endpoint/123", location.endpoint.first().reference?.value)
    }

    @Test
    fun `example use with serialization`() {
        // Create a location with the details you need.
        val location = location {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            name of "Prairie"

            // Say your test requires a type
            type of listOf(
                codeableConcept {
                    coding of listOf(
                        coding {
                            code of "codeA"
                            system of "systemA"
                        }
                    )
                    text of "textA"
                }
            )

            // And status
            status of "Active"
        }

        // This object can be serialized to JSON to be injected into your workflow, all specified R4 attributes wil be generated
        val locationJSON = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(location)

        // Uncomment to take a peek at the JSON
        // println(locationJSON)
        assertNotNull(locationJSON)
    }
}
