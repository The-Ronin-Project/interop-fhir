package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.DateTimeGenerator
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.test.data.generator.DataGenerator

object DynamicValues {
    fun boolean(boolean: FHIRBoolean) = DynamicValue(DynamicValueType.BOOLEAN, boolean)
    fun boolean(boolean: Boolean) = boolean(FHIRBoolean(boolean))
    fun boolean(booleanGenerator: DataGenerator<FHIRBoolean>) = boolean(booleanGenerator.generate())

    fun dateTime(dateTime: DateTime) = DynamicValue(DynamicValueType.DATE_TIME, dateTime)
    fun dateTime(dateTimeGenerator: DateTimeGenerator) = dateTime(dateTimeGenerator.generate())

    fun integer(integer: FHIRInteger) = DynamicValue(DynamicValueType.INTEGER, integer)
    fun integer(integer: Int) = integer(FHIRInteger(integer))
    fun integer(integerGenerator: DataGenerator<FHIRInteger>) = integer(integerGenerator.generate())
}
