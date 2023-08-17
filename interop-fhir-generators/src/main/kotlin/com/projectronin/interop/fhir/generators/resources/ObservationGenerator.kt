package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.AnnotationGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Range
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Observation
import com.projectronin.interop.fhir.r4.resource.ObservationComponent
import com.projectronin.interop.fhir.r4.resource.ObservationReferenceRange
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.interop.fhir.r4.valueset.ObservationStatus
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class ObservationGenerator(
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
    val status: CodeGenerator = CodeGenerator(ObservationStatus::class),
    val category: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val code: DataGenerator<CodeableConcept> = CodeableConceptGenerator(),
    val subject: DataGenerator<Reference> = ReferenceGenerator(),
    val focus: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val encounter: DataGenerator<Reference?> = NullDataGenerator(),
    val effective: DataGenerator<DynamicValue<Any>?> = NullDataGenerator(),
    val issued: DataGenerator<Instant?> = NullDataGenerator(),
    val performer: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val note: ListDataGenerator<Annotation> = ListDataGenerator(0, AnnotationGenerator()),
    val value: DataGenerator<DynamicValue<Any>?> = NullDataGenerator(),
    val dataAbsentReason: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val interpretation: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val bodySite: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val method: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val specimen: DataGenerator<Reference?> = NullDataGenerator(),
    val device: DataGenerator<Reference?> = NullDataGenerator(),
    val referenceRange: ListDataGenerator<ObservationReferenceRange> = ListDataGenerator(
        0,
        ObservationReferenceRangeGenerator()
    ),
    val hasMember: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val derivedFrom: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val component: ListDataGenerator<ObservationComponent> = ListDataGenerator(0, ObservationComponentGenerator())
) : DomainResource<Observation> {
    override fun toFhir(): Observation =
        Observation(
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
            category = category.generate(),
            code = code.generate(),
            subject = subject.generate(),
            focus = focus.generate(),
            encounter = encounter.generate(),
            effective = effective.generate(),
            issued = issued.generate(),
            performer = performer.generate(),
            note = note.generate(),
            value = value.generate(),
            dataAbsentReason = dataAbsentReason.generate(),
            interpretation = interpretation.generate(),
            bodySite = bodySite.generate(),
            method = method.generate(),
            specimen = specimen.generate(),
            device = device.generate(),
            referenceRange = referenceRange.generate(),
            hasMember = hasMember.generate(),
            derivedFrom = derivedFrom.generate(),
            component = component.generate()
        )
}

class ObservationComponentGenerator : DataGenerator<ObservationComponent>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val code: DataGenerator<CodeableConcept> = CodeableConceptGenerator()
    val value: DataGenerator<DynamicValue<Any>?> = NullDataGenerator()
    val dataAbsentReason: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val interpretation: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator())
    val referenceRange: ListDataGenerator<ObservationReferenceRange> =
        ListDataGenerator(0, ObservationReferenceRangeGenerator())

    override fun generateInternal() = ObservationComponent(
        id = id.generate(),
        extension = extension.generate(),
        modifierExtension = modifierExtension.generate(),
        code = code.generate(),
        value = value.generate(),
        dataAbsentReason = dataAbsentReason.generate(),
        interpretation = interpretation.generate(),
        referenceRange = referenceRange.generate()
    )
}

class ObservationReferenceRangeGenerator : DataGenerator<ObservationReferenceRange>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val low: DataGenerator<SimpleQuantity?> = NullDataGenerator()
    val high: DataGenerator<SimpleQuantity?> = NullDataGenerator()
    val type: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val appliesTo: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator())
    val age: DataGenerator<Range?> = NullDataGenerator()
    val text: DataGenerator<FHIRString?> = NullDataGenerator()

    override fun generateInternal() = ObservationReferenceRange(
        id = id.generate(),
        extension = extension.generate(),
        modifierExtension = modifierExtension.generate(),
        low = low.generate(),
        high = high.generate(),
        type = type.generate(),
        appliesTo = appliesTo.generate(),
        age = age.generate(),
        text = text.generate()
    )
}

fun observation(block: ObservationGenerator.() -> Unit): Observation {
    val observation = ObservationGenerator()
    observation.apply(block)
    return observation.toFhir()
}

fun observationComponent(block: ObservationComponentGenerator.() -> Unit): ObservationComponent {
    val observationComponent = ObservationComponentGenerator()
    observationComponent.apply(block)
    return observationComponent.generate()
}

fun observationReferenceRange(block: ObservationReferenceRangeGenerator.() -> Unit): ObservationReferenceRange {
    val observationReferenceRange = ObservationReferenceRangeGenerator()
    observationReferenceRange.apply(block)
    return observationReferenceRange.generate()
}
