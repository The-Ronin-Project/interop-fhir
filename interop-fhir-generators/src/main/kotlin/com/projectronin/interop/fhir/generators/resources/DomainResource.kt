package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

interface DomainResource<T : Resource<T>> : FhirTestResource<T> {
    val text: DataGenerator<Narrative?>
    val contained: ListDataGenerator<Resource<*>?>
    val extension: ListDataGenerator<Extension>
    val modifierExtension: ListDataGenerator<Extension>
}
