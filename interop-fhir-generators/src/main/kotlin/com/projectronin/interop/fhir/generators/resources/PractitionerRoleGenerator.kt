package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.resource.PractitionerRole
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class PractitionerRoleGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val practitioner: DataGenerator<Reference> = ReferenceGenerator(),
    val location: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator())
) : FhirTestResource<PractitionerRole> {
    override fun toFhir(): PractitionerRole = PractitionerRole(
        id = id.generate(),
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
