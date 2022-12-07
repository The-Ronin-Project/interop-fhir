package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.AvailableTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4AvailableTimeValidatorTest {
    @Test
    fun `fails for invalid day of week`() {
        val exception = assertThrows<IllegalArgumentException> {
            val availableTime = AvailableTime(
                daysOfWeek = listOf(Code("not-a-day"))
            )
            R4AvailableTimeValidator.validate(availableTime).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'not-a-day' is outside of required value set @ AvailableTime.daysOfWeek",
            exception.message
        )
    }

    @Test
    fun `fails for multiple invalid days of week`() {
        val exception = assertThrows<IllegalArgumentException> {
            val availableTime = AvailableTime(
                daysOfWeek = listOf(Code("not-a-day"), Code("wed"), Code("sun"), Code("also-not-a-day"))
            )
            R4AvailableTimeValidator.validate(availableTime).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'not-a-day', 'also-not-a-day' are outside of required value set @ AvailableTime.daysOfWeek",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val availableTime = AvailableTime(
            availableStartTime = Time("08:00:00")
        )
        R4AvailableTimeValidator.validate(availableTime).alertIfErrors()
    }
}
