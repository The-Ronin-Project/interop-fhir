package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.CodeGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.resource.Bundle
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class BundleGenerator(
    override val id: NullDataGenerator<Id> = NullDataGenerator(),
    override val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val type: CodeGenerator = CodeGenerator()
) : FhirTestResource<Bundle> {
    override fun toFhir(): Bundle = Bundle(
        id = id.generate(),
        identifier = identifier.generate().firstOrNull(),
        type = type.generate()
    )
}

fun bundle(block: BundleGenerator.() -> Unit): Bundle {
    val bundle = BundleGenerator()
    bundle.apply(block)
    return bundle.toFhir()
}
