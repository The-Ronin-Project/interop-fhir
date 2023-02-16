package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.faker.WordGenerator

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
