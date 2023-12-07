package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.ContactDetail
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.UsageContext
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement
import com.projectronin.interop.fhir.r4.valueset.FilterOperator
import com.projectronin.interop.fhir.r4.valueset.PublicationStatus
import com.projectronin.interop.fhir.validate.annotation.RequiredField
import com.projectronin.interop.fhir.validate.annotation.RequiredValueSet
import com.projectronin.interop.fhir.validate.annotation.SupportedDynamicValueTypes

@JsonDeserialize(using = ValueSetDeserializer::class)
@JsonSerialize(using = ValueSetSerializer::class)
@JsonTypeName("ValueSet")
data class ValueSet(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<Resource<*>> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val url: Uri? = null,
    val identifier: List<Identifier> = listOf(),
    val version: FHIRString? = null,
    val name: FHIRString? = null,
    val title: FHIRString? = null,
    @RequiredField
    @RequiredValueSet(PublicationStatus::class)
    val status: Code?,
    val experimental: FHIRBoolean? = null,
    val date: DateTime? = null,
    val publisher: FHIRString? = null,
    val contact: List<ContactDetail> = listOf(),
    val description: Markdown? = null,
    val useContext: List<UsageContext> = listOf(),
    val jurisdiction: List<CodeableConcept> = listOf(),
    val immutable: FHIRBoolean? = null,
    val purpose: Markdown? = null,
    val copyright: Markdown? = null,
    val compose: ValueSetCompose? = null,
    val expansion: ValueSetExpansion? = null,
) : DomainResource<ValueSet> {
    override val resourceType: String = "ValueSet"
}

class ValueSetDeserializer : BaseFHIRDeserializer<ValueSet>(ValueSet::class.java)

class ValueSetSerializer : BaseFHIRSerializer<ValueSet>(ValueSet::class.java)

@JsonDeserialize(using = ValueSetComposeDeserializer::class)
@JsonSerialize(using = ValueSetComposeSerializer::class)
data class ValueSetCompose(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val lockedDate: Date? = null,
    val inactive: FHIRBoolean? = null,
    @RequiredField
    val include: List<ValueSetInclude> = listOf(),
    val exclude: List<ValueSetInclude> = listOf(),
) : BackboneElement<ValueSetCompose>

class ValueSetComposeDeserializer : BaseFHIRDeserializer<ValueSetCompose>(ValueSetCompose::class.java)

class ValueSetComposeSerializer : BaseFHIRSerializer<ValueSetCompose>(ValueSetCompose::class.java)

@JsonDeserialize(using = ValueSetIncludeDeserializer::class)
@JsonSerialize(using = ValueSetIncludeSerializer::class)
data class ValueSetInclude(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val system: Uri? = null,
    val version: FHIRString? = null,
    val concept: List<ValueSetConcept> = listOf(),
    val filter: List<ValueSetFilter> = listOf(),
    val valueSet: List<Canonical> = listOf(),
) : BackboneElement<ValueSetInclude>

class ValueSetIncludeDeserializer : BaseFHIRDeserializer<ValueSetInclude>(ValueSetInclude::class.java)

class ValueSetIncludeSerializer : BaseFHIRSerializer<ValueSetInclude>(ValueSetInclude::class.java)

@JsonDeserialize(using = ValueSetConceptDeserializer::class)
@JsonSerialize(using = ValueSetConceptSerializer::class)
data class ValueSetConcept(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    @RequiredField
    val code: Code?,
    val display: FHIRString? = null,
    val designation: List<ValueSetDesignation> = listOf(),
) : BackboneElement<ValueSetConcept>

class ValueSetConceptDeserializer : BaseFHIRDeserializer<ValueSetConcept>(ValueSetConcept::class.java)

class ValueSetConceptSerializer : BaseFHIRSerializer<ValueSetConcept>(ValueSetConcept::class.java)

@JsonDeserialize(using = ValueSetDesignationDeserializer::class)
@JsonSerialize(using = ValueSetDesignationSerializer::class)
data class ValueSetDesignation(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val language: Code? = null,
    val use: Coding? = null,
    @RequiredField
    val value: FHIRString?,
) : BackboneElement<ValueSetDesignation>

class ValueSetDesignationDeserializer : BaseFHIRDeserializer<ValueSetDesignation>(ValueSetDesignation::class.java)

class ValueSetDesignationSerializer : BaseFHIRSerializer<ValueSetDesignation>(ValueSetDesignation::class.java)

@JsonDeserialize(using = ValueSetFilterDeserializer::class)
@JsonSerialize(using = ValueSetFilterSerializer::class)
data class ValueSetFilter(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    @RequiredField
    val property: Code?,
    @RequiredField
    @RequiredValueSet(FilterOperator::class)
    val op: Code?,
    @RequiredField
    val value: FHIRString?,
) : BackboneElement<ValueSetFilter>

class ValueSetFilterDeserializer : BaseFHIRDeserializer<ValueSetFilter>(ValueSetFilter::class.java)

class ValueSetFilterSerializer : BaseFHIRSerializer<ValueSetFilter>(ValueSetFilter::class.java)

@JsonDeserialize(using = ValueSetExpansionDeserializer::class)
@JsonSerialize(using = ValueSetExpansionSerializer::class)
data class ValueSetExpansion(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: Uri? = null,
    @RequiredField
    val timestamp: DateTime?,
    val total: FHIRInteger? = null,
    val offset: FHIRInteger? = null,
    val parameter: List<ValueSetParameter> = listOf(),
    val contains: List<ValueSetContains> = listOf(),
) : BackboneElement<ValueSetExpansion>

class ValueSetExpansionDeserializer : BaseFHIRDeserializer<ValueSetExpansion>(ValueSetExpansion::class.java)

class ValueSetExpansionSerializer : BaseFHIRSerializer<ValueSetExpansion>(ValueSetExpansion::class.java)

@JsonDeserialize(using = ValueSetParameterDeserializer::class)
@JsonSerialize(using = ValueSetParameterSerializer::class)
data class ValueSetParameter(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    @RequiredField
    val name: FHIRString?,
    @SupportedDynamicValueTypes(
        DynamicValueType.STRING,
        DynamicValueType.BOOLEAN,
        DynamicValueType.INTEGER,
        DynamicValueType.DECIMAL,
        DynamicValueType.URI,
        DynamicValueType.CODE,
        DynamicValueType.DATE_TIME,
    )
    val value: DynamicValue<Any>? = null,
) : BackboneElement<ValueSetParameter>

class ValueSetParameterDeserializer : BaseFHIRDeserializer<ValueSetParameter>(ValueSetParameter::class.java)

class ValueSetParameterSerializer : BaseFHIRSerializer<ValueSetParameter>(ValueSetParameter::class.java)

@JsonDeserialize(using = ValueSetContainsDeserializer::class)
@JsonSerialize(using = ValueSetContainsSerializer::class)
data class ValueSetContains(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val system: Uri? = null,
    val abstract: FHIRBoolean? = null,
    val inactive: FHIRBoolean? = null,
    val version: FHIRString? = null,
    val code: Code? = null,
    val display: FHIRString? = null,
    val designation: List<ValueSetDesignation> = listOf(),
    val contains: List<ValueSetContains> = listOf(),
) : BackboneElement<ValueSetContains>

class ValueSetContainsDeserializer : BaseFHIRDeserializer<ValueSetContains>(ValueSetContains::class.java)

class ValueSetContainsSerializer : BaseFHIRSerializer<ValueSetContains>(ValueSetContains::class.java)
