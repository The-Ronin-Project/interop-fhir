package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.faker.WordGenerator
import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.asSequence

open class StringGenerator(private val minimumLength: Int = 15) :
    DataGenerator<String>() {
    private val wordGenerator = WordGenerator()
    override fun generateInternal(): String {
        var word = wordGenerator.generate()
        while (word.length <= minimumLength) {
            word = word + "-" + wordGenerator.generate()
        }

        return word
    }
}

open class IdentifierStringGenerator(private val minimumLength: Int = 15) : StringGenerator(minimumLength) {
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    override fun generateInternal(): String =
        ThreadLocalRandom.current()
            .ints(minimumLength.toLong(), 0, charPool.size)
            .asSequence()
            .map(charPool::get)
            .joinToString("")
}
