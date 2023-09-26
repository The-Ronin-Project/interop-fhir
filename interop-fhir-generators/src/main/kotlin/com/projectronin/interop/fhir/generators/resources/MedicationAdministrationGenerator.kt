package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.AnnotationGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.DynamicValues
import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.PeriodGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.generators.primitives.UriGenerator
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.MedicationAdministration
import com.projectronin.interop.fhir.r4.resource.MedicationAdministrationDosage
import com.projectronin.interop.fhir.r4.resource.MedicationAdministrationPerformer
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.interop.fhir.r4.valueset.MedicationAdministrationStatus
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class MedicationAdministrationGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    override val text: DataGenerator<Narrative?> = NullDataGenerator(),
    override val contained: ListDataGenerator<Resource<*>?> = ListDataGenerator(0, NullDataGenerator()),
    override val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    override val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),

    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val instantiates: ListDataGenerator<Uri> = ListDataGenerator(0, UriGenerator()),
    val partOf: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val status: CodeGenerator = CodeGenerator(MedicationAdministrationStatus::class),
    val statusReason: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val category: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val medication: DataGenerator<DynamicValue<Any>> = MedAdminMedicationGenerator(),
    val subject: DataGenerator<Reference> = ReferenceGenerator(),
    val context: DataGenerator<Reference?> = NullDataGenerator(),
    val supportingInformation: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val effective: DataGenerator<DynamicValue<Any>> = MedAdminEffectiveGenerator(),
    val performer: ListDataGenerator<MedicationAdministrationPerformer> = ListDataGenerator(0, MedAdminPerformerGenerator()),
    val reasonCode: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val reasonReference: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val request: DataGenerator<Reference?> = NullDataGenerator(),
    val device: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val note: ListDataGenerator<Annotation> = ListDataGenerator(0, AnnotationGenerator()),
    val dosage: DataGenerator<MedicationAdministrationDosage?> = NullDataGenerator(),
    val eventHistory: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator())
) : DomainResource<MedicationAdministration> {
    override fun toFhir(): MedicationAdministration =
        MedicationAdministration(
            id = id.generate(),
            meta = meta.generate(),
            implicitRules = implicitRules.generate(),
            language = language.generate(),
            text = text.generate(),
            contained = contained.generate().filterNotNull(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            identifier = identifier.generate(),
            instantiates = instantiates.generate(),
            partOf = partOf.generate(),
            status = status.generate(),
            statusReason = statusReason.generate(),
            category = category.generate(),
            medication = medication.generate(),
            subject = subject.generate(),
            context = context.generate(),
            supportingInformation = supportingInformation.generate(),
            effective = effective.generate(),
            performer = performer.generate(),
            reasonCode = reasonCode.generate(),
            reasonReference = reasonReference.generate(),
            request = request.generate(),
            device = device.generate(),
            note = note.generate(),
            dosage = dosage.generate(),
            eventHistory = eventHistory.generate()
        )
}

class MedAdminPerformerGenerator : DataGenerator<MedicationAdministrationPerformer>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val function: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val actor: DataGenerator<Reference> = ReferenceGenerator()

    override fun generateInternal() = MedicationAdministrationPerformer(
        id = id.generate(),
        extension = extension.generate(),
        modifierExtension = modifierExtension.generate(),
        function = function.generate(),
        actor = actor.generate()
    )
}

class MedAdminDosageGenerator : DataGenerator<MedicationAdministrationDosage>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val text: DataGenerator<FHIRString?> = NullDataGenerator()
    val site: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val route: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val method: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val dose: DataGenerator<Quantity?> = NullDataGenerator()
    val rate: DataGenerator<DynamicValue<Any>?> = NullDataGenerator()

    override fun generateInternal() = MedicationAdministrationDosage(
        id = id.generate(),
        extension = extension.generate(),
        modifierExtension = modifierExtension.generate(),
        text = text.generate(),
        site = site.generate(),
        route = route.generate(),
        method = method.generate(),
        dose = dose.generate(),
        rate = rate.generate()
    )
}

class MedAdminMedicationGenerator : DataGenerator<DynamicValue<Any>>() {
    override fun generateInternal(): DynamicValue<Any> {
        return DynamicValues.reference(ReferenceGenerator().generate())
    }
}

class MedAdminEffectiveGenerator : DataGenerator<DynamicValue<Any>>() {
    override fun generateInternal(): DynamicValue<Any> {
        return DynamicValues.period(PeriodGenerator().generate())
    }
}

fun medicationAdministration(block: MedicationAdministrationGenerator.() -> Unit): MedicationAdministration {
    val medicationAdministration = MedicationAdministrationGenerator()
    medicationAdministration.apply(block)
    return medicationAdministration.toFhir()
}

fun medAdminPerformer(block: MedAdminPerformerGenerator.() -> Unit): MedicationAdministrationPerformer {
    val medAdminPerformer = MedAdminPerformerGenerator()
    medAdminPerformer.apply(block)
    return medAdminPerformer.generate()
}

fun medAdminDosage(block: MedAdminDosageGenerator.() -> Unit): MedicationAdministrationDosage {
    val medAdminDosage = MedAdminDosageGenerator()
    medAdminDosage.apply(block)
    return medAdminDosage.generate()
}
