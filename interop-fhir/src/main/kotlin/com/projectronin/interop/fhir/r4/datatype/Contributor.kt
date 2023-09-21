package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.validate.annotation.RequiredField

/**
 * Contributor information
 */
@JsonSerialize(using = ContributorSerializer::class)
@JsonDeserialize(using = ContributorDeserializer::class)
data class Contributor(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    @RequiredField
    val type: Code?,
    @RequiredField
    val name: FHIRString?,
    val contact: List<ContactDetail> = listOf()
) : Element<Contributor>

class ContributorSerializer : BaseFHIRSerializer<Contributor>(Contributor::class.java)
class ContributorDeserializer : BaseFHIRDeserializer<Contributor>(Contributor::class.java)
