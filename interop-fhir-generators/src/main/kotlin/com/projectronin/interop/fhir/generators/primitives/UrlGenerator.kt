package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Url
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.faker.FakerDataGenerator
import com.projectronin.test.data.generator.faker.UriGenerator

class UrlGenerator : FakerDataGenerator<Url>() {
    override fun generateInternal() = Url(UriGenerator().generate())
}

infix fun DataGenerator<Url?>.of(value: String) = of(Url(value))
