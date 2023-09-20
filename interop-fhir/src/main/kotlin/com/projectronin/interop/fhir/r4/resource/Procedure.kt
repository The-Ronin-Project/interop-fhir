package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
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
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement

/**
 * An action that is or was performed on or for a patient. This can be a physical intervention like an operation, or
 * less invasive like long term services, counseling, or hypnotherapy.
 */
@JsonDeserialize(using = ProcedureDeserializer::class)
@JsonSerialize(using = ProcedureSerializer::class)
@JsonTypeName("Procedure")
data class Procedure(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<Resource<*>> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val instantiatesCanonical: List<Canonical> = listOf(),
    val instantiatesUri: List<Uri> = listOf(),
    val basedOn: List<Reference> = listOf(),
    val partOf: List<Reference> = listOf(),
    val status: Code?,
    val statusReason: CodeableConcept? = null,
    val category: CodeableConcept? = null,
    val code: CodeableConcept? = null,
    val subject: Reference?,
    val encounter: Reference? = null,
    val performed: DynamicValue<Any>? = null,
    val recorder: Reference? = null,
    val asserter: Reference? = null,
    val performer: List<ProcedurePerformer> = listOf(),
    val location: Reference? = null,
    val reasonCode: List<CodeableConcept> = listOf(),
    val reasonReference: List<Reference> = listOf(),
    val bodySite: List<CodeableConcept> = listOf(),
    val outcome: CodeableConcept? = null,
    val report: List<Reference> = listOf(),
    val complication: List<CodeableConcept> = listOf(),
    val complicationDetail: List<Reference> = listOf(),
    val followUp: List<CodeableConcept> = listOf(),
    val note: List<Annotation> = listOf(),
    val focalDevice: List<ProcedureFocalDevice> = listOf(),
    val usedReference: List<Reference> = listOf(),
    val usedCode: List<CodeableConcept> = listOf()
) : DomainResource<Procedure> {
    override val resourceType: String = "Procedure"
}

class ProcedureSerializer : BaseFHIRSerializer<Procedure>(Procedure::class.java)
class ProcedureDeserializer : BaseFHIRDeserializer<Procedure>(Procedure::class.java)

/**
 * The people who performed the procedure
 */
@JsonSerialize(using = ProcedurePerformerSerializer::class)
@JsonDeserialize(using = ProcedurePerformerDeserializer::class)
data class ProcedurePerformer(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val function: CodeableConcept? = null,
    val actor: Reference?,
    val onBehalfOf: Reference? = null
) : BackboneElement<ProcedurePerformer>

class ProcedurePerformerSerializer : BaseFHIRSerializer<ProcedurePerformer>(ProcedurePerformer::class.java)
class ProcedurePerformerDeserializer : BaseFHIRDeserializer<ProcedurePerformer>(ProcedurePerformer::class.java)

@JsonSerialize(using = ProcedureFocalDeviceSerializer::class)
@JsonDeserialize(using = ProcedureFocalDeviceDeserializer::class)
data class ProcedureFocalDevice(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val action: CodeableConcept? = null,
    val manipulated: Reference?
) : BackboneElement<ProcedureFocalDevice>

class ProcedureFocalDeviceSerializer : BaseFHIRSerializer<ProcedureFocalDevice>(ProcedureFocalDevice::class.java)
class ProcedureFocalDeviceDeserializer : BaseFHIRDeserializer<ProcedureFocalDevice>(ProcedureFocalDevice::class.java)
