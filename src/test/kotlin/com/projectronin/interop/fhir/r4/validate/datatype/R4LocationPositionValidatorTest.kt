package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.LocationPosition
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4LocationPositionValidatorTest {
    @Test
    fun `fails if no longitude provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val position = LocationPosition(
                longitude = null,
                latitude = Decimal(66.077132)
            )
            R4LocationPositionValidator.validate(position).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: longitude is a required element @ LocationPosition.longitude",
            exception.message
        )
    }

    @Test
    fun `fails if no latitude provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val position = LocationPosition(
                longitude = Decimal(13.81531),
                latitude = null
            )
            R4LocationPositionValidator.validate(position).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: latitude is a required element @ LocationPosition.latitude",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val position = LocationPosition(
            longitude = Decimal(13.81531),
            latitude = Decimal(66.077132)
        )
        R4LocationPositionValidator.validate(position).alertIfErrors()
    }
}
