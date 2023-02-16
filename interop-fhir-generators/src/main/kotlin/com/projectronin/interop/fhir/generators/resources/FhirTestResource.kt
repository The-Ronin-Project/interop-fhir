package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

interface FhirTestResource<T : Resource<T>> {
    val id: DataGenerator<Id?>
    val identifier: ListDataGenerator<Identifier>

    fun toFhir(): T
}
