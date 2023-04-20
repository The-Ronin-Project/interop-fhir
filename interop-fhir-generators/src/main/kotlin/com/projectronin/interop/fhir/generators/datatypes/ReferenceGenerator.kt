package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.FHIRStringDataGenerator
import com.projectronin.interop.fhir.generators.primitives.UriGenerator
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.test.data.generator.DataGenerator

class ReferenceGenerator : DataGenerator<Reference>() {
    val id: FHIRStringDataGenerator = FHIRStringDataGenerator()
    val type: UriGenerator = UriGenerator()
    val reference: FHIRStringDataGenerator = FHIRStringDataGenerator()
    val identifier: DataGenerator<Identifier> = IdentifierGenerator()

    override fun generateInternal(): Reference =
        Reference(
            id = id.generate(),
            type = type.generate(),
            reference = reference.generate(),
            identifier = identifier.generate()
        )
}

fun reference(type: String, id: String): Reference {
    val reference = ReferenceGenerator()
    reference.type of type
    reference.id of id
    reference.reference of "$type/$id"
    return reference.generate()
}
