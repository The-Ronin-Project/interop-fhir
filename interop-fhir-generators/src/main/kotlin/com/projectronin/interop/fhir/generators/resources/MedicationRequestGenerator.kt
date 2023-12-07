package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.AnnotationGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.DosageGenerator
import com.projectronin.interop.fhir.generators.datatypes.DynamicValues
import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.CanonicalGenerator
import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.generators.primitives.FHIRBooleanDataGenerator
import com.projectronin.interop.fhir.generators.primitives.UriGenerator
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Dosage
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.DispenseRequest
import com.projectronin.interop.fhir.r4.resource.InitialFill
import com.projectronin.interop.fhir.r4.resource.MedicationRequest
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.interop.fhir.r4.resource.Substitution
import com.projectronin.interop.fhir.r4.valueset.MedicationRequestIntent
import com.projectronin.interop.fhir.r4.valueset.MedicationRequestStatus
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class MedicationRequestGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    override val text: DataGenerator<Narrative?> = NullDataGenerator(),
    override val contained: ListDataGenerator<Resource<*>?> = ListDataGenerator(0, NullDataGenerator()),
    override val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    override val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val status: CodeGenerator = CodeGenerator(MedicationRequestStatus::class),
    val statusReason: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val intent: CodeGenerator = CodeGenerator(MedicationRequestIntent::class),
    val category: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val priority: DataGenerator<Code?> = NullDataGenerator(),
    val doNotPerform: DataGenerator<FHIRBoolean?> = NullDataGenerator(),
    val reported: DataGenerator<DynamicValue<Any>?> = NullDataGenerator(),
    val medication: DataGenerator<DynamicValue<Any>> = MedicationRequestMedicationGenerator(),
    val subject: DataGenerator<Reference> = ReferenceGenerator(),
    val encounter: DataGenerator<Reference?> = NullDataGenerator(),
    val supportingInformation: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val authoredOn: DataGenerator<DateTime?> = NullDataGenerator(),
    val requester: DataGenerator<Reference?> = NullDataGenerator(),
    val performer: DataGenerator<Reference?> = NullDataGenerator(),
    val performerType: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val recorder: DataGenerator<Reference?> = NullDataGenerator(),
    val reasonCode: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val reasonReference: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val instantiatesCanonical: ListDataGenerator<Canonical> = ListDataGenerator(0, CanonicalGenerator()),
    val instantiatesUri: ListDataGenerator<Uri> = ListDataGenerator(0, UriGenerator()),
    val basedOn: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val groupIdentifier: DataGenerator<Identifier?> = NullDataGenerator(),
    val courseOfTherapyType: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val insurance: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val note: ListDataGenerator<Annotation> = ListDataGenerator(0, AnnotationGenerator()),
    val dosageInstruction: ListDataGenerator<Dosage> = ListDataGenerator(0, DosageGenerator()),
    val dispenseRequest: DataGenerator<DispenseRequest?> = NullDataGenerator(),
    val substitution: DataGenerator<Substitution?> = NullDataGenerator(),
    val priorPrescription: DataGenerator<Reference?> = NullDataGenerator(),
    val detectedIssue: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val eventHistory: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
) : DomainResource<MedicationRequest> {
    override fun toFhir(): MedicationRequest =
        MedicationRequest(
            id = id.generate(),
            meta = meta.generate(),
            implicitRules = implicitRules.generate(),
            language = language.generate(),
            text = text.generate(),
            contained = contained.generate().filterNotNull(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            identifier = identifier.generate(),
            status = status.generate(),
            statusReason = statusReason.generate(),
            intent = intent.generate(),
            category = category.generate(),
            priority = priority.generate(),
            doNotPerform = doNotPerform.generate(),
            reported = reported.generate(),
            medication = medication.generate(),
            subject = subject.generate(),
            encounter = encounter.generate(),
            supportingInformation = supportingInformation.generate(),
            authoredOn = authoredOn.generate(),
            requester = requester.generate(),
            performer = performer.generate(),
            performerType = performerType.generate(),
            recorder = recorder.generate(),
            reasonCode = reasonCode.generate(),
            reasonReference = reasonReference.generate(),
            instantiatesCanonical = instantiatesCanonical.generate(),
            instantiatesUri = instantiatesUri.generate(),
            basedOn = basedOn.generate(),
            groupIdentifier = groupIdentifier.generate(),
            courseOfTherapyType = courseOfTherapyType.generate(),
            insurance = insurance.generate(),
            note = note.generate(),
            dosageInstruction = dosageInstruction.generate(),
            dispenseRequest = dispenseRequest.generate(),
            substitution = substitution.generate(),
            priorPrescription = priorPrescription.generate(),
            detectedIssue = detectedIssue.generate(),
            eventHistory = eventHistory.generate(),
        )
}

class MedicationRequestMedicationGenerator : DataGenerator<DynamicValue<Any>>() {
    override fun generateInternal(): DynamicValue<Any> {
        return DynamicValues.reference(reference("Medication"))
    }
}

class MedicationRequestDispenseRequestGenerator : DataGenerator<DispenseRequest>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val initialFill: DataGenerator<InitialFill?> = NullDataGenerator()
    val dispenseInterval: DataGenerator<Duration?> = NullDataGenerator()
    val validityPeriod: DataGenerator<Period?> = NullDataGenerator()
    val numberOfRepeatsAllowed: DataGenerator<UnsignedInt?> = NullDataGenerator()
    val quantity: DataGenerator<SimpleQuantity?> = NullDataGenerator()
    val expectedSupplyDuration: DataGenerator<Duration?> = NullDataGenerator()
    val performer: DataGenerator<Reference?> = NullDataGenerator()

    override fun generateInternal(): DispenseRequest {
        return DispenseRequest(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            initialFill = initialFill.generate(),
            dispenseInterval = dispenseInterval.generate(),
            validityPeriod = validityPeriod.generate(),
            numberOfRepeatsAllowed = numberOfRepeatsAllowed.generate(),
            quantity = quantity.generate(),
            expectedSupplyDuration = expectedSupplyDuration.generate(),
            performer = performer.generate(),
        )
    }
}

class MedicationRequestInitialFillGenerator : DataGenerator<InitialFill>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val quantity: DataGenerator<SimpleQuantity?> = NullDataGenerator()
    val duration: DataGenerator<Duration?> = NullDataGenerator()

    override fun generateInternal(): InitialFill {
        return InitialFill(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            quantity = quantity.generate(),
            duration = duration.generate(),
        )
    }
}

class MedicationRequestSubstitutionGenerator : DataGenerator<Substitution>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val allowed: DataGenerator<DynamicValue<Any>> = MedicationRequestSubstitutionAllowedGenerator()
    val reason: DataGenerator<CodeableConcept?> = NullDataGenerator()

    override fun generateInternal(): Substitution {
        return Substitution(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            allowed = allowed.generate(),
            reason = reason.generate(),
        )
    }
}

class MedicationRequestSubstitutionAllowedGenerator : DataGenerator<DynamicValue<Any>>() {
    override fun generateInternal(): DynamicValue<Any> {
        return DynamicValues.boolean(FHIRBooleanDataGenerator().generateRequired())
    }
}

fun medicationRequest(block: MedicationRequestGenerator.() -> Unit): MedicationRequest {
    val medicationRequest = MedicationRequestGenerator()
    medicationRequest.apply(block)
    return medicationRequest.toFhir()
}

fun medicationRequestDispenseRequest(block: MedicationRequestDispenseRequestGenerator.() -> Unit): DispenseRequest {
    val dispenseRequest = MedicationRequestDispenseRequestGenerator()
    dispenseRequest.apply(block)
    return dispenseRequest.generate()
}

fun medicationRequestInitialFill(block: MedicationRequestInitialFillGenerator.() -> Unit): InitialFill {
    val initialFill = MedicationRequestInitialFillGenerator()
    initialFill.apply(block)
    return initialFill.generate()
}

fun medicationRequestSubstitution(block: MedicationRequestSubstitutionGenerator.() -> Unit): Substitution {
    val substitution = MedicationRequestSubstitutionGenerator()
    substitution.apply(block)
    return substitution.generate()
}
