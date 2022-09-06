package com.projectronin.interop.fhir.r4.datatype

/**
 * A CodeableConcept represents a value that is usually supplied by providing a reference to one or more terminologies or
 * ontologies but may also be defined by the provision of text. This is a common pattern in healthcare data.
 */
data class CodeableConcept(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val coding: List<Coding> = listOf(),
    val text: String? = null
) : Element<CodeableConcept>
