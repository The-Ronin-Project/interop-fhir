package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.CodeGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.resource.ConceptMap
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class ConceptMapGenerator(
    override val id: NullDataGenerator<Id> = NullDataGenerator(),
    override val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val status: CodeGenerator = CodeGenerator()
) : FhirTestResource<ConceptMap> {
    override fun toFhir(): ConceptMap =
        ConceptMap(
            id = id.generate(),
            identifier = identifier.generate().firstOrNull(),
            status = status.generate()
        )
}

fun conceptMap(block: ConceptMapGenerator.() -> Unit): ConceptMap {
    val conceptMap = ConceptMapGenerator()
    conceptMap.apply(block)
    return conceptMap.toFhir()
}
