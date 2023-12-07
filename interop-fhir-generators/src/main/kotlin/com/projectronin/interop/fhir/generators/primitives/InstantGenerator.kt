package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.test.data.generator.DataGenerator
import java.time.LocalDate
import com.projectronin.test.data.generator.temporal.DateGenerator as BaseDateGenerator
import com.projectronin.test.data.generator.temporal.TimeGenerator as BaseTimeGenerator

class InstantGenerator : DataGenerator<Instant>() {
    val year: DataGenerator<Int> = BaseDateGenerator().year
    val month: DataGenerator<Int> = BaseDateGenerator().month
    val day: DataGenerator<Int> = BaseDateGenerator().day
    val hour: DataGenerator<Int> = BaseTimeGenerator().hour
    val minute: DataGenerator<Int> = BaseTimeGenerator().minute
    val second: DataGenerator<Int> = BaseTimeGenerator().second

    override fun generateInternal(): Instant =
        Instant(
            "%d-%02d-%02dT%02d:%02d:%02dZ".format(
                year.generate(),
                month.generate(),
                day.generate(),
                hour.generate(),
                minute.generate(),
                second.generate(),
            ),
        )
}

fun instant(block: InstantGenerator.() -> Unit): Instant {
    val instant = InstantGenerator()
    instant.apply(block)
    return instant.generate()
}

fun Int.daysAgo(): Instant {
    val adjustedNow = LocalDate.now().minusDays(this.toLong())
    return instant {
        year of adjustedNow.year
        month of adjustedNow.month.value
        day of adjustedNow.dayOfMonth
    }
}

fun Int.daysFromNow(): Instant {
    val adjustedNow = LocalDate.now().plusDays(this.toLong())
    return instant {
        year of adjustedNow.year
        month of adjustedNow.month.value
        day of adjustedNow.dayOfMonth
    }
}
