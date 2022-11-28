package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

@JsonDeserialize(using = CarePlanDetailDeserializer::class)
@JsonSerialize(using = CarePlanDetailSerializer::class)
data class CarePlanDetail(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val kind: Code? = null,
    val instantiatesCanonical: List<Canonical> = listOf(),
    val instantiatesUri: Uri? = null,
    val code: CodeableConcept? = null,
    val reasonCode: List<CodeableConcept> = listOf(),
    val reasonReference: List<Reference> = listOf(),
    val goal: List<Reference> = listOf(),
    val status: Code? = null,
    val statusReason: CodeableConcept? = null,
    val doNotPerform: FHIRBoolean? = null,
    val scheduled: DynamicValue<Any>? = null,
    val location: Reference? = null,
    val performer: List<Reference> = listOf(),
    val product: DynamicValue<Any>? = null,
    val dailyAmount: SimpleQuantity? = null,
    val quantity: SimpleQuantity? = null,
    val description: FHIRString? = null
) : BackboneElement<CarePlanDetail>

class CarePlanDetailDeserializer : BaseFHIRDeserializer<CarePlanDetail>(CarePlanDetail::class.java)
class CarePlanDetailSerializer : BaseFHIRSerializer<CarePlanDetail>(CarePlanDetail::class.java)
