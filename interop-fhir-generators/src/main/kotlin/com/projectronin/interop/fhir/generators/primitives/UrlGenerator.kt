package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Url
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.faker.FakerDataGenerator

class UrlGenerator : FakerDataGenerator<Url>() {
    private val generator = faker.internet()
    override fun generateInternal(): Url {
        return Url(generator.url())
    }
}

infix fun DataGenerator<Url?>.of(value: String) = of(Url(value))
