package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.faker.FakerDataGenerator

class CanonicalGenerator : FakerDataGenerator<Canonical>() {
    private val generator = faker.internet()

    override fun generateInternal(): Canonical {
        return Canonical(generator.url())
    }
}

infix fun DataGenerator<Canonical?>.of(value: String) = of(Canonical(value))
