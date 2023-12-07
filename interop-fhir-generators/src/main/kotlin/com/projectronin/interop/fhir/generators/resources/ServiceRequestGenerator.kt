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
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.interop.fhir.r4.resource.ServiceRequest
import com.projectronin.interop.fhir.r4.valueset.RequestIntent
import com.projectronin.interop.fhir.r4.valueset.RequestStatus
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class ServiceRequestGenerator(
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
    val replaces: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val requisition: DataGenerator<Identifier?> = NullDataGenerator(),
    val status: DataGenerator<Code?> = CodeGenerator(RequestStatus::class),
    val intent: DataGenerator<Code?> = CodeGenerator(RequestIntent::class),
    val category: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val priority: DataGenerator<Code?> = NullDataGenerator(),
    val doNotPerform: DataGenerator<FHIRBoolean?> = NullDataGenerator(),
    val code: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val orderDetail: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val quantity: DataGenerator<DynamicValue<Any>?> = NullDataGenerator(),
    val subject: DataGenerator<Reference> = ReferenceGenerator(),
    val encounter: DataGenerator<Reference?> = NullDataGenerator(),
    val occurrence: DataGenerator<DynamicValue<Any>?> = NullDataGenerator(),
    val asNeeded: DataGenerator<DynamicValue<Any>?> = NullDataGenerator(),
    val authoredOn: DataGenerator<DateTime?> = NullDataGenerator(),
    val requester: DataGenerator<Reference?> = NullDataGenerator(),
    val performerType: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val performer: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val locationCode: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val locationReference: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val reasonCode: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val reasonReference: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val insurance: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val supportingInfo: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val specimen: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val bodySite: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val note: ListDataGenerator<Annotation> = ListDataGenerator(0, AnnotationGenerator()),
    val patientInstruction: DataGenerator<FHIRString?> = NullDataGenerator(),
    val relevantHistory: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
) : DomainResource<ServiceRequest> {
    override fun toFhir(): ServiceRequest =
        ServiceRequest(
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
            replaces = replaces.generate(),
            requisition = requisition.generate(),
            status = status.generate(),
            intent = intent.generate(),
            category = category.generate(),
            priority = priority.generate(),
            doNotPerform = doNotPerform.generate(),
            code = code.generate(),
            orderDetail = orderDetail.generate(),
            quantity = quantity.generate(),
            subject = subject.generate(),
            encounter = encounter.generate(),
            occurrence = occurrence.generate(),
            asNeeded = asNeeded.generate(),
            authoredOn = authoredOn.generate(),
            requester = requester.generate(),
            performerType = performerType.generate(),
            performer = performer.generate(),
            locationCode = locationCode.generate(),
            locationReference = locationReference.generate(),
            reasonCode = reasonCode.generate(),
            reasonReference = reasonReference.generate(),
            insurance = insurance.generate(),
            supportingInfo = supportingInfo.generate(),
            specimen = specimen.generate(),
            bodySite = bodySite.generate(),
            note = note.generate(),
            patientInstruction = patientInstruction.generate(),
            relevantHistory = relevantHistory.generate(),
        )
}

fun serviceRequest(block: ServiceRequestGenerator.() -> Unit): ServiceRequest {
    val serviceRequest = ServiceRequestGenerator()
    serviceRequest.apply(block)
    return serviceRequest.toFhir()
}
