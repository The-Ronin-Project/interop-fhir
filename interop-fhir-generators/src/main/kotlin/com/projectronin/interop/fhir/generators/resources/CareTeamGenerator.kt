package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.CareTeam
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class CareTeamGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    override val text: DataGenerator<Narrative?> = NullDataGenerator(),
    override val contained: ListDataGenerator<Resource<*>?> = ListDataGenerator(0, NullDataGenerator()),
    override val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    override val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val subject: DataGenerator<Reference> = ReferenceGenerator(),
    val status: CodeGenerator = CodeGenerator()
) : DomainResource<CareTeam> {
    override fun toFhir(): CareTeam =
        CareTeam(
            id = id.generate(),
            meta = meta.generate(),
            implicitRules = implicitRules.generate(),
            language = language.generate(),
            text = text.generate(),
            contained = contained.generate().filterNotNull(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
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
