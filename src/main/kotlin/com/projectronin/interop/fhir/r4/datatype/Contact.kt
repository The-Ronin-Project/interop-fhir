package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * Contact covers all kinds of contact parties: family members, business contacts, guardians, caregivers. Not applicable
 * to register pedigree and family ties beyond use of having contact.
 */
@JsonSerialize(using = ContactSerializer::class)
@JsonDeserialize(using = ContactDeserializer::class)
data class Contact(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val relationship: List<CodeableConcept> = listOf(),
    val name: HumanName? = null,
    val telecom: List<ContactPoint> = listOf(),
    val address: Address? = null,
    val gender: Code? = null,
    val organization: Reference? = null,
    val period: Period? = null
) : BackboneElement<Contact>

class ContactSerializer : BaseFHIRSerializer<Contact>(Contact::class.java)
class ContactDeserializer : BaseFHIRDeserializer<Contact>(Contact::class.java)
