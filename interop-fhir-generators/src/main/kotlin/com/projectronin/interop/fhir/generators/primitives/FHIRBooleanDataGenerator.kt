package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.test.data.generator.DataGenerator

class FHIRBooleanDataGenerator : DataGenerator<FHIRBoolean?>() {
    override fun generateInternal(): FHIRBoolean? = null

    fun generateRequired(): FHIRBoolean =
        when (listOf(0, 1).random()) {
            0 -> FHIRBoolean(false)
            else -> FHIRBoolean(true)
        }
}

infix fun DataGenerator<FHIRBoolean?>.of(value: Boolean) {
    of(FHIRBoolean(value))
}
