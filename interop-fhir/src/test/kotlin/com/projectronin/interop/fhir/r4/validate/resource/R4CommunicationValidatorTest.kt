package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.resource.Communication
import com.projectronin.interop.fhir.r4.valueset.EventStatus
import com.projectronin.interop.fhir.r4.valueset.RequestPriority
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4CommunicationValidatorTest {
    @Test
    fun `fails if no status`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                R4CommunicationValidator.validate(
                    Communication(
                        status = null,
                    ),
                ).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ Communication.status",
            exception.message,
        )
    }

    @Test
    fun `fails for invalid status`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                R4CommunicationValidator.validate(
                    Communication(
                        status = Code("invalid-status"),
                    ),
                ).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'invalid-status' is outside of required value set @ Communication.status",
            exception.message,
        )
    }

    @Test
    fun `fails for invalid priority`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                R4CommunicationValidator.validate(
                    Communication(
                        status = EventStatus.ON_HOLD.asCode(),
                        priority = Code("invalid-status"),
                    ),
                ).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'invalid-status' is outside of required value set @ Communication.priority",
            exception.message,
        )
    }

    @Test
    fun `validates successfully with valid priority`() {
        R4CommunicationValidator.validate(
            Communication(
                status = EventStatus.IN_PROGRESS.asCode(),
                priority = RequestPriority.STAT.asCode(),
            ),
        ).alertIfErrors()
    }

    @Test
    fun `validates successfully`() {
        R4CommunicationValidator.validate(
            Communication(
                status = EventStatus.NOT_DONE.asCode(),
            ),
        ).alertIfErrors()
    }
}
