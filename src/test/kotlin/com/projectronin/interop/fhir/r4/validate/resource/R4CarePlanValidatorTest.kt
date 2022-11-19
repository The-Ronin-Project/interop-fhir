package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.resource.CarePlan
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4CarePlanValidatorTest {
    @Test
    fun `validates successfully`() {
        val carePlan = CarePlan(
            status = Code("active"),
            intent = Code("plan"),
            subject = Reference(reference = "XYZ123")
        )
        R4CarePlanValidator.validate(carePlan).alertIfErrors()
    }

    @Test
    fun `fails on null intent`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlan = CarePlan(
                status = Code("active"),
                intent = null,
                subject = Reference(reference = "XYZ123")
            )
            R4CarePlanValidator.validate(carePlan).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: intent is a required element @ CarePlan.intent",
            exception.message
        )
    }

    @Test
    fun `fails on null status`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlan = CarePlan(
                status = null,
                intent = Code("plan"),
                subject = Reference(reference = "XYZ123")
            )
            R4CarePlanValidator.validate(carePlan).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ CarePlan.status",
            exception.message
        )
    }

    @Test
    fun `fails on null subject`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlan = CarePlan(
                status = Code("active"),
                intent = Code("plan"),
                subject = null
            )
            R4CarePlanValidator.validate(carePlan).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: subject is a required element @ CarePlan.subject",
            exception.message
        )
    }

    @Test
    fun `fails on invalid code for intent`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlan = CarePlan(
                status = Code("active"),
                intent = Code("potato"),
                subject = Reference(reference = "XYZ123")
            )
            R4CarePlanValidator.validate(carePlan).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: intent is outside of required value set @ CarePlan.intent",
            exception.message
        )
    }

    @Test
    fun `fails on invalid code for status`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlan = CarePlan(
                status = Code("potato"),
                intent = Code("plan"),
                subject = Reference(reference = "XYZ123")
            )
            R4CarePlanValidator.validate(carePlan).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: status is outside of required value set @ CarePlan.status",
            exception.message
        )
    }
}
