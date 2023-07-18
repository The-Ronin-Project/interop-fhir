package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.faker.FakerDataGenerator
import com.projectronin.test.data.generator.faker.UriGenerator

class UriGenerator : FakerDataGenerator<Uri>() {
    override fun generateInternal() = Uri(UriGenerator().generate())

    infix fun of(value: String) {
        of(Uri(value))
    }
}

infix fun DataGenerator<Uri?>.of(value: String) = of(Uri(value))
