package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.primitives.StringGenerator
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import com.projectronin.interop.fhir.r4.resource.Organization
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class OrganizationGenerator(
    override val id: NullDataGenerator<Id> = NullDataGenerator(),
    override val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val name: DataGenerator<String> = StringGenerator()

) : FhirTestResource<Organization> {

    override fun toFhir(): Organization =
        Organization(
            id = id.generate(),
            identifier = identifier.generate(),
            name = name.generate().asFHIR()
        )
}

fun organization(block: OrganizationGenerator.() -> Unit): Organization {
    val organization = OrganizationGenerator()
    organization.apply(block)
    return organization.toFhir()
}
