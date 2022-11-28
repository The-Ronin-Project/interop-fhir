package com.projectronin.interop.fhir.r4.datatype.medication

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.Element
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt

/**
 * Medication supply authorization
 */
@JsonDeserialize(using = DispenseRequestDeserializer::class)
@JsonSerialize(using = DispenseRequestSerializer::class)
data class DispenseRequest(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val initialFill: InitialFill? = null,
    val dispenseInterval: Duration? = null,
    val validityPeriod: Period? = null,
    val numberOfRepeatsAllowed: UnsignedInt? = null,
    val quantity: SimpleQuantity? = null,
    val expectedSupplyDuration: Duration? = null,
    val performer: Reference? = null
) : Element<DispenseRequest>

class DispenseRequestSerializer : BaseFHIRSerializer<DispenseRequest>(DispenseRequest::class.java)
class DispenseRequestDeserializer : BaseFHIRDeserializer<DispenseRequest>(DispenseRequest::class.java)

/**
 * First fill details
 */
@JsonDeserialize(using = InitialFillDeserializer::class)
@JsonSerialize(using = InitialFillSerializer::class)
data class InitialFill(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val quantity: SimpleQuantity? = null,
    val duration: Duration? = null
) : Element<InitialFill>

class InitialFillSerializer : BaseFHIRSerializer<InitialFill>(InitialFill::class.java)
class InitialFillDeserializer : BaseFHIRDeserializer<InitialFill>(InitialFill::class.java)
