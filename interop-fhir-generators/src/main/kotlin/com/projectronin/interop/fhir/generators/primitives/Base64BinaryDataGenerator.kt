package com.projectronin.interop.fhir.generators.primitives

import com.projectronin.interop.fhir.r4.datatype.primitive.Base64Binary
import com.projectronin.test.data.generator.DataGenerator
import net.datafaker.Faker
import java.util.Base64

class Base64BinaryDataGenerator : DataGenerator<Base64Binary?>() {
    private val defaultString = Faker().lorem().fixedString(((1..10).random()) * 4)
    override fun generateInternal(): Base64Binary = Base64Binary(encode(defaultString))
}

infix fun DataGenerator<Base64Binary?>.of(value: String) = of(Base64Binary(encode(value)))

infix fun DataGenerator<Base64Binary?>.ofLength(stringLength: Int) {
    val stringValue = Faker().lorem().fixedString(stringLength)
    of(stringValue)
}

private fun encode(value: String): String = Base64.getEncoder().encodeToString(value.toByteArray())
