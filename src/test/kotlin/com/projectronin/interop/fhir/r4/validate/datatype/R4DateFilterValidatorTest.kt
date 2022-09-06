package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.DateFilter
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4DateFilterValidatorTest {
    @Test
    fun `fails for both path and searchParam provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val dateFilter = DateFilter(path = "path", searchParam = "search")
                R4DateFilterValidator.validate(dateFilter).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_DTFILT_001: Either a path or a searchParam must be provided, but not both @ DateFilter",
            exception.message
        )
    }

    @Test
    fun `fails for neither path nor searchParam provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val dateFilter = DateFilter(id = "filter")
                R4DateFilterValidator.validate(dateFilter).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_DTFILT_001: Either a path or a searchParam must be provided, but not both @ DateFilter",
            exception.message
        )
    }

    @Test
    fun `fails for unsupported dynamic value type`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val dateFilter = DateFilter(path = "path", value = DynamicValue(DynamicValueType.INTEGER, 1))
                R4DateFilterValidator.validate(dateFilter).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: value can only be one of the following: DateTime, Period, Duration @ DateFilter.value",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val dateFilter = DateFilter(
            path = "date-filter-path",
            value = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2021-10-31"))
        )
        R4DateFilterValidator.validate(dateFilter).alertIfErrors()
    }
}
