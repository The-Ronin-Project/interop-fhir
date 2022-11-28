package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * List of locations where the patient has been.
 * See [R4 Encounter.location](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.location).
 */
@JsonSerialize(using = EncounterLocationSerializer::class)
@JsonDeserialize(using = EncounterLocationDeserializer::class)
data class EncounterLocation(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val location: Reference?,
    val status: Code? = null,
    val physicalType: CodeableConcept? = null,
    val period: Period? = null,
) : BackboneElement<EncounterLocation>

class EncounterLocationSerializer : BaseFHIRSerializer<EncounterLocation>(EncounterLocation::class.java)
class EncounterLocationDeserializer : BaseFHIRDeserializer<EncounterLocation>(EncounterLocation::class.java)
