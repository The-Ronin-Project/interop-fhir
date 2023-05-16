package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.test.data.generator.DataGenerator

class FHIRBooleanDataGenerator : DataGenerator<FHIRBoolean?>() {
    override fun generateInternal(): FHIRBoolean? = null
}

infix fun DataGenerator<FHIRBoolean?>.of(value: Boolean) {
    of(FHIRBoolean(value))
}
