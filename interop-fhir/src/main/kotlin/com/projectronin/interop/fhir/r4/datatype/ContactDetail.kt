package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.element.Element

/**
 * The ContactDetail structure defines general contact details.
 */
@JsonSerialize(using = ContactDetailSerializer::class)
@JsonDeserialize(using = ContactDetailDeserializer::class)
data class ContactDetail(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val name: FHIRString? = null,
    val telecom: List<ContactPoint> = listOf(),
) : Element<ContactDetail>

class ContactDetailSerializer : BaseFHIRSerializer<ContactDetail>(ContactDetail::class.java)

class ContactDetailDeserializer : BaseFHIRDeserializer<ContactDetail>(ContactDetail::class.java)
