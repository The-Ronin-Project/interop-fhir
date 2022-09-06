package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.SampledData
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4SampledDataValidatorTest {
    @Test
    fun `fails if no origin provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val sampledData = SampledData(
                origin = null,
                period = 3.0,
                dimensions = PositiveInt(5)
            )
            R4SampledDataValidator.validate(sampledData).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: origin is a required element @ SampledData.origin",
            exception.message
        )
    }

    @Test
    fun `fails if no period provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val sampledData = SampledData(
                origin = SimpleQuantity(value = 1.0),
                period = null,
                dimensions = PositiveInt(5)
            )
            R4SampledDataValidator.validate(sampledData).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: period is a required element @ SampledData.period",
            exception.message
        )
    }

    @Test
    fun `fails if no dimensions provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val sampledData = SampledData(
                origin = SimpleQuantity(value = 1.0),
                period = 3.0,
                dimensions = null
            )
            R4SampledDataValidator.validate(sampledData).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: dimensions is a required element @ SampledData.dimensions",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val sampledData = SampledData(
            origin = SimpleQuantity(value = 1.0),
            period = 3.0,
            dimensions = PositiveInt(5)
        )
        R4SampledDataValidator.validate(sampledData).alertIfErrors()
    }
}
