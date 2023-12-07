package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.AttachmentGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.DocumentReference
import com.projectronin.interop.fhir.r4.resource.DocumentReferenceContent
import com.projectronin.interop.fhir.r4.resource.DocumentReferenceContext
import com.projectronin.interop.fhir.r4.resource.DocumentReferenceRelatesTo
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.interop.fhir.r4.valueset.DocumentReferenceStatus
import com.projectronin.interop.fhir.r4.valueset.DocumentRelationshipType
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class DocumentReferenceGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    override val text: DataGenerator<Narrative?> = NullDataGenerator(),
    override val contained: ListDataGenerator<Resource<*>?> = ListDataGenerator(0, NullDataGenerator()),
    override val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    override val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val masterIdentifier: DataGenerator<Identifier?> = NullDataGenerator(),
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val status: CodeGenerator = CodeGenerator(DocumentReferenceStatus::class),
    val docStatus: DataGenerator<Code?> = NullDataGenerator(),
    val type: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val category: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val subject: DataGenerator<Reference?> = NullDataGenerator(),
    val date: DataGenerator<Instant?> = NullDataGenerator(),
    val author: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val authenticator: DataGenerator<Reference?> = NullDataGenerator(),
    val custodian: DataGenerator<Reference?> = NullDataGenerator(),
    val relatesTo: ListDataGenerator<DocumentReferenceRelatesTo> =
        ListDataGenerator(
            0,
            DocumentReferenceRelatesToGenerator(),
        ),
    val description: DataGenerator<FHIRString?> = NullDataGenerator(),
    val securityLabel: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val content: ListDataGenerator<DocumentReferenceContent> =
        ListDataGenerator(
            1,
            DocumentReferenceContentGenerator(),
        ),
    val context: DataGenerator<DocumentReferenceContext?> = NullDataGenerator(),
) : DomainResource<DocumentReference> {
    override fun toFhir(): DocumentReference =
        DocumentReference(
            id = id.generate(),
            meta = meta.generate(),
            implicitRules = implicitRules.generate(),
            language = language.generate(),
            text = text.generate(),
            contained = contained.generate().filterNotNull(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            masterIdentifier = masterIdentifier.generate(),
            identifier = identifier.generate(),
            status = status.generate(),
            docStatus = docStatus.generate(),
            type = type.generate(),
            category = category.generate(),
            subject = subject.generate(),
            date = date.generate(),
            author = author.generate(),
            authenticator = authenticator.generate(),
            relatesTo = relatesTo.generate(),
            description = description.generate(),
            securityLabel = securityLabel.generate(),
            content = content.generate(),
            context = context.generate(),
        )
}

class DocumentReferenceContentGenerator : DataGenerator<DocumentReferenceContent>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val attachment: AttachmentGenerator = AttachmentGenerator()
    val format: DataGenerator<Coding?> = NullDataGenerator()

    override fun generateInternal(): DocumentReferenceContent {
        return DocumentReferenceContent(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            attachment = attachment.generate(),
            format = format.generate(),
        )
    }
}

class DocumentReferenceContextGenerator : DataGenerator<DocumentReferenceContext>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val encounter: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator())
    val event: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator())
    val period: DataGenerator<Period?> = NullDataGenerator()
    val facilityType: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val practiceSetting: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val sourcePatientInfo: DataGenerator<Reference?> = NullDataGenerator()
    val related: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator())

    override fun generateInternal(): DocumentReferenceContext {
        return DocumentReferenceContext(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            encounter = encounter.generate(),
            event = event.generate(),
            period = period.generate(),
            facilityType = facilityType.generate(),
            practiceSetting = practiceSetting.generate(),
            sourcePatientInfo = sourcePatientInfo.generate(),
            related = related.generate(),
        )
    }
}

class DocumentReferenceRelatesToGenerator : DataGenerator<DocumentReferenceRelatesTo>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val code: CodeGenerator = CodeGenerator(DocumentRelationshipType::class)

    override fun generateInternal(): DocumentReferenceRelatesTo {
        return DocumentReferenceRelatesTo(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            code = code.generate(),
            target = reference("DocumentReference", id.generate()?.value),
        )
    }
}

fun documentReference(block: DocumentReferenceGenerator.() -> Unit): DocumentReference {
    val docReference = DocumentReferenceGenerator()
    docReference.apply(block)
    return docReference.toFhir()
}

fun documentReferenceContent(block: DocumentReferenceContentGenerator.() -> Unit): DocumentReferenceContent {
    val content = DocumentReferenceContentGenerator()
    content.apply(block)
    return content.generate()
}

fun documentReferenceContext(block: DocumentReferenceContextGenerator.() -> Unit): DocumentReferenceContext {
    val context = DocumentReferenceContextGenerator()
    context.apply(block)
    return context.generate()
}

fun documentReferenceRelatesTo(block: DocumentReferenceRelatesToGenerator.() -> Unit): DocumentReferenceRelatesTo {
    val relatesTo = DocumentReferenceRelatesToGenerator()
    relatesTo.apply(block)
    return relatesTo.generate()
}
