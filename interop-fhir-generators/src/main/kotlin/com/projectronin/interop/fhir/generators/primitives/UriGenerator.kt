package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.test.data.generator.DataGenerator

class UriGenerator : DataGenerator<Uri>() {
    private val generator = StringGenerator()
    override fun generateInternal(): Uri {
        return Uri(generator.generate())
    }

    infix fun of(value: String) {
        of(Uri(value))
    }
}

infix fun DataGenerator<Uri?>.of(value: String) = of(Uri(value))
