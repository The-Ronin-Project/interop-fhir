package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

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
    val code: CodeableConcept?,
    val period: Period? = null,
    val issuer: Reference? = null
) : BackboneElement<Qualification>

class QualificationSerializer : BaseFHIRSerializer<Qualification>(Qualification::class.java)
class QualificationDeserializer : BaseFHIRDeserializer<Qualification>(Qualification::class.java)
