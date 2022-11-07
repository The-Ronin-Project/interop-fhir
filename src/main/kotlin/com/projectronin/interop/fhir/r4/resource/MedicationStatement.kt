package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Dosage
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

@JsonDeserialize(using = MedicationStatementDeserializer::class)
@JsonSerialize(using = MedicationStatementSerializer::class)
@JsonTypeName("MedicationStatement")
data class MedicationStatement(
    override val id: Id? = null,
    override val meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<ContainedResource> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val basedOn: List<Reference> = listOf(),
    val partOf: List<Reference> = listOf(),
    val status: Code? = null,
    val statusReason: List<CodeableConcept> = listOf(),
    val category: CodeableConcept? = null,
    val medication: DynamicValue<Any>? = null,
    val subject: Reference? = null,
    val context: Reference? = null,
    val effective: DynamicValue<Any>? = null,
    val dateAsserted: DateTime? = null,
    val informationSource: Reference? = null,
    val derivedFrom: List<Reference> = listOf(),
    val reasonCode: List<CodeableConcept> = listOf(),
    val reasonReference: List<Reference> = listOf(),
    val note: List<Annotation> = listOf(),
    val dosage: List<Dosage> = listOf(),
) : DomainResource<MedicationStatement> {
    override val resourceType: String = "MedicationStatement"
}

class MedicationStatementDeserializer : BaseFHIRDeserializer<MedicationStatement>(MedicationStatement::class.java)
class MedicationStatementSerializer : BaseFHIRSerializer<MedicationStatement>(MedicationStatement::class.java)
