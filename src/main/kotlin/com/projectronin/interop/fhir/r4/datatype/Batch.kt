package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime

/**
 * Information that only applies to packages (not products).
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/medication-definitions.html#Medication.batch)
 */
data class Batch(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val lotNumber: String? = null,
    val expirationDate: DateTime? = null
) : BackboneElement<Batch>
