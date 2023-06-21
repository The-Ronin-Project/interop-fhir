package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.faker.FakerDataGenerator

class UriGenerator : FakerDataGenerator<Uri>() {
    private val generator = faker.internet()
    override fun generateInternal(): Uri {
        return Uri(generator.url())
    }

    infix fun of(value: String) {
        of(Uri(value))
    }
}

infix fun DataGenerator<Uri?>.of(value: String) = of(Uri(value))
