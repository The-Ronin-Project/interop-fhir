package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.LocationHoursOfOperation
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.valueset.DayOfWeek
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4LocationHoursOfOperationValidatorTest {
    @Test
    fun `fails for invalid day of week`() {
        val exception = assertThrows<IllegalArgumentException> {
            val hoursOfOperation = LocationHoursOfOperation(
                daysOfWeek = listOf(Code("not-a-day"))
            )
            R4LocationHoursOfOperationValidator.validate(hoursOfOperation).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: daysOfWeek is outside of required value set @ LocationHoursOfOperation.daysOfWeek",
            exception.message
        )
    }

    @Test
    fun `fails for multiple invalid days of week`() {
        val exception = assertThrows<IllegalArgumentException> {
            val hoursOfOperation = LocationHoursOfOperation(
                daysOfWeek = listOf(Code("not-a-day"), Code("also-not-a-day"))
            )
            R4LocationHoursOfOperationValidator.validate(hoursOfOperation).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: daysOfWeek is outside of required value set @ LocationHoursOfOperation.daysOfWeek",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val hoursOfOperation = LocationHoursOfOperation(
            daysOfWeek = listOf(DayOfWeek.FRIDAY.asCode())
        )
        R4LocationHoursOfOperationValidator.validate(hoursOfOperation).alertIfErrors()
    }
}
