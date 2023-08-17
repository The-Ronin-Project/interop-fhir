package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.AnnotationGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.DosageGenerator
import com.projectronin.interop.fhir.generators.datatypes.DynamicValues
import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Dosage
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.MedicationStatement
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.interop.fhir.r4.valueset.MedicationStatementStatus
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class MedicationStatementGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    override val text: DataGenerator<Narrative?> = NullDataGenerator(),
    override val contained: ListDataGenerator<Resource<*>?> = ListDataGenerator(0, NullDataGenerator()),
    override val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    override val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val basedOn: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val partOf: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val status: CodeGenerator = CodeGenerator(MedicationStatementStatus::class),
    val statusReason: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val category: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val medication: DataGenerator<DynamicValue<Any>> = MedicationStatementMedicationGenerator(),
    val subject: DataGenerator<Reference> = ReferenceGenerator(),
    val context: DataGenerator<Reference?> = NullDataGenerator(),
    val effective: DataGenerator<DynamicValue<Any>?> = NullDataGenerator(),
    val dateAsserted: DataGenerator<DateTime?> = NullDataGenerator(),
    val informationSource: DataGenerator<Reference?> = NullDataGenerator(),
    val derivedFrom: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val reasonCode: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val reasonReference: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val note: ListDataGenerator<Annotation> = ListDataGenerator(0, AnnotationGenerator()),
    val dosage: ListDataGenerator<Dosage> = ListDataGenerator(0, DosageGenerator())
) : DomainResource<MedicationStatement> {
    override fun toFhir(): MedicationStatement =
        MedicationStatement(
            id = id.generate(),
            meta = meta.generate(),
            implicitRules = implicitRules.generate(),
            language = language.generate(),
            text = text.generate(),
            contained = contained.generate().filterNotNull(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            identifier = identifier.generate(),
            basedOn = basedOn.generate(),
            partOf = partOf.generate(),
            status = status.generate(),
            statusReason = statusReason.generate(),
            category = category.generate(),
            medication = medication.generate(),
            subject = subject.generate(),
            context = context.generate(),
            effective = effective.generate(),
            dateAsserted = dateAsserted.generate(),
            informationSource = informationSource.generate(),
            derivedFrom = derivedFrom.generate(),
            reasonCode = reasonCode.generate(),
            reasonReference = reasonReference.generate(),
            note = note.generate(),
            dosage = dosage.generate()
        )
}

class MedicationStatementMedicationGenerator : DataGenerator<DynamicValue<Any>>() {
    override fun generateInternal(): DynamicValue<Any> {
        return DynamicValues.reference(ReferenceGenerator().generate())
    }
}

fun medicationStatement(block: MedicationStatementGenerator.() -> Unit): MedicationStatement {
    val medicationStatement = MedicationStatementGenerator()
    medicationStatement.apply(block)
    return medicationStatement.toFhir()
}
