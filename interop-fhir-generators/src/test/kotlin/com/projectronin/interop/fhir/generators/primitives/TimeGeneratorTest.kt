package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TimeGeneratorTest {
    @Test
    fun `function works with default`() {
        val time = time {}
        assertNotNull(time)
        assertTrue(time!!.value!!.matches(Regex("\\d{1,2}:\\d{2}")))
    }

    @Test
    fun `function works with parameters and defaults`() {
        val timeA = time {
            hour of 1
            minute of 52
        }
        assertEquals("01:52", timeA!!.value)

        val timeB = time {
            hour of 17
            minute of 52
        }
        assertEquals("17:52", timeB!!.value)

        val timeC = time {
            hour of 17
        }
        timeC!!
        assertTrue(timeC.value!!.matches(Regex("\\d{2}:\\d{2}")))
        assertTrue(timeC.value!!.startsWith("17:"))

        val timeD = time {
            minute of 52
        }
        timeD!!
        assertTrue(timeD.value!!.matches(Regex("\\d{2}:\\d{2}")))
        assertTrue(timeD.value!!.endsWith(":52"))
    }

    @Test
    fun `supports infix setter for Time`() {
        val generator = TimeGenerator()
        generator of Time("11:22")
        val time = generator.generate()
        assertEquals("11:22", time?.value)
    }

    @Test
    fun `pass with good inputs for AM time`() {
        val timeB = TimeGenerator()
        timeB of "9:23"
        assertEquals("9:23", timeB.generate()?.value)

        val timeC = TimeGenerator()
        timeC of "09:23"
        assertEquals("09:23", timeC.generate()?.value)
    }

    @Test
    fun `pass with good inputs for PM time`() {
        val timeE = TimeGenerator()
        timeE of "21:23"
        assertEquals("21:23", timeE.generate()?.value)
    }

    @Test
    fun `pass with good inputs for noon`() {
        val timeD = TimeGenerator()
        timeD of "12:30"
        assertEquals("12:30", timeD.generate()?.value)
    }

    @Test
    fun `pass with good inputs for midnight`() {
        val timeB = TimeGenerator()
        timeB of "0:00"
        assertEquals("0:00", timeB.generate()?.value)

        val timeC = TimeGenerator()
        timeC of "00:00"
        assertEquals("00:00", timeC.generate()?.value)
    }

    @Test
    fun `fail with bad inputs for time`() {
        val generator = TimeGenerator()

        val exception0 = assertThrows<IllegalArgumentException> { generator of "9:" }
        assertEquals("9: is not valid", exception0.message)

        val exception1 = assertThrows<IllegalArgumentException> { generator of "9:0" }
        assertEquals("9:0 is not valid", exception1.message)

        val exception2 = assertThrows<IllegalArgumentException> { generator of ":30" }
        assertEquals(":30 is not valid", exception2.message)

        val exception3 = assertThrows<IllegalArgumentException> { generator of "any" }
        assertEquals("any is not valid", exception3.message)

        val exception4 = assertThrows<IllegalArgumentException> { generator of "10 A.M." }
        assertEquals("10 A.M. is not valid", exception4.message)

        val exception5 = assertThrows<IllegalArgumentException> { generator of "11:o1" }
        assertEquals("11:o1 is not valid", exception5.message)

        val exception7 = assertThrows<IllegalArgumentException> { generator of "24:30" }
        assertEquals("24:30 is not valid", exception7.message)

        val exception8 = assertThrows<IllegalArgumentException> { generator of "5:67" }
        assertEquals("5:67 is not valid", exception8.message)
    }
}
