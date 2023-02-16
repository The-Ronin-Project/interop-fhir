package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator

class ReferenceGenerator : DataGenerator<Reference>() {
    val id: NullDataGenerator<String> = NullDataGenerator()
    val type: UriGenerator = UriGenerator()
    val reference: DataGenerator<String?> = NullDataGenerator()
    val identifier: DataGenerator<Identifier> = IdentifierGenerator()

    override fun generateInternal(): Reference =
        Reference(
            id = id.generate()?.asFHIR(),
            type = type.generate(),
            reference = reference.generate()?.asFHIR(),
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
