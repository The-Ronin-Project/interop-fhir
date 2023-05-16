package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.faker.IntGenerator
import kotlin.reflect.KClass

class CodeGenerator(private val possibleValues: List<String> = listOf()) : DataGenerator<Code?>() {
    constructor(enumClass: KClass<out CodedEnum<*>>) : this(enumClass.java.enumConstants.map { it.code })

    override fun generateInternal(): Code? =
        if (possibleValues.isEmpty()) {
            null
        } else {
            val index = IntGenerator(0, possibleValues.size - 1).generate()
            Code(possibleValues[index])
        }
}

infix fun DataGenerator<Code?>.of(value: String) {
    of(Code(value))
}
