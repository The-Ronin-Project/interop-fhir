package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Range
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SampledData
import com.projectronin.interop.fhir.r4.datatype.Timing
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.test.data.generator.DataGenerator

object DynamicValues {
    fun boolean(boolean: FHIRBoolean) = DynamicValue(DynamicValueType.BOOLEAN, boolean)
    fun boolean(boolean: Boolean) = boolean(FHIRBoolean(boolean))
    fun boolean(booleanGenerator: DataGenerator<FHIRBoolean>) = boolean(booleanGenerator.generate())

    fun codeableConcept(codeableConcept: CodeableConcept) =
        DynamicValue(DynamicValueType.CODEABLE_CONCEPT, codeableConcept)

    fun codeableConcept(codeableConceptGenerator: DataGenerator<CodeableConcept>) =
        codeableConcept(codeableConceptGenerator.generate())

    fun dateTime(dateTime: String) = dateTime(DateTime(dateTime))
    fun dateTime(dateTime: DateTime) = DynamicValue(DynamicValueType.DATE_TIME, dateTime)
    fun dateTime(dateTimeGenerator: DataGenerator<DateTime>) = dateTime(dateTimeGenerator.generate())

    fun instant(instant: String) = instant(Instant(instant))
    fun instant(instant: Instant) = DynamicValue(DynamicValueType.INSTANT, instant)
    fun instant(instantGenerator: DataGenerator<Instant>) = instant(instantGenerator.generate())

    fun integer(integer: FHIRInteger) = DynamicValue(DynamicValueType.INTEGER, integer)
    fun integer(integer: Int) = integer(FHIRInteger(integer))
    fun integer(integerGenerator: DataGenerator<FHIRInteger>) = integer(integerGenerator.generate())

    fun period(period: Period) = DynamicValue(DynamicValueType.PERIOD, period)
    fun period(periodGenerator: DataGenerator<Period>) = period(periodGenerator.generate())

    fun quantity(quantity: Quantity) = DynamicValue(DynamicValueType.QUANTITY, quantity)
    fun quantity(quantityGenerator: DataGenerator<Quantity>) = quantity(quantityGenerator.generate())

    fun range(range: Range) = DynamicValue(DynamicValueType.RANGE, range)
    fun range(rangeGenerator: DataGenerator<Range>) = range(rangeGenerator.generate())

    fun ratio(ratio: Ratio) = DynamicValue(DynamicValueType.RATIO, ratio)
    fun ratio(ratioGenerator: DataGenerator<Ratio>) = ratio(ratioGenerator.generate())

    fun reference(reference: Reference) = DynamicValue(DynamicValueType.REFERENCE, reference)
    fun reference(referenceGenerator: DataGenerator<Reference>) = reference(referenceGenerator.generate())

    fun sampledData(sampledData: SampledData) = DynamicValue(DynamicValueType.SAMPLED_DATA, sampledData)
    fun sampledData(sampledDataGenerator: DataGenerator<SampledData>) = sampledData(sampledDataGenerator.generate())

    fun string(string: FHIRString) = DynamicValue(DynamicValueType.STRING, string)
    fun string(string: String) = string(FHIRString(string))
    fun string(stringGenerator: DataGenerator<FHIRString>) = string(stringGenerator.generate())

    fun time(time: String) = time(Time(time))
    fun time(time: Time) = DynamicValue(DynamicValueType.TIME, time)
    fun time(timeGenerator: DataGenerator<Time>) = time(timeGenerator.generate())

    fun timing(timing: Timing) = DynamicValue(DynamicValueType.TIMING, timing)
    fun timing(timingGenerator: DataGenerator<Timing>) = timing(timingGenerator.generate())
}
