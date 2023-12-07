package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.element.Element

/**
 * A CodeableConcept represents a value that is usually supplied by providing a reference to one or more terminologies or
 * ontologies but may also be defined by the provision of text. This is a common pattern in healthcare data.
 */
@JsonSerialize(using = CodeableConceptSerializer::class)
@JsonDeserialize(using = CodeableConceptDeserializer::class)
data class CodeableConcept(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val coding: List<Coding> = listOf(),
    val text: FHIRString? = null,
) : Element<CodeableConcept>

class CodeableConceptSerializer : BaseFHIRSerializer<CodeableConcept>(CodeableConcept::class.java)

class CodeableConceptDeserializer : BaseFHIRDeserializer<CodeableConcept>(CodeableConcept::class.java)
