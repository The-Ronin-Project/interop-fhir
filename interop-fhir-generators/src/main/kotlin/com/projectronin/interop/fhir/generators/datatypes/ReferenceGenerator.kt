package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.FHIRStringDataGenerator
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

class ReferenceGenerator : DataGenerator<Reference>() {
    val id: FHIRStringDataGenerator = FHIRStringDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val reference: FHIRStringDataGenerator = FHIRStringDataGenerator()
    val type: DataGenerator<Uri?> = NullDataGenerator()
    val identifier: DataGenerator<Identifier?> = NullDataGenerator()
    val display: DataGenerator<FHIRString?> = NullDataGenerator()

    override fun generateInternal(): Reference =
        Reference(
            id = id.generate(),
            extension = extension.generate(),
            reference = reference.generate(),
            type = type.generate(),
            identifier = identifier.generate(),
            display = display.generate()
        )
}

fun reference(type: String, id: String): Reference {
    val reference = ReferenceGenerator()
    reference.reference of "$type/$id"
    return reference.generate()
}
