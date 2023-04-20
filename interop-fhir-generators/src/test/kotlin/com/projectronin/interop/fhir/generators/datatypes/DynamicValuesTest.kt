package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.DateTimeGenerator
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.test.data.generator.DataGenerator
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DynamicValuesTest {
    @Test
    fun `can create boolean DynamicValue from FHIRBoolean`() {
        val value = DynamicValues.boolean(FHIRBoolean.TRUE)
        assertEquals(DynamicValueType.BOOLEAN, value.type)
        assertEquals(FHIRBoolean.TRUE, value.value)
    }

    @Test
    fun `can create boolean DynamicValue from Boolean`() {
        val value = DynamicValues.boolean(false)
        assertEquals(DynamicValueType.BOOLEAN, value.type)
        assertEquals(FHIRBoolean.FALSE, value.value)
    }

    @Test
    fun `can create boolean DynamicValue from boolean data generator`() {
        val generator = mockk<DataGenerator<FHIRBoolean>> {
            every { generate() } returns FHIRBoolean.FALSE
        }
        val value = DynamicValues.boolean(generator)
        assertEquals(DynamicValueType.BOOLEAN, value.type)
        assertEquals(FHIRBoolean.FALSE, value.value)
    }

    @Test
    fun `can create DateTime DynamicValue from DateTime`() {
        val dateTime = DateTime("2017-01-01T00:00:00.000Z")
        val value = DynamicValues.dateTime(dateTime)
        assertEquals(DynamicValueType.DATE_TIME, value.type)
        assertEquals(dateTime, value.value)
    }

    @Test
    fun `can create DateTime DynamicValue from DateTime data generator`() {
        val dateTime = DateTime("2017-01-01T00:00:00.000Z")
        val generator = mockk<DateTimeGenerator> {
            every { generate() } returns dateTime
        }

        val value = DynamicValues.dateTime(generator)
        assertEquals(DynamicValueType.DATE_TIME, value.type)
        assertEquals(dateTime, value.value)
    }

    @Test
    fun `can create integer DynamicValue from FHIRInteger`() {
        val value = DynamicValues.integer(FHIRInteger(123))
        assertEquals(DynamicValueType.INTEGER, value.type)
        assertEquals(FHIRInteger(123), value.value)
    }

    @Test
    fun `can create integer DynamicValue from Int`() {
        val value = DynamicValues.integer(456)
        assertEquals(DynamicValueType.INTEGER, value.type)
        assertEquals(FHIRInteger(456), value.value)
    }

    @Test
    fun `can create integer DynamicValue from FHIRInteger data generator`() {
        val generator = mockk<DataGenerator<FHIRInteger>> {
            every { generate() } returns FHIRInteger(789)
        }
        val value = DynamicValues.integer(generator)
        assertEquals(DynamicValueType.INTEGER, value.type)
        assertEquals(FHIRInteger(789), value.value)
    }
}
