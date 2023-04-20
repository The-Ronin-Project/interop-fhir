package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.AddressGenerator
import com.projectronin.interop.fhir.generators.datatypes.AttachmentGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.ContactPointGenerator
import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.generators.datatypes.HumanNameGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.generators.primitives.DateGenerator
import com.projectronin.interop.fhir.generators.primitives.FHIRBooleanDataGenerator
import com.projectronin.interop.fhir.generators.primitives.FHIRStringDataGenerator
import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.resource.Patient
import com.projectronin.interop.fhir.r4.resource.PatientCommunication
import com.projectronin.interop.fhir.r4.resource.PatientContact
import com.projectronin.interop.fhir.r4.resource.PatientLink
import com.projectronin.interop.fhir.r4.valueset.AdministrativeGender
import com.projectronin.interop.fhir.r4.valueset.LinkType
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.EmptyListDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class PatientGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    override val text: DataGenerator<Narrative?> = NullDataGenerator(),
    override val contained: ListDataGenerator<ContainedResource?> = ListDataGenerator(0, NullDataGenerator()),
    override val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    override val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val active: FHIRBooleanDataGenerator = FHIRBooleanDataGenerator(),
    val name: ListDataGenerator<HumanName> = ListDataGenerator(1, HumanNameGenerator()),
    val telecom: ListDataGenerator<ContactPoint> = ListDataGenerator(1, ContactPointGenerator()),
    val gender: CodeGenerator = CodeGenerator(AdministrativeGender::class),
    val birthDate: DataGenerator<Date> = DateGenerator(),
    val deceased: DataGenerator<DynamicValue<Any>?> = NullDataGenerator(),
    val address: ListDataGenerator<Address> = ListDataGenerator(1, AddressGenerator()),
    val maritalStatus: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val multipleBirth: DataGenerator<DynamicValue<Any>?> = NullDataGenerator(),
    val photo: ListDataGenerator<Attachment> = ListDataGenerator(0, AttachmentGenerator()),
    val contact: ListDataGenerator<PatientContact> = ListDataGenerator(0, PatientContactGenerator()),
    val communication: ListDataGenerator<PatientCommunication> = ListDataGenerator(0, PatientCommunicationGenerator()),
    val generalPractitioner: ListDataGenerator<Reference?> = EmptyListDataGenerator(),
    val managingOrganization: DataGenerator<Reference?> = NullDataGenerator(),
    val link: ListDataGenerator<PatientLink> = ListDataGenerator(0, PatientLinkGenerator())
) : DomainResource<Patient> {
    override fun toFhir(): Patient =
        Patient(
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
            telecom = telecom.generate(),
            gender = gender.generate(),
            birthDate = birthDate.generate(),
            deceased = deceased.generate(),
            address = address.generate(),
            maritalStatus = maritalStatus.generate(),
            multipleBirth = multipleBirth.generate(),
            photo = photo.generate(),
            contact = contact.generate(),
            communication = communication.generate(),
            generalPractitioner = generalPractitioner.generate().filterNotNull(),
            managingOrganization = managingOrganization.generate(),
            link = link.generate()
        )
}

class PatientContactGenerator : DataGenerator<PatientContact>() {
    val id: FHIRStringDataGenerator = FHIRStringDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val relationship: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator())
    val name: DataGenerator<HumanName> = HumanNameGenerator()
    val telecom: ListDataGenerator<ContactPoint> = ListDataGenerator(1, ContactPointGenerator())
    val address: DataGenerator<Address> = AddressGenerator()
    val gender: CodeGenerator = CodeGenerator(AdministrativeGender::class)
    val organization: DataGenerator<Reference?> = NullDataGenerator()
    val period: DataGenerator<Period?> = NullDataGenerator()

    override fun generateInternal() = PatientContact(
        id = id.generate(),
        extension = extension.generate(),
        modifierExtension = modifierExtension.generate(),
        relationship = relationship.generate(),
        name = name.generate(),
        telecom = telecom.generate(),
        address = address.generate(),
        gender = gender.generate(),
        organization = organization.generate(),
        period = period.generate()
    )
}

class PatientCommunicationGenerator : DataGenerator<PatientCommunication>() {
    val id: FHIRStringDataGenerator = FHIRStringDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val language: DataGenerator<CodeableConcept> = CodeableConceptGenerator()
    val preferred: FHIRBooleanDataGenerator = FHIRBooleanDataGenerator()

    override fun generateInternal() = PatientCommunication(
        id = id.generate(),
        extension = extension.generate(),
        modifierExtension = modifierExtension.generate(),
        language = language.generate(),
        preferred = preferred.generate()
    )
}

class PatientLinkGenerator : DataGenerator<PatientLink>() {
    val id: FHIRStringDataGenerator = FHIRStringDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val other: DataGenerator<Reference?> = NullDataGenerator()
    val type: CodeGenerator = CodeGenerator(LinkType::class)

    override fun generateInternal() = PatientLink(
        id = id.generate(),
        extension = extension.generate(),
        modifierExtension = modifierExtension.generate(),
        other = other.generate(),
        type = type.generate()
    )
}

fun patient(block: PatientGenerator.() -> Unit): Patient {
    val patient = PatientGenerator()
    patient.apply(block)
    return patient.toFhir()
}

fun patientContact(block: PatientContactGenerator.() -> Unit): PatientContact {
    val patientContact = PatientContactGenerator()
    patientContact.apply(block)
    return patientContact.generate()
}

fun patientCommunication(block: PatientCommunicationGenerator.() -> Unit): PatientCommunication {
    val patientCommunication = PatientCommunicationGenerator()
    patientCommunication.apply(block)
    return patientCommunication.generate()
}

fun patientLink(block: PatientLinkGenerator.() -> Unit): PatientLink {
    val patientLink = PatientLinkGenerator()
    patientLink.apply(block)
    return patientLink.generate()
}
