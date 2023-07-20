package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.primitives.Base64BinaryDataGenerator
import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Base64Binary
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Binary
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator

data class BinaryGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    val contentType: DataGenerator<Code?> = CodeGenerator(listOf("text/plain")),
    val securityContent: DataGenerator<Reference?> = NullDataGenerator(),
    val data: DataGenerator<Base64Binary?> = Base64BinaryDataGenerator()
) : FhirTestResource<Binary> {
    override fun toFhir(): Binary =
        Binary(
            id = id.generate(),
            meta = meta.generate(),
            implicitRules = implicitRules.generate(),
            language = language.generate(),
            contentType = contentType.generate(),
            securityContent = securityContent.generate(),
            data = data.generate()
        )
}

fun binary(block: BinaryGenerator.() -> Unit): Binary {
    val binary = BinaryGenerator()
    binary.apply(block)
    return binary.toFhir()
}
