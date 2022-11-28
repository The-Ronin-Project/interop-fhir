package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

@JsonSerialize(using = CarePlanActivitySerializer::class)
@JsonDeserialize(using = CarePlanActivityDeserializer::class)
data class CarePlanActivity(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val outcomeCodeableConcept: List<CodeableConcept> = listOf(),
    val outcomeReference: List<Reference> = listOf(),
    val progress: List<Annotation> = listOf(),
    val reference: Reference? = null,
    val detail: CarePlanDetail? = null
) : BackboneElement<CarePlanActivity>

class CarePlanActivitySerializer : BaseFHIRSerializer<CarePlanActivity>(CarePlanActivity::class.java)
class CarePlanActivityDeserializer : BaseFHIRDeserializer<CarePlanActivity>(CarePlanActivity::class.java)
