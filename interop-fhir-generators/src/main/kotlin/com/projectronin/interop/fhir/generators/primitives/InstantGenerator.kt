package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.faker.IntGenerator
import java.time.LocalDate

class InstantGenerator : DataGenerator<Instant>() {
    val year: DataGenerator<Int> = IntGenerator(1920, 2015)
    val month: DataGenerator<Int> = IntGenerator(1, 12)
    val day: DataGenerator<Int> = IntGenerator(1, 28) // To prevent invalid dates
    val hour: DataGenerator<Int> = IntGenerator(0, 23)
    val minute: DataGenerator<Int> = IntGenerator(0, 59)
    val second: DataGenerator<Int> = IntGenerator(0, 59)

    override fun generateInternal(): Instant =
        Instant(
            "%d-%02d-%02dT%02d:%02d:%02dZ".format(
                year.generate(),
                month.generate(),
                day.generate(),
                hour.generate(),
                minute.generate(),
                second.generate()
            )
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
