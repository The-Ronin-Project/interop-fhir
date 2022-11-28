package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * Details about the admission to a healthcare service.
 * See [R4 Encounter.hospitalization](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.hospitalization).
 */
@JsonSerialize(using = EncounterHospitalizationSerializer::class)
@JsonDeserialize(using = EncounterHospitalizationDeserializer::class)
data class EncounterHospitalization(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val preAdmissionIdentifier: Identifier? = null,
    val origin: Reference? = null,
    val admitSource: CodeableConcept? = null,
    val reAdmission: CodeableConcept? = null,
    val dietPreference: List<CodeableConcept> = listOf(),
    val specialCourtesy: List<CodeableConcept> = listOf(),
    val specialArrangement: List<CodeableConcept> = listOf(),
    val destination: Reference? = null,
    val dischargeDisposition: CodeableConcept? = null,
) : BackboneElement<EncounterHospitalization>

class EncounterHospitalizationSerializer :
    BaseFHIRSerializer<EncounterHospitalization>(EncounterHospitalization::class.java)

class EncounterHospitalizationDeserializer :
    BaseFHIRDeserializer<EncounterHospitalization>(EncounterHospitalization::class.java)
