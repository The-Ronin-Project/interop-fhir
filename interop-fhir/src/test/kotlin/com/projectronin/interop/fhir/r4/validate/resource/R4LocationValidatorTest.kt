package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.resource.Location
import com.projectronin.interop.fhir.r4.valueset.LocationStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4LocationValidatorTest {
    @Test
    fun `status is outside of required value set`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val location =
                    Location(
                        status = Code("unsupported-status"),
                    )
                R4LocationValidator.validate(location).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-status' is outside of required value set @ Location.status",
            exception.message,
        )
    }

    @Test
    fun `mode is outside of required value set`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val location =
                    Location(
                        mode = Code("unsupported-mode"),
                    )
                R4LocationValidator.validate(location).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-mode' is outside of required value set @ Location.mode",
            exception.message,
        )
    }

    @Test
    fun `validates successfully`() {
        val location = Location(status = LocationStatus.ACTIVE.asCode())
        R4LocationValidator.validate(location).alertIfErrors()
    }
}
