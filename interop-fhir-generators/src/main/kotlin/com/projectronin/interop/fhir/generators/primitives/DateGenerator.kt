package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.test.data.generator.temporal.BaseDateGenerator
import java.time.LocalDate

class DateGenerator(
    minimumYear: Int = 1920,
    maximumYear: Int = LocalDate.now().year + 10
) : BaseDateGenerator<Date>(minimumYear, maximumYear) {
    override fun convert(localDate: LocalDate): Date =
        Date(localDate.toString())
}

fun date(block: DateGenerator.() -> Unit): Date {
    val date = DateGenerator()
    date.apply(block)
    return date.generate()
}
