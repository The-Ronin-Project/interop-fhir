package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Contact
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ContactValidatorTest {
    @Test
    fun `fails if gender is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val contact = Contact(
                gender = Code("unsupported-gender"),
                name = HumanName(text = FHIRString("name"))
            )
            R4ContactValidator.validate(contact).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-gender' is outside of required value set @ Contact.gender",
            exception.message
        )
    }

    @Test
    fun `fails without details`() {
        val exception = assertThrows<IllegalArgumentException> {
            val contact = Contact()
            R4ContactValidator.validate(contact).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CNTCT_001: contact SHALL at least contain a contact's details or a reference to an organization @ Contact",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val contact = Contact(
            name = HumanName(text = FHIRString("Jane Doe")),
            telecom = listOf(
                ContactPoint(
                    value = FHIRString("name@site.com"),
                    system = com.projectronin.interop.fhir.r4.valueset.ContactPointSystem.EMAIL.asCode()
                )
            ),
        )
        R4ContactValidator.validate(contact).alertIfErrors()
    }
}
