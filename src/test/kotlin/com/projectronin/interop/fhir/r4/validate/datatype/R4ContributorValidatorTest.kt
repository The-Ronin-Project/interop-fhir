package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Contributor
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.valueset.ContributorType
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ContributorValidatorTest {
    @Test
    fun `fails for no type`() {
        val exception = assertThrows<IllegalArgumentException> {
            val contributor = Contributor(
                type = null,
                name = "Josh Smith"
            )
            R4ContributorValidator.validate(contributor).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: type is a required element @ Contributor.type",
            exception.message
        )
    }

    @Test
    fun `fails for type is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val contributor = Contributor(
                type = Code("unsupported-type"),
                name = "Josh Smith"
            )
            R4ContributorValidator.validate(contributor).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: type is outside of required value set @ Contributor.type",
            exception.message
        )
    }

    @Test
    fun `fails for no name`() {
        val exception = assertThrows<IllegalArgumentException> {
            val contributor = Contributor(
                type = ContributorType.AUTHOR.asCode(),
                name = null
            )
            R4ContributorValidator.validate(contributor).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: name is a required element @ Contributor.name",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val contributor = Contributor(
            type = ContributorType.AUTHOR.asCode(),
            name = "Josh Smith"
        )
        R4ContributorValidator.validate(contributor).alertIfErrors()
    }
}
