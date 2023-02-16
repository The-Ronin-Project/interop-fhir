package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.test.data.generator.NullDataGenerator

class CodeGenerator : NullDataGenerator<Code>() {
    infix fun of(value: String) {
        of(Code(value))
    }
}
