package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.ContactDetail
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.UsageContext
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement

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
    val version: FHIRString? = null,
    val name: FHIRString? = null,
    val status: Code? = null,
    val experimental: FHIRBoolean? = null,
    val date: DateTime? = null,
    val publisher: FHIRString? = null,
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

class ConceptMapDeserializer : BaseFHIRDeserializer<ConceptMap>(ConceptMap::class.java)
class ConceptMapSerializer : BaseFHIRSerializer<ConceptMap>(ConceptMap::class.java)

@JsonSerialize(using = ConceptMapGroupSerializer::class)
@JsonDeserialize(using = ConceptMapGroupDeserializer::class)
data class ConceptMapGroup(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val source: Uri? = null,
    val sourceVersion: FHIRString? = null,
    val target: Uri? = null,
    val targetVersion: FHIRString? = null,
    val element: List<ConceptMapElement>? = listOf(),
    val unmapped: ConceptMapUnmapped? = null
) : BackboneElement<ConceptMapGroup>

class ConceptMapGroupSerializer : BaseFHIRSerializer<ConceptMapGroup>(ConceptMapGroup::class.java)
class ConceptMapGroupDeserializer : BaseFHIRDeserializer<ConceptMapGroup>(ConceptMapGroup::class.java)

@JsonSerialize(using = ConceptMapElementSerializer::class)
@JsonDeserialize(using = ConceptMapElementDeserializer::class)
data class ConceptMapElement(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val code: Code? = null,
    val display: FHIRString? = null,
    val target: List<ConceptMapTarget> = listOf()
) : BackboneElement<ConceptMapElement>

class ConceptMapElementSerializer : BaseFHIRSerializer<ConceptMapElement>(ConceptMapElement::class.java)
class ConceptMapElementDeserializer : BaseFHIRDeserializer<ConceptMapElement>(ConceptMapElement::class.java)

@JsonSerialize(using = ConceptMapTargetSerializer::class)
@JsonDeserialize(using = ConceptMapTargetDeserializer::class)
data class ConceptMapTarget(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val code: Code? = null,
    val display: FHIRString? = null,
    val equivalence: Code?,
    val comment: FHIRString? = null,
    val dependsOn: List<ConceptMapDependsOn> = listOf()
) : BackboneElement<ConceptMapTarget>

class ConceptMapTargetSerializer : BaseFHIRSerializer<ConceptMapTarget>(ConceptMapTarget::class.java)
class ConceptMapTargetDeserializer : BaseFHIRDeserializer<ConceptMapTarget>(ConceptMapTarget::class.java)

@JsonSerialize(using = ConceptMapDependsOnSerializer::class)
@JsonDeserialize(using = ConceptMapDependsOnDeserializer::class)
data class ConceptMapDependsOn(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val property: Uri?,
    val system: Canonical? = null,
    val value: FHIRString?,
    val display: FHIRString? = null,
) : BackboneElement<ConceptMapDependsOn>

class ConceptMapDependsOnSerializer : BaseFHIRSerializer<ConceptMapDependsOn>(ConceptMapDependsOn::class.java)
class ConceptMapDependsOnDeserializer : BaseFHIRDeserializer<ConceptMapDependsOn>(ConceptMapDependsOn::class.java)

@JsonSerialize(using = ConceptMapUnmappedSerializer::class)
@JsonDeserialize(using = ConceptMapUnmappedDeserializer::class)
data class ConceptMapUnmapped(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val mode: Code?,
    val code: Code? = null,
    val display: FHIRString? = null,
    val uri: Canonical? = null,
) : BackboneElement<ConceptMapUnmapped>

class ConceptMapUnmappedSerializer : BaseFHIRSerializer<ConceptMapUnmapped>(ConceptMapUnmapped::class.java)
class ConceptMapUnmappedDeserializer : BaseFHIRDeserializer<ConceptMapUnmapped>(ConceptMapUnmapped::class.java)
