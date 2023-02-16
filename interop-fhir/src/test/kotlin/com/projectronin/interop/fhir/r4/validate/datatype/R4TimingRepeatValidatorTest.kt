package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.TimingRepeat
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.valueset.DayOfWeek
import com.projectronin.interop.fhir.r4.valueset.EventTiming
import com.projectronin.interop.fhir.r4.valueset.UnitOfTime
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4TimingRepeatValidatorTest {
    @Test
    fun `fails for durationUnit is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val timingRepeat = TimingRepeat(
                timeOfDay = listOf(Time("12:00:00")),
                durationUnit = Code("unsupported-durationUnit")
            )
            R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-durationUnit' is outside of required value set @ TimingRepeat.durationUnit",
            exception.message
        )
    }

    @Test
    fun `fails for periodUnit is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val timingRepeat = TimingRepeat(
                timeOfDay = listOf(Time("12:00:00")),
                periodUnit = Code("unsupported-periodUnit")
            )
            R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-periodUnit' is outside of required value set @ TimingRepeat.periodUnit",
            exception.message
        )
    }

    @Test
    fun `fails for invalid day of week`() {
        val exception = assertThrows<IllegalArgumentException> {
            val timingRepeat = TimingRepeat(
                timeOfDay = listOf(Time("12:00:00")),
                dayOfWeek = listOf(Code("not-a-day"))
            )
            R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'not-a-day' is outside of required value set @ TimingRepeat.dayOfWeek",
            exception.message
        )
    }

    @Test
    fun `fails for multiple invalid days of week`() {
        val exception = assertThrows<IllegalArgumentException> {
            val timingRepeat = TimingRepeat(
                timeOfDay = listOf(Time("12:00:00")),
                dayOfWeek = listOf(Code("not-a-day"), Code("also-not-a-day"))
            )
            R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'not-a-day', 'also-not-a-day' are outside of required value set @ TimingRepeat.dayOfWeek",
            exception.message
        )
    }

    @Test
    fun `fails for invalid when`() {
        val exception = assertThrows<IllegalArgumentException> {
            val timingRepeat = TimingRepeat(
                `when` = listOf(Code("not-a-timing"))
            )
            R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'not-a-timing' is outside of required value set @ TimingRepeat.when",
            exception.message
        )
    }

    @Test
    fun `fails for multiple invalid whens`() {
        val exception = assertThrows<IllegalArgumentException> {
            val timingRepeat = TimingRepeat(
                `when` = listOf(Code("not-a-timing"), Code("also-not-a-timing"))
            )
            R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'not-a-timing', 'also-not-a-timing' are outside of required value set @ TimingRepeat.when",
            exception.message
        )
    }

    @Test
    fun `fails for unsupported bounds dynamic value type`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val timingRepeat = TimingRepeat(bounds = DynamicValue(DynamicValueType.INTEGER, FHIRInteger(1)))
                R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: bounds can only be one of the following: Duration, Range, Period @ TimingRepeat.bounds",
            exception.message
        )
    }

    @Test
    fun `fails for duration and no durating units`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val timingRepeat = TimingRepeat(duration = Decimal(2.0))
                R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TMRPT_001: if there's a duration, there needs to be duration units @ TimingRepeat",
            exception.message
        )
    }

    @Test
    fun `fails for period and no period units`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val timingRepeat = TimingRepeat(period = Decimal(2.0))
                R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TMRPT_002: if there's a period, there needs to be period units @ TimingRepeat",
            exception.message
        )
    }

    @Test
    fun `fails for negative duration`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val timingRepeat = TimingRepeat(
                    duration = Decimal(-2.0),
                    durationUnit = UnitOfTime.HOUR.asCode()
                )
                R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TMRPT_003: duration SHALL be a non-negative value @ TimingRepeat.duration",
            exception.message
        )
    }

    @Test
    fun `fails for negative period`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val timingRepeat = TimingRepeat(
                    period = Decimal(-2.0),
                    periodUnit = UnitOfTime.DAY.asCode()
                )
                R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TMRPT_004: period SHALL be a non-negative value @ TimingRepeat.period",
            exception.message
        )
    }

    @Test
    fun `fails for period max but no period`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val timingRepeat = TimingRepeat(periodMax = Decimal(14.0))
                R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TMRPT_005: If there's a periodMax, there must be a period @ TimingRepeat",
            exception.message
        )
    }

    @Test
    fun `fails for duration max but no duration`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val timingRepeat = TimingRepeat(durationMax = Decimal(21.0))
                R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TMRPT_006: If there's a durationMax, there must be a duration @ TimingRepeat",
            exception.message
        )
    }

    @Test
    fun `fails for count max but no count`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val timingRepeat = TimingRepeat(countMax = PositiveInt(100))
                R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TMRPT_007: If there's a countMax, there must be a count @ TimingRepeat",
            exception.message
        )
    }

    @Test
    fun `fails for offset and no when`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val timingRepeat = TimingRepeat(offset = UnsignedInt(12))
                R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TMRPT_008: If there's an offset, there must be a when (and not C, CM, CD, CV) @ TimingRepeat",
            exception.message
        )
    }

    @Test
    fun `fails for offset and an unsupported when`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val timingRepeat = TimingRepeat(
                    offset = UnsignedInt(12),
                    `when` = listOf(EventTiming.MEAL.asCode())
                )
                R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TMRPT_008: If there's an offset, there must be a when (and not C, CM, CD, CV) @ TimingRepeat",
            exception.message
        )
    }

    @Test
    fun `fails for time of day and when`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val timingRepeat = TimingRepeat(
                    timeOfDay = listOf(Time("08:00:00")),
                    `when` = listOf(EventTiming.UPON_WAKING.asCode())
                )
                R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TMRPT_009: If there's a timeOfDay, there cannot be a when, or vice versa @ TimingRepeat",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val timingRepeat = TimingRepeat(
            bounds = DynamicValue(
                DynamicValueType.DURATION,
                Duration(value = Decimal(3.0), code = Code("a"), system = CodeSystem.UCUM.uri)
            ),
            count = PositiveInt(5),
            countMax = PositiveInt(10),
            duration = Decimal(14.0),
            durationMax = Decimal(30.0),
            durationUnit = UnitOfTime.DAY.asCode(),
            frequency = PositiveInt(2),
            frequencyMax = PositiveInt(10),
            period = Decimal(6.0),
            periodMax = Decimal(8.0),
            periodUnit = UnitOfTime.HOUR.asCode(),
            dayOfWeek = listOf(DayOfWeek.MONDAY.asCode()),
            `when` = listOf(EventTiming.UPON_WAKING.asCode()),
            offset = UnsignedInt(12)
        )
        R4TimingRepeatValidator.validate(timingRepeat).alertIfErrors()
    }
}
