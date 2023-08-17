package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
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
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement

/**
 * Demographics and other administrative information about an individual or animal receiving care or other
 * health-related services.
 */
@JsonDeserialize(using = PatientDeserializer::class)
@JsonSerialize(using = PatientSerializer::class)
@JsonTypeName("Patient")
data class Patient(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<Resource<*>> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val active: FHIRBoolean? = null,
    val name: List<HumanName> = listOf(),
    val telecom: List<ContactPoint> = listOf(),
    val gender: Code? = null,
    val birthDate: Date? = null,
    val deceased: DynamicValue<Any>? = null,
    val address: List<Address> = listOf(),
    val maritalStatus: CodeableConcept? = null,
    val multipleBirth: DynamicValue<Any>? = null,
    val photo: List<Attachment> = listOf(),
    val contact: List<PatientContact> = listOf(),
    val communication: List<PatientCommunication> = listOf(),
    val generalPractitioner: List<Reference> = listOf(),
    val managingOrganization: Reference? = null,
    val link: List<PatientLink> = listOf()
) : DomainResource<Patient> {
    override val resourceType: String = "Patient"
}

class PatientDeserializer : BaseFHIRDeserializer<Patient>(Patient::class.java)
class PatientSerializer : BaseFHIRSerializer<Patient>(Patient::class.java)

/**
 * A language which may be used to communicate with the patient about his or her health.
 */
@JsonSerialize(using = PatientCommunicationSerializer::class)
@JsonDeserialize(using = PatientCommunicationDeserializer::class)
data class PatientCommunication(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val language: CodeableConcept?,
    val preferred: FHIRBoolean? = null
) : BackboneElement<PatientCommunication>

class PatientCommunicationSerializer : BaseFHIRSerializer<PatientCommunication>(PatientCommunication::class.java)
class PatientCommunicationDeserializer : BaseFHIRDeserializer<PatientCommunication>(PatientCommunication::class.java)

/**
 * Contact covers all kinds of contact parties: family members, business contacts, guardians, caregivers. Not applicable
 * to register pedigree and family ties beyond use of having contact.
 */
@JsonSerialize(using = ContactSerializer::class)
@JsonDeserialize(using = ContactDeserializer::class)
data class PatientContact(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val relationship: List<CodeableConcept> = listOf(),
    val name: HumanName? = null,
    val telecom: List<ContactPoint> = listOf(),
    val address: Address? = null,
    val gender: Code? = null,
    val organization: Reference? = null,
    val period: Period? = null
) : BackboneElement<PatientContact>

class ContactSerializer : BaseFHIRSerializer<PatientContact>(PatientContact::class.java)
class ContactDeserializer : BaseFHIRDeserializer<PatientContact>(PatientContact::class.java)

/**
 * Link to another patient resource that concerns the same actual patient.
 */
@JsonSerialize(using = PatientLinkSerializer::class)
@JsonDeserialize(using = PatientLinkDeserializer::class)
data class PatientLink(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val other: Reference?,
    val type: Code?
) : BackboneElement<PatientLink>

class PatientLinkSerializer : BaseFHIRSerializer<PatientLink>(PatientLink::class.java)
class PatientLinkDeserializer : BaseFHIRDeserializer<PatientLink>(PatientLink::class.java)
