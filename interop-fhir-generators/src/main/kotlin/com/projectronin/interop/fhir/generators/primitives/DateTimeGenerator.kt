package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.test.data.generator.temporal.BaseDateGenerator
import java.time.LocalDate

class DateTimeGenerator : BaseDateGenerator<DateTime>() {
    override fun convert(localDate: LocalDate): DateTime =
        DateTime(localDate.toString())
}

fun dateTime(block: DateTimeGenerator.() -> Unit): DateTime {
    val date = DateTimeGenerator()
    date.apply(block)
    return date.generate()
}
