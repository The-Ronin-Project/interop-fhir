package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

data class ConceptMapGroup(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val source: Uri? = null,
    val sourceVersion: String? = null,
    val target: Uri? = null,
    val targetVersion: String? = null,
    val element: List<ConceptMapElement>? = listOf(),
    val unmapped: ConceptMapUnmapped? = null
) : BackboneElement<ConceptMapGroup>

data class ConceptMapElement(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val code: Code? = null,
    val display: String? = null,
    val target: List<ConceptMapTarget> = listOf()
) : BackboneElement<ConceptMapElement>

data class ConceptMapTarget(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val code: Code? = null,
    val display: String? = null,
    val equivalence: Code?,
    val comment: String? = null,
    val dependsOn: List<ConceptMapDependsOn> = listOf()
) : BackboneElement<ConceptMapTarget>

data class ConceptMapDependsOn(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val property: Uri?,
    val system: Canonical? = null,
    val value: String?,
    val display: String? = null,
) : BackboneElement<ConceptMapDependsOn>

data class ConceptMapUnmapped(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val mode: Code?,
    val code: Code? = null,
    val display: String? = null,
    val uri: Canonical? = null,
) : BackboneElement<ConceptMapUnmapped>
