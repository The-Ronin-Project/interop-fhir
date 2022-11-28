package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * Information that only applies to packages (not products).
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/medication-definitions.html#Medication.batch)
 */
@JsonSerialize(using = BatchSerializer::class)
@JsonDeserialize(using = BatchDeserializer::class)
data class Batch(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val lotNumber: FHIRString? = null,
    val expirationDate: DateTime? = null
) : BackboneElement<Batch>

class BatchSerializer : BaseFHIRSerializer<Batch>(Batch::class.java)
class BatchDeserializer : BaseFHIRDeserializer<Batch>(Batch::class.java)
