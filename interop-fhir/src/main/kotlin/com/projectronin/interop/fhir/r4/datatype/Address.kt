package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.r4.valueset.AddressType
import com.projectronin.interop.fhir.r4.valueset.AddressUse
import com.projectronin.interop.fhir.validate.annotation.RequiredValueSet

/**
 * An address expressed using postal conventions (as opposed to GPS or other location definition formats). This data type
 * may be used to convey addresses for use in delivering mail as well as for visiting locations which might not be valid
 * for mail delivery. There are a variety of postal address formats defined around the world.
 */
@JsonSerialize(using = AddressSerializer::class)
@JsonDeserialize(using = AddressDeserializer::class)
data class Address(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    @RequiredValueSet(AddressUse::class)
    val use: Code? = null,
    @RequiredValueSet(AddressType::class)
    val type: Code? = null,
    val text: FHIRString? = null,
    val line: List<FHIRString> = listOf(),
    val city: FHIRString? = null,
    val district: FHIRString? = null,
    val state: FHIRString? = null,
    val postalCode: FHIRString? = null,
    val country: FHIRString? = null,
    val period: Period? = null,
) : Element<Address>

class AddressSerializer : BaseFHIRSerializer<Address>(Address::class.java)

class AddressDeserializer : BaseFHIRDeserializer<Address>(Address::class.java)
