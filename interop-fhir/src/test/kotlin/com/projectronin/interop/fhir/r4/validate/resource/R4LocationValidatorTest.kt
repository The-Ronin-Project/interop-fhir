package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.resource.Location
import com.projectronin.interop.fhir.r4.resource.LocationHoursOfOperation
import com.projectronin.interop.fhir.r4.valueset.DayOfWeek
import com.projectronin.interop.fhir.r4.valueset.LocationStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4LocationValidatorTest {
    @Test
    fun `status is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val location = Location(
                status = Code("unsupported-status")
            )
            R4LocationValidator.validate(location).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-status' is outside of required value set @ Location.status",
            exception.message
        )
    }

    @Test
    fun `mode is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val location = Location(
                mode = Code("unsupported-mode")
            )
            R4LocationValidator.validate(location).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-mode' is outside of required value set @ Location.mode",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val location = Location(status = LocationStatus.ACTIVE.asCode())
        R4LocationValidator.validate(location).alertIfErrors()
    }
}

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
                "ERROR INV_VALUE_SET: 'not-a-day' is outside of required value set @ LocationHoursOfOperation.daysOfWeek",
            exception.message
        )
    }

    @Test
    fun `fails for multiple invalid days of week`() {
        val exception = assertThrows<IllegalArgumentException> {
            val hoursOfOperation = LocationHoursOfOperation(
                daysOfWeek = listOf(Code("not-a-day"), Code("wed"), Code("sun"), Code("also-not-a-day"))
            )
            R4LocationHoursOfOperationValidator.validate(hoursOfOperation).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'not-a-day', 'also-not-a-day' are outside of required value set @ LocationHoursOfOperation.daysOfWeek",
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
