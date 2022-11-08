package com.projectronin.interop.fhir.r4.datatype.medication

import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.Element
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt

/**
 * Medication supply authorization
 */
data class DispenseRequest(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val initialFill: InitialFill? = null,
    val dispenseInterval: Duration? = null,
    val validityPeriod: Period? = null,
    val numberOfRepeatsAllowed: UnsignedInt? = null,
    val quantity: SimpleQuantity? = null,
    val expectedSupplyDuration: Duration? = null,
    val performer: Reference? = null
) : Element<DispenseRequest>

/**
 * First fill details
 */
data class InitialFill(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val quantity: SimpleQuantity? = null,
    val duration: Duration? = null
) : Element<InitialFill>
