package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * The practitioner is not available or performing this role during this period of time due to the provided reason.
 */
@JsonSerialize(using = NotAvailableSerializer::class)
@JsonDeserialize(using = NotAvailableDeserializer::class)
data class NotAvailable(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val description: FHIRString?,
    val during: Period? = null
) : BackboneElement<NotAvailable>

class NotAvailableSerializer : BaseFHIRSerializer<NotAvailable>(NotAvailable::class.java)
class NotAvailableDeserializer : BaseFHIRDeserializer<NotAvailable>(NotAvailable::class.java)
