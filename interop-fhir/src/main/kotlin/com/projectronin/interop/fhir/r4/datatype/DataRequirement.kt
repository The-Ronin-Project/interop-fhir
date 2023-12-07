package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.event.interop.internal.v1.ResourceType
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.r4.valueset.SortDirection
import com.projectronin.interop.fhir.validate.annotation.RequiredField
import com.projectronin.interop.fhir.validate.annotation.RequiredValueSet
import com.projectronin.interop.fhir.validate.annotation.SupportedDynamicValueTypes
import com.projectronin.interop.fhir.validate.annotation.SupportedReferenceTypes

/**
 * The DataRequirement structure defines a general data requirement for a knowledge asset such as a decision support rule or quality measure.
 */
@JsonDeserialize(using = DataRequirementDeserializer::class)
@JsonSerialize(using = DataRequirementSerializer::class)
data class DataRequirement(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    @RequiredField
    val type: Code?,
    val profile: List<Canonical> = listOf(),
    @SupportedDynamicValueTypes(DynamicValueType.CODEABLE_CONCEPT, DynamicValueType.REFERENCE)
    @SupportedReferenceTypes(ResourceType.Group)
    val subject: DynamicValue<Any>? = null,
    val mustSupport: List<FHIRString> = listOf(),
    val codeFilter: List<CodeFilter> = listOf(),
    val dateFilter: List<DateFilter> = listOf(),
    val limit: PositiveInt? = null,
    val sort: List<Sort> = listOf(),
) : Element<DataRequirement>

class DataRequirementDeserializer : BaseFHIRDeserializer<DataRequirement>(DataRequirement::class.java)

class DataRequirementSerializer : BaseFHIRSerializer<DataRequirement>(DataRequirement::class.java)

@JsonDeserialize(using = CodeFilterDeserializer::class)
@JsonSerialize(using = CodeFilterSerializer::class)
data class CodeFilter(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val path: FHIRString? = null,
    val searchParam: FHIRString? = null,
    val valueSet: Canonical? = null,
    val code: List<Coding> = listOf(),
) : Element<CodeFilter>

class CodeFilterDeserializer : BaseFHIRDeserializer<CodeFilter>(CodeFilter::class.java)

class CodeFilterSerializer : BaseFHIRSerializer<CodeFilter>(CodeFilter::class.java)

@JsonDeserialize(using = DateFilterDeserializer::class)
@JsonSerialize(using = DateFilterSerializer::class)
data class DateFilter(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val path: FHIRString? = null,
    val searchParam: FHIRString? = null,
    @SupportedDynamicValueTypes(DynamicValueType.DATE_TIME, DynamicValueType.PERIOD, DynamicValueType.DURATION)
    val value: DynamicValue<Any>? = null,
) : Element<DateFilter>

class DateFilterDeserializer : BaseFHIRDeserializer<DateFilter>(DateFilter::class.java)

class DateFilterSerializer : BaseFHIRSerializer<DateFilter>(DateFilter::class.java)

@JsonDeserialize(using = SortDeserializer::class)
@JsonSerialize(using = SortSerializer::class)
data class Sort(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    @RequiredField
    val path: FHIRString?,
    @RequiredField
    @RequiredValueSet(SortDirection::class)
    val direction: Code?,
) : Element<Sort>

class SortDeserializer : BaseFHIRDeserializer<Sort>(Sort::class.java)

class SortSerializer : BaseFHIRSerializer<Sort>(Sort::class.java)
