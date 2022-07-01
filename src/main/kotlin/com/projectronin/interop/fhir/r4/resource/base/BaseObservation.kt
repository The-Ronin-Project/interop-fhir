package com.projectronin.interop.fhir.r4.resource.base

import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.ObservationComponent
import com.projectronin.interop.fhir.r4.datatype.ObservationReferenceRange
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.valueset.ObservationStatus

/**
 * Base class representing a FHIR R4 Observation.
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/observation.html)
 */
abstract class BaseObservation {
    val resourceType: String = "Observation"

    abstract val id: Id?
    abstract val meta: Meta?
    abstract val implicitRules: Uri?
    abstract val language: Code?
    abstract val text: Narrative?
    abstract val contained: List<ContainedResource>
    abstract val extension: List<Extension>
    abstract val modifierExtension: List<Extension>
    abstract val identifier: List<Identifier>
    abstract val basedOn: List<Reference>
    abstract val partOf: List<Reference>
    abstract val status: ObservationStatus
    abstract val category: List<CodeableConcept>
    abstract val code: CodeableConcept
    abstract val subject: Reference?
    abstract val focus: List<Reference>
    abstract val encounter: Reference?
    abstract val effective: DynamicValue<Any>?
    abstract val issued: Instant?
    abstract val performer: List<Reference>
    abstract val value: DynamicValue<Any>?
    abstract val dataAbsentReason: CodeableConcept?
    abstract val interpretation: List<CodeableConcept>
    abstract val note: List<Annotation>
    abstract val bodySite: CodeableConcept?
    abstract val method: CodeableConcept?
    abstract val specimen: Reference?
    abstract val device: Reference?
    abstract val referenceRange: List<ObservationReferenceRange>
    abstract val hasMember: List<Reference>
    abstract val derivedFrom: List<Reference>
    abstract val component: List<ObservationComponent>

    companion object {
        val acceptedEffectives = listOf(
            DynamicValueType.DATE_TIME,
            DynamicValueType.PERIOD,
            DynamicValueType.TIMING,
            DynamicValueType.INSTANT
        )
    }

    protected fun validate() {
        // R4 Observation.value[x] data types are constrained by the ObservationStatus enum type, so no validation needed.

        effective?.let { data ->
            require(acceptedEffectives.contains(data.type)) {
                "Observation effective can only be one of the following data types: ${acceptedEffectives.joinToString { it.code }}"
            }
        }
        require(value == null || dataAbsentReason == null) {
            "dataAbsentReason SHALL only be present if value[x] is not present"
        }
        if (value != null) {
            require(component.all { child -> (child.code != code) }) {
                "If Observation.code is the same as an Observation.component.code then the Observation.value SHALL NOT be present"
            }
        }
    }
}
