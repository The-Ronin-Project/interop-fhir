package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.event.interop.internal.v1.ResourceType
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Dosage
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.MedicationStatementStatus
import com.projectronin.interop.fhir.validate.annotation.RequiredField
import com.projectronin.interop.fhir.validate.annotation.RequiredValueSet
import com.projectronin.interop.fhir.validate.annotation.SupportedDynamicValueTypes
import com.projectronin.interop.fhir.validate.annotation.SupportedReferenceTypes

@JsonDeserialize(using = MedicationStatementDeserializer::class)
@JsonSerialize(using = MedicationStatementSerializer::class)
@JsonTypeName("MedicationStatement")
data class MedicationStatement(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<Resource<*>> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    @SupportedReferenceTypes(ResourceType.MedicationRequest, ResourceType.CarePlan, ResourceType.ServiceRequest)
    val basedOn: List<Reference> = listOf(),
    @SupportedReferenceTypes(
        ResourceType.MedicationAdministration,
        ResourceType.MedicationDispense,
        ResourceType.MedicationStatement,
        ResourceType.Procedure,
        ResourceType.Observation,
    )
    val partOf: List<Reference> = listOf(),
    @RequiredField
    @RequiredValueSet(MedicationStatementStatus::class)
    val status: Code? = null,
    val statusReason: List<CodeableConcept> = listOf(),
    val category: CodeableConcept? = null,
    @RequiredField
    @SupportedDynamicValueTypes(DynamicValueType.CODEABLE_CONCEPT, DynamicValueType.REFERENCE)
    @SupportedReferenceTypes(ResourceType.Medication)
    val medication: DynamicValue<Any>? = null,
    @RequiredField
    @SupportedReferenceTypes(ResourceType.Patient, ResourceType.Group)
    val subject: Reference? = null,
    @SupportedReferenceTypes(ResourceType.Encounter, ResourceType.EpisodeOfCare)
    val context: Reference? = null,
    @SupportedDynamicValueTypes(DynamicValueType.DATE_TIME, DynamicValueType.PERIOD)
    val effective: DynamicValue<Any>? = null,
    val dateAsserted: DateTime? = null,
    @SupportedReferenceTypes(
        ResourceType.Patient,
        ResourceType.Practitioner,
        ResourceType.PractitionerRole,
        ResourceType.RelatedPerson,
        ResourceType.Organization,
    )
    val informationSource: Reference? = null,
    val derivedFrom: List<Reference> = listOf(),
    val reasonCode: List<CodeableConcept> = listOf(),
    @SupportedReferenceTypes(ResourceType.Condition, ResourceType.Observation, ResourceType.DiagnosticReport)
    val reasonReference: List<Reference> = listOf(),
    val note: List<Annotation> = listOf(),
    val dosage: List<Dosage> = listOf(),
) : DomainResource<MedicationStatement> {
    override val resourceType: String = "MedicationStatement"
}

class MedicationStatementDeserializer : BaseFHIRDeserializer<MedicationStatement>(MedicationStatement::class.java)

class MedicationStatementSerializer : BaseFHIRSerializer<MedicationStatement>(MedicationStatement::class.java)
