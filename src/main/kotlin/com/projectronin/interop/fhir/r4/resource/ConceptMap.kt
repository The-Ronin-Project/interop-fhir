package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.ConceptMapDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.ConceptMapSerializer
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.ConceptMapGroup
import com.projectronin.interop.fhir.r4.datatype.ContactDetail
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.UsageContext
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

@JsonDeserialize(using = ConceptMapDeserializer::class)
@JsonSerialize(using = ConceptMapSerializer::class)
@JsonTypeName("ConceptMap")
data class ConceptMap(
    override val id: Id? = null,
    override val meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<ContainedResource> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val url: Uri? = null,
    val identifier: Identifier? = null,
    val version: String? = null,
    val name: String? = null,
    val status: Code?,
    val experimental: Boolean? = null,
    val date: DateTime? = null,
    val publisher: String? = null,
    val contact: List<ContactDetail> = listOf(),
    val description: Markdown? = null,
    val useContext: List<UsageContext> = listOf(),
    val jurisdiction: List<CodeableConcept> = listOf(),
    val purpose: Markdown? = null,
    val copyright: Markdown? = null,
    val source: DynamicValue<Any>? = null,
    val target: DynamicValue<Any>? = null,
    val group: List<ConceptMapGroup> = listOf()
) : DomainResource<ConceptMap> {

    override val resourceType: String = "ConceptMap"
}
