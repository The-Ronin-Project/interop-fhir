package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Range
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SampledData
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.Timing
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Time
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
    fun `can create CodeableConcept DynamicValue from CodeableConcept`() {
        val codeableConcept = CodeableConcept()
        val value = DynamicValues.codeableConcept(codeableConcept)
        assertEquals(DynamicValueType.CODEABLE_CONCEPT, value.type)
        assertEquals(codeableConcept, value.value)
    }

    @Test
    fun `can create CodeableConcept DynamicValue from CodeableConcept data generator`() {
        val codeableConcept = CodeableConcept()
        val generator = mockk<DataGenerator<CodeableConcept>> {
            every { generate() } returns codeableConcept
        }
        val value = DynamicValues.codeableConcept(generator)
        assertEquals(DynamicValueType.CODEABLE_CONCEPT, value.type)
        assertEquals(codeableConcept, value.value)
    }

    @Test
    fun `can create DateTime DynamicValue from String`() {
        val dateTime = "2017-01-01T00:00:00.000Z"
        val value = DynamicValues.dateTime(dateTime)
        assertEquals(DynamicValueType.DATE_TIME, value.type)
        assertEquals(dateTime, value.value.value)
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
        val generator = mockk<DataGenerator<DateTime>> {
            every { generate() } returns dateTime
        }

        val value = DynamicValues.dateTime(generator)
        assertEquals(DynamicValueType.DATE_TIME, value.type)
        assertEquals(dateTime, value.value)
    }

    @Test
    fun `can create Instant DynamicValue from String`() {
        val instant = "2015-02-07T13:28:17.239+02:00"
        val value = DynamicValues.instant(instant)
        assertEquals(DynamicValueType.INSTANT, value.type)
        assertEquals(instant, value.value.value)
    }

    @Test
    fun `can create Instant DynamicValue from Instant`() {
        val instant = Instant("2015-02-07T13:28:17.239+02:00")
        val value = DynamicValues.instant(instant)
        assertEquals(DynamicValueType.INSTANT, value.type)
        assertEquals(instant, value.value)
    }

    @Test
    fun `can create Instant DynamicValue from Instant data generator`() {
        val instant = Instant("2015-02-07T13:28:17.239+02:00")
        val generator = mockk<DataGenerator<Instant>> {
            every { generate() } returns instant
        }
        val value = DynamicValues.instant(generator)
        assertEquals(DynamicValueType.INSTANT, value.type)
        assertEquals(instant, value.value)
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

    @Test
    fun `can create Period DynamicValue from Period`() {
        val period = Period()
        val value = DynamicValues.period(period)
        assertEquals(DynamicValueType.PERIOD, value.type)
        assertEquals(period, value.value)
    }

    @Test
    fun `can create Period DynamicValue from Period data generator`() {
        val period = Period()
        val generator = mockk<DataGenerator<Period>> {
            every { generate() } returns period
        }
        val value = DynamicValues.period(generator)
        assertEquals(DynamicValueType.PERIOD, value.type)
        assertEquals(period, value.value)
    }

    @Test
    fun `can create Quantity DynamicValue from Quantity`() {
        val quantity = Quantity()
        val value = DynamicValues.quantity(quantity)
        assertEquals(DynamicValueType.QUANTITY, value.type)
        assertEquals(quantity, value.value)
    }

    @Test
    fun `can create Quantity DynamicValue from Quantity data generator`() {
        val quantity = Quantity()
        val generator = mockk<DataGenerator<Quantity>> {
            every { generate() } returns quantity
        }
        val value = DynamicValues.quantity(generator)
        assertEquals(DynamicValueType.QUANTITY, value.type)
        assertEquals(quantity, value.value)
    }

    @Test
    fun `can create Range DynamicValue from Range`() {
        val range = Range()
        val value = DynamicValues.range(range)
        assertEquals(DynamicValueType.RANGE, value.type)
        assertEquals(range, value.value)
    }

    @Test
    fun `can create Range DynamicValue from Range data generator`() {
        val range = Range()
        val generator = mockk<DataGenerator<Range>> {
            every { generate() } returns range
        }
        val value = DynamicValues.range(generator)
        assertEquals(DynamicValueType.RANGE, value.type)
        assertEquals(range, value.value)
    }

    @Test
    fun `can create Ratio DynamicValue from Ratio`() {
        val ratio = Ratio()
        val value = DynamicValues.ratio(ratio)
        assertEquals(DynamicValueType.RATIO, value.type)
        assertEquals(ratio, value.value)
    }

    @Test
    fun `can create Ratio DynamicValue from Ratio data generator`() {
        val ratio = Ratio()
        val generator = mockk<DataGenerator<Ratio>> {
            every { generate() } returns ratio
        }
        val value = DynamicValues.ratio(generator)
        assertEquals(DynamicValueType.RATIO, value.type)
        assertEquals(ratio, value.value)
    }

    @Test
    fun `can create Reference DynamicValue from Reference`() {
        val reference = Reference()
        val value = DynamicValues.reference(reference)
        assertEquals(DynamicValueType.REFERENCE, value.type)
        assertEquals(reference, value.value)
    }

    @Test
    fun `can create Reference DynamicValue from Reference data generator`() {
        val reference = Reference()
        val generator = mockk<DataGenerator<Reference>> {
            every { generate() } returns reference
        }
        val value = DynamicValues.reference(generator)
        assertEquals(DynamicValueType.REFERENCE, value.type)
        assertEquals(reference, value.value)
    }

    @Test
    fun `can create SampledData DynamicValue from SampledData`() {
        val sampledData = SampledData(
            origin = SimpleQuantity(value = Decimal(42.0)),
            period = Decimal(0.5),
            dimensions = PositiveInt(1)
        )
        val value = DynamicValues.sampledData(sampledData)
        assertEquals(DynamicValueType.SAMPLED_DATA, value.type)
        assertEquals(sampledData, value.value)
    }

    @Test
    fun `can create SampledData DynamicValue from SampledData data generator`() {
        val sampledData = SampledData(
            origin = SimpleQuantity(value = Decimal(42.0)),
            period = Decimal(0.5),
            dimensions = PositiveInt(1)
        )
        val generator = mockk<DataGenerator<SampledData>> {
            every { generate() } returns sampledData
        }
        val value = DynamicValues.sampledData(generator)
        assertEquals(DynamicValueType.SAMPLED_DATA, value.type)
        assertEquals(sampledData, value.value)
    }

    @Test
    fun `can create string DynamicValue from FHIRString`() {
        val value = DynamicValues.string(FHIRString("string"))
        assertEquals(DynamicValueType.STRING, value.type)
        assertEquals(FHIRString("string"), value.value)
    }

    @Test
    fun `can create string DynamicValue from String`() {
        val value = DynamicValues.string("string")
        assertEquals(DynamicValueType.STRING, value.type)
        assertEquals(FHIRString("string"), value.value)
    }

    @Test
    fun `can create string DynamicValue from FHIRString data generator`() {
        val generator = mockk<DataGenerator<FHIRString>> {
            every { generate() } returns FHIRString("generated")
        }
        val value = DynamicValues.string(generator)
        assertEquals(DynamicValueType.STRING, value.type)
        assertEquals(FHIRString("generated"), value.value)
    }

    @Test
    fun `can create Time DynamicValue from String`() {
        val time = "12:51:00"
        val value = DynamicValues.time(time)
        assertEquals(DynamicValueType.TIME, value.type)
        assertEquals(time, value.value.value)
    }

    @Test
    fun `can create Time DynamicValue from Time`() {
        val time = Time("12:51:00")
        val value = DynamicValues.time(time)
        assertEquals(DynamicValueType.TIME, value.type)
        assertEquals(time, value.value)
    }

    @Test
    fun `can create Time DynamicValue from Time data generator`() {
        val time = Time("12:51:00")
        val generator = mockk<DataGenerator<Time>> {
            every { generate() } returns time
        }
        val value = DynamicValues.time(generator)
        assertEquals(DynamicValueType.TIME, value.type)
        assertEquals(time, value.value)
    }

    @Test
    fun `can create Timing DynamicValue from Timing`() {
        val timing = Timing()
        val value = DynamicValues.timing(timing)
        assertEquals(DynamicValueType.TIMING, value.type)
        assertEquals(timing, value.value)
    }

    @Test
    fun `can create Timing DynamicValue from Timing data generator`() {
        val timing = Timing()
        val generator = mockk<DataGenerator<Timing>> {
            every { generate() } returns timing
        }
        val value = DynamicValues.timing(generator)
        assertEquals(DynamicValueType.TIMING, value.type)
        assertEquals(timing, value.value)
    }
}
