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
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.CarePlan
import com.projectronin.interop.fhir.r4.resource.CarePlanActivity
import com.projectronin.interop.fhir.r4.resource.CarePlanDetail
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.valueset.CarePlanActivityStatus
import com.projectronin.interop.fhir.r4.valueset.CarePlanIntent
import com.projectronin.interop.fhir.r4.valueset.RequestStatus
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class CarePlanGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    override val text: DataGenerator<Narrative?> = NullDataGenerator(),
    override val contained: ListDataGenerator<ContainedResource?> = ListDataGenerator(0, NullDataGenerator()),
    override val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    override val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val instantiatesCanonical: ListDataGenerator<Canonical> = ListDataGenerator(0, CanonicalGenerator()),
    val instantiatesUri: ListDataGenerator<Uri> = ListDataGenerator(0, UriGenerator()),
    val basedOn: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val replaces: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val partOf: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val status: CodeGenerator = CodeGenerator(RequestStatus::class),
    val intent: CodeGenerator = CodeGenerator(CarePlanIntent::class),
    val category: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val title: DataGenerator<FHIRString?> = NullDataGenerator(),
    val description: DataGenerator<FHIRString?> = NullDataGenerator(),
    val subject: DataGenerator<Reference> = ReferenceGenerator(),
    val encounter: DataGenerator<Reference?> = NullDataGenerator(),
    val period: DataGenerator<Period?> = NullDataGenerator(),
    val created: DataGenerator<DateTime?> = NullDataGenerator(),
    val author: DataGenerator<Reference?> = NullDataGenerator(),
    val contributor: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val careTeam: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val addresses: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val supportingInfo: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val goal: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val activity: ListDataGenerator<CarePlanActivity> = ListDataGenerator(0, CarePlanActivityGenerator()),
    val note: ListDataGenerator<Annotation> = ListDataGenerator(0, AnnotationGenerator())
) : DomainResource<CarePlan> {
    override fun toFhir(): CarePlan =
        CarePlan(
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
            partOf = partOf.generate(),
            status = status.generate(),
            intent = intent.generate(),
            category = category.generate(),
            title = title.generate(),
            description = description.generate(),
            subject = subject.generate(),
            encounter = encounter.generate(),
            period = period.generate(),
            created = created.generate(),
            author = author.generate(),
            contributor = contributor.generate(),
            careTeam = careTeam.generate(),
            addresses = addresses.generate(),
            supportingInfo = supportingInfo.generate(),
            goal = goal.generate(),
            activity = activity.generate(),
            note = note.generate()
        )
}

class CarePlanActivityGenerator : DataGenerator<CarePlanActivity>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val outcomeCodeableConcept: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator())
    val outcomeReference: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator())
    val progress: ListDataGenerator<Annotation> = ListDataGenerator(0, AnnotationGenerator())
    val reference: DataGenerator<Reference?> = NullDataGenerator()
    val detail: DataGenerator<CarePlanDetail?> = NullDataGenerator()

    override fun generateInternal(): CarePlanActivity {
        return CarePlanActivity(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            outcomeCodeableConcept = outcomeCodeableConcept.generate(),
            outcomeReference = outcomeReference.generate(),
            progress = progress.generate(),
            reference = reference.generate(),
            detail = detail.generate()
        )
    }
}

class CarePlanDetailGenerator : DataGenerator<CarePlanDetail?>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val kind: DataGenerator<Code?> = NullDataGenerator()
    val instantiatesCanonical: ListDataGenerator<Canonical> = ListDataGenerator(0, CanonicalGenerator())
    val instantiatesUri: ListDataGenerator<Uri> = ListDataGenerator(0, UriGenerator())
    val code: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val reasonCode: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator())
    val reasonReference: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator())
    val goal: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator())
    val status: CodeGenerator = CodeGenerator(CarePlanActivityStatus::class)
    val statusReason: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val doNotPerform: DataGenerator<FHIRBoolean?> = NullDataGenerator()
    val scheduled: DataGenerator<DynamicValue<Any>?> = NullDataGenerator()
    val location: DataGenerator<Reference?> = NullDataGenerator()
    val performer: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator())
    val product: DataGenerator<DynamicValue<Any>?> = NullDataGenerator()
    val dailyAmount: DataGenerator<SimpleQuantity?> = NullDataGenerator()
    val quantity: DataGenerator<SimpleQuantity?> = NullDataGenerator()
    val description: DataGenerator<FHIRString?> = NullDataGenerator()

    override fun generateInternal() = CarePlanDetail(
        id = id.generate(),
        extension = extension.generate(),
        modifierExtension = modifierExtension.generate(),
        kind = kind.generate(),
        instantiatesCanonical = instantiatesCanonical.generate(),
        instantiatesUri = instantiatesUri.generate(),
        code = code.generate(),
        reasonCode = reasonCode.generate(),
        reasonReference = reasonReference.generate(),
        goal = goal.generate(),
        status = status.generate(),
        statusReason = statusReason.generate(),
        doNotPerform = doNotPerform.generate(),
        scheduled = scheduled.generate(),
        location = location.generate(),
        performer = performer.generate(),
        product = product.generate(),
        dailyAmount = dailyAmount.generate(),
        quantity = quantity.generate(),
        description = description.generate()
    )
}

fun carePlan(block: CarePlanGenerator.() -> Unit): CarePlan {
    val carePlan = CarePlanGenerator()
    carePlan.apply(block)
    return carePlan.toFhir()
}

fun carePlanActivity(block: CarePlanActivityGenerator.() -> Unit): CarePlanActivity {
    val activity = CarePlanActivityGenerator()
    activity.apply(block)
    return activity.generate()
}

fun carePlanDetail(block: CarePlanDetailGenerator.() -> Unit): CarePlanDetail? {
    val detail = CarePlanDetailGenerator()
    detail.apply(block)
    return detail.generate()
}
