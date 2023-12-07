package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.validate.annotation.RequiredField

/**
 * Data that comes from a series of measurements taken by a device, which may have upper and lower limits. The data type
 * also supports more than one dimension in the data. A SampledData provides a concise way to handle the data produced by
 * devices that sample a particular physical state at a high frequency. A typical use for this is for the output of an
 * ECG or EKG device. The data type includes a series of raw decimal values (which are mostly simple integers), along
 * with adjustments for scale and factor.
 */
@JsonSerialize(using = SampledDataSerializer::class)
@JsonDeserialize(using = SampledDataDeserializer::class)
data class SampledData(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    @RequiredField
    val origin: SimpleQuantity?,
    @RequiredField
    val period: Decimal?,
    val factor: Decimal? = null,
    val lowerLimit: Decimal? = null,
    val upperLimit: Decimal? = null,
    @RequiredField
    val dimensions: PositiveInt?,
    val data: FHIRString? = null,
) : Element<SampledData>

class SampledDataSerializer : BaseFHIRSerializer<SampledData>(SampledData::class.java)

class SampledDataDeserializer : BaseFHIRDeserializer<SampledData>(SampledData::class.java)
