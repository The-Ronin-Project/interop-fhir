package com.projectronin.interop.fhir.r4.datatype

/**
 * Details about the admission to a healthcare service.
 * See [R4 Encounter.hospitalization](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.hospitalization).
 */
data class EncounterHospitalization(
    override val id: String? = null,
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
