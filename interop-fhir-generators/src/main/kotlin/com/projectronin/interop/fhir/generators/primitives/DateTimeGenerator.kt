package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.faker.IntGenerator

class DateTimeGenerator : DataGenerator<DateTime>() {
    val year: DataGenerator<Int> = IntGenerator(1920, 2015)
    val month: DataGenerator<Int> = IntGenerator(1, 12)
    val day: DataGenerator<Int> = IntGenerator(1, 28) // To prevent invalid dates

    override fun generateInternal(): DateTime =
        DateTime("%d-%02d-%02d".format(year.generate(), month.generate(), day.generate()))
}

fun dateTime(block: DateTimeGenerator.() -> Unit): DateTime {
    val date = DateTimeGenerator()
    date.apply(block)
    return date.generate()
}
