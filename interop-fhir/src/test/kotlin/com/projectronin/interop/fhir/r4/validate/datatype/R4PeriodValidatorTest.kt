package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4PeriodValidatorTest {
    @Test
    fun `fails if start has a higher value than end`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val period =
                    Period(
                        start = DateTime("2017-01-03T00:00:00.000Z"),
                        end = DateTime("2017-01-01T00:00:00.000Z"),
                    )
                R4PeriodValidator.validate(period).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_PRD_001: start SHALL have a lower value than end @ Period",
            exception.message,
        )
    }

    @Test
    fun `succeeds if start and end match`() {
        val period =
            Period(
                start = DateTime("2018"),
                end = DateTime("2018"),
            )
        R4PeriodValidator.validate(period).alertIfErrors()
    }

    @Test
    fun `handles start null values`() {
        val period =
            Period(
                start =
                    DateTime(
                        value = null,
                        extension =
                            listOf(
                                Extension(
                                    url = Uri("http://localhost/extension"),
                                    value = DynamicValue(DynamicValueType.STRING, FHIRString("012020")),
                                ),
                            ),
                    ),
                end = DateTime("2018"),
            )
        R4PeriodValidator.validate(period).alertIfErrors()
    }
}
