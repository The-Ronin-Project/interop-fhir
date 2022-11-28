package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * The Dosage structure defines general dosage instruction information typically represented in medication requests,
 * medication dispenses and medication statements.
 */
@JsonDeserialize(using = DosageDeserializer::class)
@JsonSerialize(using = DosageSerializer::class)
data class Dosage(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val sequence: FHIRInteger? = null,
    val text: FHIRString? = null,
    val additionalInstruction: List<CodeableConcept> = listOf(),
    val patientInstruction: FHIRString? = null,
    val timing: Timing? = null,
    val asNeeded: DynamicValue<Any>? = null,
    val site: CodeableConcept? = null,
    val route: CodeableConcept? = null,
    val method: CodeableConcept? = null,
    val doseAndRate: List<DoseAndRate> = listOf(),
    val maxDosePerPeriod: Ratio? = null,
    val maxDosePerAdministration: SimpleQuantity? = null,
    val maxDosePerLifetime: SimpleQuantity? = null
) : BackboneElement<Dosage>

class DosageDeserializer : BaseFHIRDeserializer<Dosage>(Dosage::class.java)
class DosageSerializer : BaseFHIRSerializer<Dosage>(Dosage::class.java)

@JsonDeserialize(using = DoseAndRateDeserializer::class)
@JsonSerialize(using = DoseAndRateSerializer::class)
data class DoseAndRate(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val type: CodeableConcept? = null,
    val dose: DynamicValue<Any>? = null,
    val rate: DynamicValue<Any>? = null
) : Element<DoseAndRate>

class DoseAndRateDeserializer : BaseFHIRDeserializer<DoseAndRate>(DoseAndRate::class.java)
class DoseAndRateSerializer : BaseFHIRSerializer<DoseAndRate>(DoseAndRate::class.java)
