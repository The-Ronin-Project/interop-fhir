package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.dateTime
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class TimingGeneratorTest {
    @Test
    fun `function works with default`() {
        val timing = timing {}
        assertNull(timing.id)
        assertEquals(emptyList<Extension>(), timing.extension)
        assertEquals(emptyList<Extension>(), timing.modifierExtension)
        assertEquals(emptyList<DateTime>(), timing.event)
        assertNull(timing.repeat)
        assertNull(timing.code)
    }

    @Test
    fun `function works with parameters`() {
        val timing = timing {
            event of listOf(
                dateTime { year of 1990 },
                dateTime { year of 1993 }
            )
            repeat of timingRepeat {}
            code of codeableConcept { text of "code" }
        }
        assertEquals(2, timing.event.size)
        assertTrue(timing.event[0].value?.startsWith("1990")!!)
        assertTrue(timing.event[1].value?.startsWith("1993")!!)
        assertEquals("code", timing.code?.text?.value)
    }
}

class TimingRepeatGeneratorTest {
    @Test
    fun `function works with default`() {
        val repeat = timingRepeat {}
        assertNull(repeat.id)
        assertEquals(emptyList<Extension>(), repeat.extension)
        assertNull(repeat.bounds)
        assertNull(repeat.count)
        assertNull(repeat.countMax)
        assertNull(repeat.duration)
        assertNull(repeat.durationMax)
        assertNull(repeat.durationUnit)
        assertNull(repeat.frequency)
        assertNull(repeat.frequencyMax)
        assertNull(repeat.period)
        assertNull(repeat.periodMax)
        assertNull(repeat.periodUnit)
        assertEquals(emptyList<Extension>(), repeat.dayOfWeek)
        assertEquals(emptyList<Extension>(), repeat.timeOfDay)
        assertEquals(emptyList<Extension>(), repeat.`when`)
        assertNull(repeat.offset)
    }

    @Test
    fun `function works with parameters`() {
        val repeat = timingRepeat {
            bounds of DynamicValues.period(
                period {
                    start of dateTime { year of 1990 }
                    end of dateTime { year of 1993 }
                }
            )
            count of PositiveInt(1)
            countMax of PositiveInt(3)
            duration of Decimal(BigDecimal(1.23))
            durationMax of Decimal(BigDecimal(2.46))
            durationUnit of Code("min")
            frequency of PositiveInt(2)
            frequencyMax of PositiveInt(6)
            period of Decimal(BigDecimal(1.23))
            periodMax of Decimal(BigDecimal(2.46))
            periodUnit of Code("h")
            dayOfWeek of listOf(
                Code("mon"),
                Code("wed"),
                Code("fri")
            )
            timeOfDay of listOf(
                Time("08:00"),
                Time("06:00")
            )
            `when` of listOf(
                Code("CM"),
                Code("CV")
            )
            offset of UnsignedInt(4)
        }
        val actualBounds = repeat.bounds?.value as Period
        assertTrue(actualBounds.start?.value?.startsWith("1990") == true)
        assertTrue(actualBounds.end?.value?.startsWith("1993") == true)
        assertEquals(1, repeat.count?.value)
        assertEquals(3, repeat.countMax?.value)
        assertEquals(BigDecimal(1.23), repeat.duration?.value)
        assertEquals(BigDecimal(2.46), repeat.durationMax?.value)
        assertEquals("min", repeat.durationUnit?.value)
        assertEquals(2, repeat.frequency?.value)
        assertEquals(6, repeat.frequencyMax?.value)
        assertEquals(BigDecimal(1.23), repeat.period?.value)
        assertEquals(BigDecimal(2.46), repeat.periodMax?.value)
        assertEquals("h", repeat.periodUnit?.value)
        assertEquals(3, repeat.dayOfWeek.size)
        assertEquals("mon", repeat.dayOfWeek[0].value)
        assertEquals("wed", repeat.dayOfWeek[1].value)
        assertEquals("fri", repeat.dayOfWeek[2].value)
        assertEquals(2, repeat.timeOfDay.size)
        assertEquals("08:00", repeat.timeOfDay[0].value)
        assertEquals("06:00", repeat.timeOfDay[1].value)
        assertEquals(2, repeat.`when`.size)
        assertEquals("CM", repeat.`when`[0].value)
        assertEquals("CV", repeat.`when`[1].value)
        assertEquals(4, repeat.offset?.value)
    }
}
