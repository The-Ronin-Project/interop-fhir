package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.generators.datatypes.address
import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.coding
import com.projectronin.interop.fhir.generators.datatypes.contactPoint
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.name
import com.projectronin.interop.fhir.generators.primitives.date
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import com.projectronin.interop.fhir.r4.valueset.ContactPointSystem
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PractitionerGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val practitioner = practitioner {}
        assertNull(practitioner.id)
        assertNull(practitioner.meta)
        assertNull(practitioner.implicitRules)
        assertNull(practitioner.language)
        assertNull(practitioner.text)
        assertEquals(0, practitioner.contained.size)
        assertEquals(0, practitioner.extension.size)
        assertEquals(0, practitioner.modifierExtension.size)
        assertTrue(practitioner.identifier.isEmpty())
        assertNull(practitioner.active)
        assertEquals(1, practitioner.name.size)
        assertTrue(practitioner.name.first().given.first().toString().isNotEmpty())
        assertTrue(practitioner.name.first().family?.value.toString().isNotEmpty())
        assertEquals(0, practitioner.telecom.size)
        assertEquals(0, practitioner.address.size)
        assertNotNull(practitioner.gender)
        assertNull(practitioner.birthDate)
        assertEquals(0, practitioner.qualification.size)
        assertEquals(0, practitioner.communication.size)
    }

    @Test
    fun `function works with parameters`() {
        val practitioner = practitioner {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            active of FHIRBoolean(true)
            name of listOf(name { family of "Felt" })
            telecom of listOf(
                contactPoint {
                    system of ContactPointSystem.EMAIL.asCode()
                    value of "doctor.com"
                }
            )
            address of listOf(
                address {
                    city of "Kansas City"
                    state of "KS"
                }
            )
            // gender of "Very"
            birthDate of date { year of 1990 }
            qualification of listOf(
                qualification {
                    code of codeableConcept {
                        coding of listOf(
                            coding {
                                code of "codeA"
                                system of "systemA"
                            }
                        )
                        text of "textA"
                    }
                }
            )
            communication of listOf(
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
        }
        assertEquals("id", practitioner.id?.value)
        assertEquals(1, practitioner.identifier.size)
        assertTrue(practitioner.identifier.first().system?.value.toString().isNotEmpty())
        assertTrue(practitioner.identifier.first().value?.value.toString().isNotEmpty())
        assertEquals(true.asFHIR(), practitioner.active)
        assertEquals("Felt", practitioner.name.first().family?.value)
        assertEquals(Code("email"), practitioner.telecom.first().system)
        assertEquals(FHIRString("doctor.com"), practitioner.telecom.first().value)
        assertEquals(FHIRString("Kansas City"), practitioner.address.first().city)
        assertEquals(FHIRString("KS"), practitioner.address.first().state)
        assertNotNull(practitioner.gender)
        assertTrue(practitioner.birthDate?.value?.startsWith("1990")!!)
        assertEquals("systemA", practitioner.qualification.first().code?.coding?.first()?.system?.value)
        assertEquals("codeA", practitioner.qualification.first().code?.coding?.first()?.code?.value)
        assertEquals("textA", practitioner.qualification.first().code?.text?.value)
        assertEquals("systemB", practitioner.communication.first().coding.first().system?.value)
        assertEquals("codeB", practitioner.communication.first().coding.first().code?.value)
        assertEquals("textB", practitioner.communication.first().text?.value)
    }

    @Test
    fun `example use with serialization`() {
        // Create a practitioner with the details you need.
        val practitioner = practitioner {
            id of Id("id")
            identifier of listOf(
                identifier {}
            )
            name of listOf(name { family of "Felt" })
            gender of "Very"
            birthDate of date { year of 1990 }

            // Say your test require a qualification
            qualification of listOf(
                qualification {
                    code of codeableConcept {
                        coding of listOf(
                            coding {
                                code of "codeA"
                                system of "systemA"
                            }
                        )
                        text of "textA"
                    }
                }
            )

            // And active status
            active of FHIRBoolean(true)
        }

        // This object can be serialized to JSON to be injected into your workflow, all specified R4 attributes wil be generated
        val practitionerJSON = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(practitioner)

        // Uncomment to take a peek at the JSON
        // println(practitionerJSON)
        assertNotNull(practitionerJSON)
    }
}
