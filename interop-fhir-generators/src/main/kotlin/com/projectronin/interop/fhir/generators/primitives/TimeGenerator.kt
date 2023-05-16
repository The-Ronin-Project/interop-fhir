package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.faker.IntGenerator

class TimeGenerator : DataGenerator<Time?>() {
    var hour: DataGenerator<Int> = IntGenerator(0, 23)
    var minute: DataGenerator<Int> = IntGenerator(0, 59)

    override fun generateInternal(): Time =
        Time("%02d:%02d".format(hour.generate(), minute.generate()))
}

infix fun DataGenerator<Time?>.of(value: String) {
    if (!value.matches(Regex("\\d{1,2}:\\d{2}"))) throw IllegalArgumentException("$value is not valid")

    val hour = value.split(":")[0].toInt()
    val minute = value.split(":")[1].toInt()
    if (hour < 0 || hour > 23 || minute < 0 || minute > 59) throw IllegalArgumentException("$value is not valid")

    of(Time(value))
}

fun time(block: TimeGenerator.() -> Unit): Time? {
    val time = TimeGenerator()
    time.apply(block)
    return time.generate()
}
