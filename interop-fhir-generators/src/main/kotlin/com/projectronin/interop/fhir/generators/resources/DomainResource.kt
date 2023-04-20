package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

interface DomainResource<T : Resource<T>> : FhirTestResource<T> {
    val text: DataGenerator<Narrative?>
    val contained: ListDataGenerator<ContainedResource?>
    val extension: ListDataGenerator<Extension>
    val modifierExtension: ListDataGenerator<Extension>
}

fun contained(resource: FhirTestResource<*>): ContainedResource =
    ContainedResource(JacksonManager.objectMapper.writeValueAsString(resource))
