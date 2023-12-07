package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.generators.primitives.DateTimeGenerator
import com.projectronin.interop.fhir.generators.primitives.TimeGenerator
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Timing
import com.projectronin.interop.fhir.r4.datatype.TimingRepeat
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class TimingGenerator(
    val id: DataGenerator<FHIRString?> = NullDataGenerator(),
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val event: ListDataGenerator<DateTime> = ListDataGenerator(0, DateTimeGenerator()),
    val repeat: DataGenerator<TimingRepeat?> = NullDataGenerator(),
    val code: DataGenerator<CodeableConcept?> = NullDataGenerator(),
) : DataGenerator<Timing>() {
    override fun generateInternal() =
        Timing(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            event = event.generate(),
            repeat = repeat.generate(),
            code = code.generate(),
        )
}

class TimingRepeatGenerator : DataGenerator<TimingRepeat>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val bounds: DataGenerator<DynamicValue<Any>?> = NullDataGenerator()
    val count: DataGenerator<PositiveInt?> = NullDataGenerator()
    val countMax: DataGenerator<PositiveInt?> = NullDataGenerator()
    val duration: DataGenerator<Decimal?> = NullDataGenerator()
    val durationMax: DataGenerator<Decimal?> = NullDataGenerator()
    val durationUnit: DataGenerator<Code?> = NullDataGenerator()
    val frequency: DataGenerator<PositiveInt?> = NullDataGenerator()
    val frequencyMax: DataGenerator<PositiveInt?> = NullDataGenerator()
    val period: DataGenerator<Decimal?> = NullDataGenerator()
    val periodMax: DataGenerator<Decimal?> = NullDataGenerator()
    val periodUnit: DataGenerator<Code?> = NullDataGenerator()
    val dayOfWeek: ListDataGenerator<Code?> = ListDataGenerator(0, CodeGenerator())
    val timeOfDay: ListDataGenerator<Time?> = ListDataGenerator(0, TimeGenerator())

    @Suppress("ktlint:standard:property-naming")
    val `when`: ListDataGenerator<Code?> = ListDataGenerator(0, CodeGenerator())
    val offset: DataGenerator<UnsignedInt?> = NullDataGenerator()

    override fun generateInternal(): TimingRepeat {
        return TimingRepeat(
            id = id.generate(),
            extension = extension.generate(),
            bounds = bounds.generate(),
            count = count.generate(),
            countMax = countMax.generate(),
            duration = duration.generate(),
            durationMax = durationMax.generate(),
            durationUnit = durationUnit.generate(),
            frequency = frequency.generate(),
            frequencyMax = frequencyMax.generate(),
            period = period.generate(),
            periodMax = periodMax.generate(),
            periodUnit = periodUnit.generate(),
            dayOfWeek = dayOfWeek.generate().filterNotNull(),
            timeOfDay = timeOfDay.generate().filterNotNull(),
            `when` = `when`.generate().filterNotNull(),
            offset = offset.generate(),
        )
    }
}

fun timing(block: TimingGenerator.() -> Unit): Timing {
    val timing = TimingGenerator()
    timing.apply(block)
    return timing.generate()
}

fun timingRepeat(block: TimingRepeatGenerator.() -> Unit): TimingRepeat {
    val repeat = TimingRepeatGenerator()
    repeat.apply(block)
    return repeat.generate()
}
