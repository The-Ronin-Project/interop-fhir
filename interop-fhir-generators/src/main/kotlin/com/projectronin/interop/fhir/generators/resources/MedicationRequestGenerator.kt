package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.CodeGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.resource.MedicationRequest
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class MedicationRequestGenerator(
    override val id: NullDataGenerator<Id> = NullDataGenerator(),
    override val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val intent: CodeGenerator = CodeGenerator(),
    val medicationCodeableConcept: DataGenerator<CodeableConcept> = CodeableConceptGenerator(),
    val status: CodeGenerator = CodeGenerator(),
    val subject: DataGenerator<Reference> = ReferenceGenerator(),
    val requester: DataGenerator<Reference> = ReferenceGenerator()
) : FhirTestResource<MedicationRequest> {
    override fun toFhir(): MedicationRequest =
        MedicationRequest(
            id = id.generate(),
            identifier = identifier.generate(),
            intent = intent.generate(),
            medication = DynamicValue(
                type = DynamicValueType.CODEABLE_CONCEPT,
                value = medicationCodeableConcept.generate()
            ),
            status = status.generate(),
            subject = subject.generate(),
            requester = requester.generate()
        )
}

fun medicationRequest(block: MedicationRequestGenerator.() -> Unit): MedicationRequest {
    val medicationRequest = MedicationRequestGenerator()
    medicationRequest.apply(block)
    return medicationRequest.toFhir()
}
