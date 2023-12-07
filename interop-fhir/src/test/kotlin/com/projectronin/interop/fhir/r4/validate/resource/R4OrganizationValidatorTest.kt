package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Organization
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4OrganizationValidatorTest {
    @Test
    fun `identifier and name not provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val organization =
                    Organization(
                        active = FHIRBoolean.TRUE,
                        type =
                            listOf(
                                CodeableConcept(
                                    coding =
                                        listOf(
                                            Coding(
                                                system = Uri("http://terminology.hl7.org/CodeSystem/organization-type"),
                                                code = Code("dept"),
                                                display = FHIRString("Hospital Department"),
                                            ),
                                        ),
                                ),
                            ),
                    )
                R4OrganizationValidator.validate(organization).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_ORG_001: The organization SHALL at least have a name or an identifier, and possibly more than one @ Organization",
            exception.message,
        )
    }

    @Test
    fun `address of an organization can never be of use home`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val organization =
                    Organization(
                        identifier = listOf(Identifier(value = FHIRString("id"))),
                        active = FHIRBoolean.TRUE,
                        address =
                            listOf(
                                Address(
                                    country = FHIRString("USA"),
                                    use = Code("home"),
                                ),
                            ),
                    )
                R4OrganizationValidator.validate(organization).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_ORG_002: An address of an organization can never be of use 'home' @ Organization.address[0]",
            exception.message,
        )
    }

    @Test
    fun `telecom of an organization can never be of use home`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val organization =
                    Organization(
                        identifier = listOf(Identifier(value = FHIRString("id"))),
                        active = FHIRBoolean.TRUE,
                        telecom =
                            listOf(
                                ContactPoint(
                                    value = FHIRString("8675309"),
                                    system = com.projectronin.interop.fhir.r4.valueset.ContactPointSystem.PHONE.asCode(),
                                    use = Code("home"),
                                ),
                            ),
                    )
                R4OrganizationValidator.validate(organization).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_ORG_003: The telecom of an organization can never be of use 'home' @ Organization.telecom[0]",
            exception.message,
        )
    }

    @Test
    fun `success when identifier is provided`() {
        val organization =
            Organization(
                identifier = listOf(Identifier(value = FHIRString("id"))),
                active = FHIRBoolean.TRUE,
                telecom =
                    listOf(
                        ContactPoint(
                            value = FHIRString("8675309"),
                            system = com.projectronin.interop.fhir.r4.valueset.ContactPointSystem.PHONE.asCode(),
                        ),
                    ),
            )
        R4OrganizationValidator.validate(organization).alertIfErrors()
    }

    @Test
    fun `success when name is provided`() {
        val organization =
            Organization(
                active = FHIRBoolean.TRUE,
                name = FHIRString("Jane Doe"),
                telecom =
                    listOf(
                        ContactPoint(
                            value = FHIRString("8675309"),
                            system = com.projectronin.interop.fhir.r4.valueset.ContactPointSystem.PHONE.asCode(),
                        ),
                    ),
            )
        R4OrganizationValidator.validate(organization).alertIfErrors()
    }
}
