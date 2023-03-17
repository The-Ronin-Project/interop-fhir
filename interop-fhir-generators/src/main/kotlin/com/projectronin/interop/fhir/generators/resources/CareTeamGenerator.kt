package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.CodeGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.resource.CareTeam
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class CareTeamGenerator(
    override val id: NullDataGenerator<Id> = NullDataGenerator(),
    override val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val subject: DataGenerator<Reference> = ReferenceGenerator(),
    val status: CodeGenerator = CodeGenerator()
) : FhirTestResource<CareTeam> {
    override fun toFhir(): CareTeam =
        CareTeam(
            id = id.generate(),
            identifier = identifier.generate(),
            subject = subject.generate(),
            status = status.generate()
        )
}

fun careTeam(block: CareTeamGenerator.() -> Unit): CareTeam {
    val careTeam = CareTeamGenerator()
    careTeam.apply(block)
    return careTeam.toFhir()
}
