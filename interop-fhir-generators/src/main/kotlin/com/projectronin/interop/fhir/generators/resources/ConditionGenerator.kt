package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.resource.Condition
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class ConditionGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val clinicalStatus: DataGenerator<CodeableConcept> = CodeableConceptGenerator(),
    val category: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val code: DataGenerator<CodeableConcept> = CodeableConceptGenerator(),
    val subject: DataGenerator<Reference> = ReferenceGenerator()
) : FhirTestResource<Condition> {
    override fun toFhir(): Condition =
        Condition(
            id = id.generate(),
            identifier = identifier.generate(),
            clinicalStatus = clinicalStatus.generate(),
            category = category.generate(),
            code = code.generate(),
            subject = subject.generate()
        )
}

fun condition(block: ConditionGenerator.() -> Unit): Condition {
    val condition = ConditionGenerator()
    condition.apply(block)
    return condition.toFhir()
}
