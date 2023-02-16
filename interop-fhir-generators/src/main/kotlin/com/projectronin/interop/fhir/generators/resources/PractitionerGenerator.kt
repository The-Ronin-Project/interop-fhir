package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.HumanNameGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.resource.Practitioner
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class PractitionerGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val name: ListDataGenerator<HumanName> = ListDataGenerator(1, HumanNameGenerator())
) : FhirTestResource<Practitioner> {
    override fun toFhir(): Practitioner =
        Practitioner(
            id = id.generate(),
            identifier = identifier.generate(),
            name = name.generate()
        )
}

fun practitioner(block: PractitionerGenerator.() -> Unit): Practitioner {
    val practitioner = PractitionerGenerator()
    practitioner.apply(block)
    return practitioner.toFhir()
}
