package com.projectronin.interop.fhir.r4.datatype.medication

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.SubstitutionDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.SubstitutionSerializer
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Element
import com.projectronin.interop.fhir.r4.datatype.Extension

/**
 * Any restrictions on medication substitution
 */
@JsonDeserialize(using = SubstitutionDeserializer::class)
@JsonSerialize(using = SubstitutionSerializer::class)
data class Substitution(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val allowed: DynamicValue<Any>?,
    val reason: CodeableConcept? = null
) : Element<Substitution>
