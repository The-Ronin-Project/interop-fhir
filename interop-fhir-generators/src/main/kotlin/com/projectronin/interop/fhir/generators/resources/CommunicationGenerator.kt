package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.CodeGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.resource.Communication
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class CommunicationGenerator(
    override val id: NullDataGenerator<Id> = NullDataGenerator(),
    override val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val status: CodeGenerator = CodeGenerator()
) : FhirTestResource<Communication> {
    override fun toFhir(): Communication =
        Communication(
            id = id.generate(),
            identifier = identifier.generate(),
            status = status.generate()
        )
}

fun communication(block: CommunicationGenerator.() -> Unit): Communication {
    val communication = CommunicationGenerator()
    communication.apply(block)
    return communication.toFhir()
}
