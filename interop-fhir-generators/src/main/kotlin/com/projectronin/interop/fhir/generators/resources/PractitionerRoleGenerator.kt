package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.PractitionerRole
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class PractitionerRoleGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    override val text: DataGenerator<Narrative?> = NullDataGenerator(),
    override val contained: ListDataGenerator<Resource<*>?> = ListDataGenerator(0, NullDataGenerator()),
    override val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    override val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val practitioner: DataGenerator<Reference> = ReferenceGenerator(),
    val location: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator())
) : DomainResource<PractitionerRole> {
    override fun toFhir(): PractitionerRole = PractitionerRole(
        id = id.generate(),
        meta = meta.generate(),
        implicitRules = implicitRules.generate(),
        language = language.generate(),
        text = text.generate(),
        contained = contained.generate().filterNotNull(),
        extension = extension.generate(),
        modifierExtension = modifierExtension.generate(),
        identifier = identifier.generate(),
        practitioner = practitioner.generate(),
        location = location.generate()
    )
}

fun practitionerRole(block: PractitionerRoleGenerator.() -> Unit): PractitionerRole {
    val practitionerRole = PractitionerRoleGenerator()
    practitionerRole.apply(block)
    return practitionerRole.toFhir()
}
