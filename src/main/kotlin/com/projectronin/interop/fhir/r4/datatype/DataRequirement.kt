package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt

/**
 * The DataRequirement structure defines a general data requirement for a knowledge asset such as a decision support rule or quality measure.
 */
@JsonDeserialize(using = DataRequirementDeserializer::class)
@JsonSerialize(using = DataRequirementSerializer::class)
data class DataRequirement(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val type: Code?,
    val profile: List<Canonical> = listOf(),
    val subject: DynamicValue<Any>? = null,
    val mustSupport: List<String> = listOf(),
    val codeFilter: List<CodeFilter> = listOf(),
    val dateFilter: List<DateFilter> = listOf(),
    val limit: PositiveInt? = null,
    val sort: List<Sort> = listOf()
) : Element<DataRequirement>

data class CodeFilter(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val path: String? = null,
    val searchParam: String? = null,
    val valueSet: Canonical? = null,
    val code: List<Coding> = listOf()
) : Element<CodeFilter>

class DataRequirementDeserializer : BaseFHIRDeserializer<DataRequirement>(DataRequirement::class.java)
class DataRequirementSerializer : BaseFHIRSerializer<DataRequirement>(DataRequirement::class.java)

@JsonDeserialize(using = DateFilterDeserializer::class)
@JsonSerialize(using = DateFilterSerializer::class)
data class DateFilter(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val path: String? = null,
    val searchParam: String? = null,
    val value: DynamicValue<Any>? = null
) : Element<DateFilter>

data class Sort(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val path: String?,
    val direction: Code?
) : Element<Sort>

class DateFilterDeserializer : BaseFHIRDeserializer<DateFilter>(DateFilter::class.java)
class DateFilterSerializer : BaseFHIRSerializer<DateFilter>(DateFilter::class.java)
