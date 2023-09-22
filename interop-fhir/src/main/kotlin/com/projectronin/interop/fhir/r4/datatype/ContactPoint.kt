package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.r4.valueset.ContactPointSystem
import com.projectronin.interop.fhir.r4.valueset.ContactPointUse
import com.projectronin.interop.fhir.validate.annotation.RequiredValueSet

/**
 * Details for all kinds of technology-mediated contact points for a person or organization, including telephone, email, etc.
 */
@JsonDeserialize(using = ContactPointDeserializer::class)
@JsonSerialize(using = ContactPointSerializer::class)
data class ContactPoint(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    @RequiredValueSet(ContactPointSystem::class)
    val system: Code? = null,
    val value: FHIRString? = null,
    @RequiredValueSet(ContactPointUse::class)
    val use: Code? = null,
    val rank: PositiveInt? = null,
    val period: Period? = null
) : Element<ContactPoint>

class ContactPointDeserializer : BaseFHIRDeserializer<ContactPoint>(ContactPoint::class.java)
class ContactPointSerializer : BaseFHIRSerializer<ContactPoint>(ContactPoint::class.java)
