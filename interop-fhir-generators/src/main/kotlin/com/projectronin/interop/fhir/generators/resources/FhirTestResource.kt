package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.test.data.generator.DataGenerator

interface FhirTestResource<T : Resource<T>> {
    val id: DataGenerator<Id?>
    val meta: DataGenerator<Meta?>
    val implicitRules: DataGenerator<Uri?>
    val language: DataGenerator<Code?>

    fun toFhir(): T
}
