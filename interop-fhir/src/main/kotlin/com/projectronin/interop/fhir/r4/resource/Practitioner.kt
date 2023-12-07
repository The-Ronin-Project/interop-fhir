package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.event.interop.internal.v1.ResourceType
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
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
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement
import com.projectronin.interop.fhir.r4.valueset.AdministrativeGender
import com.projectronin.interop.fhir.validate.annotation.RequiredField
import com.projectronin.interop.fhir.validate.annotation.RequiredValueSet
import com.projectronin.interop.fhir.validate.annotation.SupportedReferenceTypes

/**
 * A person who is directly or indirectly involved in the provisioning of healthcare.
 */
@JsonSerialize(using = PractitionerSerializer::class)
@JsonDeserialize(using = PractitionerDeserializer::class)
@JsonTypeName("Practitioner")
data class Practitioner(
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
    val address: List<Address> = listOf(),
    @RequiredValueSet(AdministrativeGender::class)
    val gender: Code? = null,
    val birthDate: Date? = null,
    val photo: List<Attachment> = listOf(),
    val qualification: List<Qualification> = listOf(),
    val communication: List<CodeableConcept> = listOf(),
) : DomainResource<Practitioner> {
    override val resourceType: String = "Practitioner"
}

class PractitionerSerializer : BaseFHIRSerializer<Practitioner>(Practitioner::class.java)

class PractitionerDeserializer : BaseFHIRDeserializer<Practitioner>(Practitioner::class.java)

/**
 * The official certifications, training, and licenses that authorize or otherwise pertain to the provision of care by
 * the practitioner. For example, a medical license issued by a medical board authorizing the practitioner to practice
 * medicine within a certain locality.
 */
@JsonSerialize(using = QualificationSerializer::class)
@JsonDeserialize(using = QualificationDeserializer::class)
data class Qualification(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    @RequiredField
    val code: CodeableConcept?,
    val period: Period? = null,
    @SupportedReferenceTypes(ResourceType.Organization)
    val issuer: Reference? = null,
) : BackboneElement<Qualification>

class QualificationSerializer : BaseFHIRSerializer<Qualification>(Qualification::class.java)

class QualificationDeserializer : BaseFHIRDeserializer<Qualification>(Qualification::class.java)
