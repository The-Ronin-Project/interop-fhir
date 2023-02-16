package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.faker.IntGenerator

class DateGenerator : DataGenerator<Date>() {
    val year: DataGenerator<Int> = IntGenerator(1920, 2015)
    val month: DataGenerator<Int> = IntGenerator(1, 12)
    val day: DataGenerator<Int> = IntGenerator(1, 28) // To prevent invalid dates

    override fun generateInternal(): Date =
        Date("%d-%02d-%02d".format(year.generate(), month.generate(), day.generate()))
}

fun date(block: DateGenerator.() -> Unit): Date {
    val date = DateGenerator()
    date.apply(block)
    return date.generate()
}
