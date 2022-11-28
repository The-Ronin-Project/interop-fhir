package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt

/**
 * List of diagnoses relevant to this encounter.
 * See [R4 Encounter.diagnosis](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.diagnosis).
 */
@JsonSerialize(using = EncounterDiagnosisSerializer::class)
@JsonDeserialize(using = EncounterDiagnosisDeserializer::class)
data class EncounterDiagnosis(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val condition: Reference?,
    val use: CodeableConcept? = null,
    val rank: PositiveInt? = null,
) : BackboneElement<EncounterDiagnosis>

class EncounterDiagnosisSerializer : BaseFHIRSerializer<EncounterDiagnosis>(EncounterDiagnosis::class.java)
class EncounterDiagnosisDeserializer : BaseFHIRDeserializer<EncounterDiagnosis>(EncounterDiagnosis::class.java)
