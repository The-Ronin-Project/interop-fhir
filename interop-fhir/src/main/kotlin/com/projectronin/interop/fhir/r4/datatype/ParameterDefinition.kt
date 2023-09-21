package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.validate.annotation.RequiredField

/**
 * The ParameterDefinition structure defines a parameter to a knowledge asset such as a decision support rule or quality
 * measure. Parameters are typically used to communicate patient-independent information such as configuration values,
 * whereas DataRequirements are typically used to communicate patient-dependent information such as MedicationStatements
 * and Encounters.
 */
@JsonSerialize(using = ParameterDefinitionSerializer::class)
@JsonDeserialize(using = ParameterDefinitionDeserializer::class)
data class ParameterDefinition(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val name: Code? = null,
    @RequiredField
    val use: Code?,
    val min: FHIRInteger? = null,
    val max: FHIRString? = null,
    val documentation: FHIRString? = null,
    @RequiredField
    val type: Code?,
    val profile: Canonical? = null
) : Element<ParameterDefinition>

class ParameterDefinitionSerializer : BaseFHIRSerializer<ParameterDefinition>(ParameterDefinition::class.java)
class ParameterDefinitionDeserializer : BaseFHIRDeserializer<ParameterDefinition>(ParameterDefinition::class.java)
