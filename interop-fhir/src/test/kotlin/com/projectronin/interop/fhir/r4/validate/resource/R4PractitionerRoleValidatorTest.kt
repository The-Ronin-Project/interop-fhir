package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.interop.fhir.r4.resource.AvailableTime
import com.projectronin.interop.fhir.r4.resource.NotAvailable
import com.projectronin.interop.fhir.r4.resource.PractitionerRole
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4PractitionerRoleValidatorTest {
    @Test
    fun `passes validation`() {
        val practitionerRole = PractitionerRole(
            identifier = listOf(Identifier(value = FHIRString("id")))
        )
        R4PractitionerRoleValidator.validate(practitionerRole).alertIfErrors()
    }
}

class R4AvailableTimeValidatorTest {
    @Test
    fun `fails for invalid day of week`() {
        val exception = assertThrows<IllegalArgumentException> {
            val availableTime = AvailableTime(
                daysOfWeek = listOf(Code("not-a-day"))
            )
            R4AvailableTimeValidator.validate(availableTime).alertIfErrors()
        }
        Assertions.assertEquals(
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
        Assertions.assertEquals(
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

class R4NotAvailableValidatorTest {
    @Test
    fun `fails if no description provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val notAvailable = NotAvailable(
                description = null
            )
            R4NotAvailableValidator.validate(notAvailable).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: description is a required element @ NotAvailable.description",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val notAvailable = NotAvailable(
            description = FHIRString("Vacation")
        )
        R4NotAvailableValidator.validate(notAvailable).alertIfErrors()
    }
}