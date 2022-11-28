package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * A time period defined by a start and end date/time.
 */
@JsonSerialize(using = PeriodSerializer::class)
@JsonDeserialize(using = PeriodDeserializer::class)
data class Period(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val start: DateTime? = null,
    val end: DateTime? = null
) : Element<Period>

class PeriodSerializer : BaseFHIRSerializer<Period>(Period::class.java)
class PeriodDeserializer : BaseFHIRDeserializer<Period>(Period::class.java)
