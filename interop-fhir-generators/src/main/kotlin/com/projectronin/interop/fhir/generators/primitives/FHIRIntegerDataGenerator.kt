package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.test.data.generator.DataGenerator

class FHIRIntegerDataGenerator : DataGenerator<FHIRInteger?>() {
    override fun generateInternal(): FHIRInteger? = null
}

infix fun DataGenerator<FHIRInteger?>.of(value: Int) = of(FHIRInteger(value))
