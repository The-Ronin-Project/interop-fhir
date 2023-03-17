package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.CodeGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.generators.primitives.DateTimeGenerator
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.resource.MedicationStatement
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class MedicationStatementGenerator(
    override val id: NullDataGenerator<Id> = NullDataGenerator(),
    override val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val medicationCodeableConcept: DataGenerator<CodeableConcept> = CodeableConceptGenerator(),
    val dateAsserted: DataGenerator<DateTime> = DateTimeGenerator(),
    val status: CodeGenerator = CodeGenerator(),
    val subject: DataGenerator<Reference> = ReferenceGenerator()
) : FhirTestResource<MedicationStatement> {
    override fun toFhir(): MedicationStatement =
        MedicationStatement(
            id = id.generate(),
            identifier = identifier.generate(),
            dateAsserted = dateAsserted.generate(),
            medication = DynamicValue(
                type = DynamicValueType.CODEABLE_CONCEPT,
                value = medicationCodeableConcept.generate()
            ),
            status = status.generate(),
            subject = subject.generate()
        )
}

fun medicationStatement(block: MedicationStatementGenerator.() -> Unit): MedicationStatement {
    val medicationStatement = MedicationStatementGenerator()
    medicationStatement.apply(block)
    return medicationStatement.toFhir()
}
