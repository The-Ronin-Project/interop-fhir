package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.AnnotationGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.generators.primitives.CanonicalGenerator
import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.generators.primitives.UriGenerator
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Procedure
import com.projectronin.interop.fhir.r4.resource.ProcedureFocalDevice
import com.projectronin.interop.fhir.r4.resource.ProcedurePerformer
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.interop.fhir.r4.valueset.EventStatus
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class ProcedureGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    override val text: DataGenerator<Narrative?> = NullDataGenerator(),
    override val contained: ListDataGenerator<Resource<*>?> = ListDataGenerator(0, NullDataGenerator()),
    override val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    override val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val instantiatesCanonical: ListDataGenerator<Canonical> = ListDataGenerator(0, CanonicalGenerator()),
    val instantiatesUri: ListDataGenerator<Uri> = ListDataGenerator(0, UriGenerator()),
    val basedOn: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val partOf: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val status: DataGenerator<Code?> = CodeGenerator(EventStatus::class),
    val statusReason: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val category: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val code: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val subject: DataGenerator<Reference> = ReferenceGenerator(),
    val encounter: DataGenerator<Reference?> = NullDataGenerator(),
    val performed: DataGenerator<DynamicValue<Any>?> = NullDataGenerator(),
    val recorder: DataGenerator<Reference?> = NullDataGenerator(),
    val asserter: DataGenerator<Reference?> = NullDataGenerator(),
    val performer: ListDataGenerator<ProcedurePerformer> = ListDataGenerator(0, ProcedurePerformerGenerator()),
    val location: DataGenerator<Reference?> = NullDataGenerator(),
    val reasonCode: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val reasonReference: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val bodySite: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val outcome: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val report: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val complication: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val complicationDetail: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val followUp: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val note: ListDataGenerator<Annotation> = ListDataGenerator(0, AnnotationGenerator()),
    val focalDevice: ListDataGenerator<ProcedureFocalDevice> = ListDataGenerator(0, ProcedureFocalDeviceGenerator()),
    val usedReference: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val usedCode: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
) : DomainResource<Procedure> {
    override fun toFhir(): Procedure =
        Procedure(
            id = id.generate(),
            meta = meta.generate(),
            implicitRules = implicitRules.generate(),
            language = language.generate(),
            text = text.generate(),
            contained = contained.generate().filterNotNull(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            identifier = identifier.generate(),
            instantiatesCanonical = instantiatesCanonical.generate(),
            instantiatesUri = instantiatesUri.generate(),
            basedOn = basedOn.generate(),
            partOf = partOf.generate(),
            status = status.generate(),
            statusReason = statusReason.generate(),
            category = category.generate(),
            code = code.generate(),
            subject = subject.generate(),
            encounter = encounter.generate(),
            performed = performed.generate(),
            recorder = recorder.generate(),
            asserter = asserter.generate(),
            performer = performer.generate(),
            location = location.generate(),
            reasonCode = reasonCode.generate(),
            reasonReference = reasonReference.generate(),
            bodySite = bodySite.generate(),
            outcome = outcome.generate(),
            report = report.generate(),
            complication = complication.generate(),
            complicationDetail = complicationDetail.generate(),
            followUp = followUp.generate(),
            note = note.generate(),
            focalDevice = focalDevice.generate(),
            usedReference = usedReference.generate(),
            usedCode = usedCode.generate(),
        )
}

class ProcedurePerformerGenerator : DataGenerator<ProcedurePerformer>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val function: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val actor: DataGenerator<Reference> = ReferenceGenerator()
    val onBehalfOf: DataGenerator<Reference?> = NullDataGenerator()

    override fun generateInternal() =
        ProcedurePerformer(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            function = function.generate(),
            actor = actor.generate(),
            onBehalfOf = onBehalfOf.generate(),
        )
}

class ProcedureFocalDeviceGenerator : DataGenerator<ProcedureFocalDevice>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val action: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val manipulated: DataGenerator<Reference> = ReferenceGenerator()

    override fun generateInternal() =
        ProcedureFocalDevice(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            action = action.generate(),
            manipulated = manipulated.generate(),
        )
}

fun procedure(block: ProcedureGenerator.() -> Unit): Procedure {
    val procedure = ProcedureGenerator()
    procedure.apply(block)
    return procedure.toFhir()
}

fun procedurePerformer(block: ProcedurePerformerGenerator.() -> Unit): ProcedurePerformer {
    val procedurePerformer = ProcedurePerformerGenerator()
    procedurePerformer.apply(block)
    return procedurePerformer.generate()
}

fun procedureFocalDevice(block: ProcedureFocalDeviceGenerator.() -> Unit): ProcedureFocalDevice {
    val procedureFocalDevice = ProcedureFocalDeviceGenerator()
    procedureFocalDevice.apply(block)
    return procedureFocalDevice.generate()
}
