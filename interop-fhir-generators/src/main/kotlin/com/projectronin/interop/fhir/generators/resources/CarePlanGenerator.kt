package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.CodeGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.resource.CarePlan
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class CarePlanGenerator(
    override val id: NullDataGenerator<Id> = NullDataGenerator(),
    override val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val category: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val status: CodeGenerator = CodeGenerator(),
    val intent: CodeGenerator = CodeGenerator(),
    val subject: DataGenerator<Reference> = ReferenceGenerator()
) : FhirTestResource<CarePlan> {
    override fun toFhir(): CarePlan =
        CarePlan(
            id = id.generate(),
            identifier = identifier.generate(),
            category = category.generate(),
            status = status.generate(),
            intent = intent.generate(),
            subject = subject.generate()

        )
}

fun carePlan(block: CarePlanGenerator.() -> Unit): CarePlan {
    val carePlan = CarePlanGenerator()
    carePlan.apply(block)
    return carePlan.toFhir()
}
