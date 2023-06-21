package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.r4.datatype.Expression
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class ExpressionGenerator(
    val id: DataGenerator<FHIRString?> = NullDataGenerator(),
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val description: DataGenerator<FHIRString?> = NullDataGenerator(),
    val name: DataGenerator<Id?> = NullDataGenerator(),
    val language: DataGenerator<Code?> = CodeGenerator(listOf("text/cql", "text/fhirpath", "application/x-fhir-query")),
    val expression: DataGenerator<FHIRString?> = NullDataGenerator(),
    val reference: DataGenerator<Uri?> = NullDataGenerator()
) : DataGenerator<Expression>() {
    override fun generateInternal() =
        Expression(
            id = id.generate(),
            extension = extension.generate(),
            description = description.generate(),
            name = name.generate(),
            language = language.generate(),
            expression = expression.generate(),
            reference = reference.generate()
        )
}

fun expression(block: ExpressionGenerator.() -> Unit): Expression {
    val expression = ExpressionGenerator()
    expression.apply(block)
    return expression.generate()
}
