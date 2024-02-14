package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.AttachmentGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.DynamicValues
import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.PeriodGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.DiagnosticReport
import com.projectronin.interop.fhir.r4.resource.DiagnosticReportMedia
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class DiagnosticReportGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    override val text: DataGenerator<Narrative?> = NullDataGenerator(),
    override val contained: ListDataGenerator<Resource<*>?> = ListDataGenerator(0, NullDataGenerator()),
    override val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    override val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val status: CodeGenerator = CodeGenerator(),
    val code: DataGenerator<CodeableConcept> = CodeableConceptGenerator(),
    val subject: DataGenerator<Reference> = ReferenceGenerator(),
    val category: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val encounter: DataGenerator<Reference> = ReferenceGenerator(),
    val effective: DataGenerator<DynamicValue<Any>> = DiagnosticReportEffectiveGenerator(),
    val issued: DataGenerator<Instant?> = NullDataGenerator(),
    val performer: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val resultsInterpreter: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val specimen: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val result: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val imagingStudy: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val media: ListDataGenerator<DiagnosticReportMedia> = ListDataGenerator(0, DiagnosticReportMediaGenerator()),
    val conclusion: DataGenerator<FHIRString?> = NullDataGenerator(),
    val conclusionCode: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val presentedForm: ListDataGenerator<Attachment> = ListDataGenerator(0, AttachmentGenerator()),
) : DomainResource<DiagnosticReport> {
    override fun toFhir(): DiagnosticReport =
        DiagnosticReport(
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
            code = code.generate(),
            subject = subject.generate(),
            category = category.generate(),
            encounter = encounter.generate(),
            effective = effective.generate(),
            issued = issued.generate(),
            performer = performer.generate(),
            resultsInterpreter = resultsInterpreter.generate(),
            specimen = specimen.generate(),
            result = result.generate(),
            imagingStudy = imagingStudy.generate(),
            media = media.generate(),
            conclusion = conclusion.generate(),
            conclusionCode = conclusionCode.generate(),
            presentedForm = presentedForm.generate(),
        )
}

class DiagnosticReportMediaGenerator : DataGenerator<DiagnosticReportMedia>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val comment: DataGenerator<FHIRString?> = NullDataGenerator()
    val link: DataGenerator<Reference> = ReferenceGenerator()

    override fun generateInternal() =
        DiagnosticReportMedia(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            comment = comment.generate(),
            link = link.generate(),
        )
}

class DiagnosticReportEffectiveGenerator : DataGenerator<DynamicValue<Any>>() {
    override fun generateInternal(): DynamicValue<Any> {
        return DynamicValues.period(PeriodGenerator().generate())
    }
}

fun diagnosticReport(block: DiagnosticReportGenerator.() -> Unit): DiagnosticReport {
    val diagnosticReport = DiagnosticReportGenerator()
    diagnosticReport.apply(block)
    return diagnosticReport.toFhir()
}
