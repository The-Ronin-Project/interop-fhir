package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.AttachmentGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.generators.datatypes.HumanNameGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.generators.primitives.FHIRStringDataGenerator
import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.resource.Practitioner
import com.projectronin.interop.fhir.r4.resource.Qualification
import com.projectronin.interop.fhir.r4.valueset.AdministrativeGender
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class PractitionerGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    override val text: DataGenerator<Narrative?> = NullDataGenerator(),
    override val contained: ListDataGenerator<ContainedResource?> = ListDataGenerator(0, NullDataGenerator()),
    override val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    override val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val active: DataGenerator<FHIRBoolean?> = NullDataGenerator(),
    val name: ListDataGenerator<HumanName> = ListDataGenerator(1, HumanNameGenerator()),
    val telecom: ListDataGenerator<ContactPoint?> = ListDataGenerator(0, NullDataGenerator()),
    val address: ListDataGenerator<Address?> = ListDataGenerator(0, NullDataGenerator()),
    val gender: CodeGenerator = CodeGenerator(AdministrativeGender::class),
    val birthDate: DataGenerator<Date?> = NullDataGenerator(),
    val photo: ListDataGenerator<Attachment> = ListDataGenerator(0, AttachmentGenerator()),
    val qualification: ListDataGenerator<Qualification> = ListDataGenerator(0, QualificationGenerator()),
    val communication: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator())
) : DomainResource<Practitioner> {
    override fun toFhir(): Practitioner =
        Practitioner(
            id = id.generate(),
            meta = meta.generate(),
            implicitRules = implicitRules.generate(),
            language = language.generate(),
            text = text.generate(),
            contained = contained.generate().filterNotNull(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            identifier = identifier.generate(),
            active = active.generate(),
            name = name.generate(),
            telecom = telecom.generate().filterNotNull(),
            address = address.generate().filterNotNull(),
            gender = gender.generate(),
            birthDate = birthDate.generate(),
            photo = photo.generate(),
            qualification = qualification.generate(),
            communication = communication.generate()
        )
}

class QualificationGenerator : DataGenerator<Qualification>() {
    val id: FHIRStringDataGenerator = FHIRStringDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator())
    val code: DataGenerator<CodeableConcept> = CodeableConceptGenerator()
    val period: DataGenerator<Period?> = NullDataGenerator()
    val issuer: DataGenerator<Reference?> = NullDataGenerator()

    override fun generateInternal() = Qualification(
        id = id.generate(),
        extension = extension.generate(),
        modifierExtension = modifierExtension.generate(),
        identifier = identifier.generate(),
        code = code.generate(),
        period = period.generate(),
        issuer = issuer.generate()
    )
}

fun practitioner(block: PractitionerGenerator.() -> Unit): Practitioner {
    val practitioner = PractitionerGenerator()
    practitioner.apply(block)
    return practitioner.toFhir()
}

fun qualification(block: QualificationGenerator.() -> Unit): Qualification {
    val qualification = QualificationGenerator()
    qualification.apply(block)
    return qualification.generate()
}
