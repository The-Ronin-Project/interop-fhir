package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Sort
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.valueset.SortDirection
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4SortValidatorTest {
    @Test
    fun `fails for no path provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val sort = Sort(
                path = null,
                direction = SortDirection.ASCENDING.asCode()
            )
            R4SortValidator.validate(sort).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: path is a required element @ Sort.path",
            exception.message
        )
    }

    @Test
    fun `fails for no direction provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val sort = Sort(
                path = "path",
                direction = null
            )
            R4SortValidator.validate(sort).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: direction is a required element @ Sort.direction",
            exception.message
        )
    }

    @Test
    fun `fails for direction is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val sort = Sort(
                path = "path",
                direction = Code("unsupported-direction")
            )
            R4SortValidator.validate(sort).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: direction is outside of required value set @ Sort.direction",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val sort = Sort(
            path = "path",
            direction = SortDirection.ASCENDING.asCode()
        )
        R4SortValidator.validate(sort).alertIfErrors()
    }
}
