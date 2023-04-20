package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Bundle
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class BundleGenerator(
    override val id: NullDataGenerator<Id> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val type: CodeGenerator = CodeGenerator()
) : FhirTestResource<Bundle> {
    override fun toFhir(): Bundle = Bundle(
        id = id.generate(),
        meta = meta.generate(),
        implicitRules = implicitRules.generate(),
        language = language.generate(),
        identifier = identifier.generate().firstOrNull(),
        type = type.generate()
    )
}

fun bundle(block: BundleGenerator.() -> Unit): Bundle {
    val bundle = BundleGenerator()
    bundle.apply(block)
    return bundle.toFhir()
}
