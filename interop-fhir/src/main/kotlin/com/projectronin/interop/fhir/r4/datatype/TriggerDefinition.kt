package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.event.interop.internal.v1.ResourceType
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.r4.valueset.TriggerType
import com.projectronin.interop.fhir.validate.annotation.RequiredField
import com.projectronin.interop.fhir.validate.annotation.RequiredValueSet
import com.projectronin.interop.fhir.validate.annotation.SupportedDynamicValueTypes
import com.projectronin.interop.fhir.validate.annotation.SupportedReferenceTypes

/**
 * The TriggerDefinition structure defines when a knowledge artifact is expected to be evaluated. The structure can
 * represent three main kinds of triggering events, depending on the value of type:
 *
 * - Named Event
 * - Scheduled Event
 * - Data Event
 *
 * A named event is an event identified by the implementation environment. This allows any event generated within the
 * implementation environment to be used as a trigger, but it requires pre-coordination of the names involved with the
 * consuming environments. HL7 v2 events are assigned the URI http://terminology.hl7.org/CodeSystem/v2-0003#```[code]```
 * e.g. http://terminology.hl7.org/CodeSystem/v2-0003/A01, and reference any data change that would trigger the sending
 * of the matching HL7 v2 version, if the application providing the FHIR API supports v2 events internally.
 *
 * A scheduled event occurs on a fixed or periodic schedule.
 *
 * And finally, a data event occurs in response to some data-related event in the integrated environment such as a
 * record being added or updated. The data-of-interest for a data event is described using a DataRequirement. This allows
 * for systems to automatically invoke based on data activity occurring within the system. A condition may also be
 * specified to further refine the trigger
 */
@JsonDeserialize(using = TriggerDefinitionDeserializer::class)
@JsonSerialize(using = TriggerDefinitionSerializer::class)
data class TriggerDefinition(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    @RequiredField
    @RequiredValueSet(TriggerType::class)
    val type: Code?,
    val name: FHIRString? = null,
    @SupportedDynamicValueTypes(
        DynamicValueType.TIMING,
        DynamicValueType.REFERENCE,
        DynamicValueType.DATE,
        DynamicValueType.DATE_TIME,
    )
    @SupportedReferenceTypes(ResourceType.Schedule)
    val timing: DynamicValue<Any>? = null,
    val data: List<DataRequirement> = listOf(),
    val condition: Expression? = null,
) : Element<TriggerDefinition>

class TriggerDefinitionDeserializer : BaseFHIRDeserializer<TriggerDefinition>(TriggerDefinition::class.java)

class TriggerDefinitionSerializer : BaseFHIRSerializer<TriggerDefinition>(TriggerDefinition::class.java)
