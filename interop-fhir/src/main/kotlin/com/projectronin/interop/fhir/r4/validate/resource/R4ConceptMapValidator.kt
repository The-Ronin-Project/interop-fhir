package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.ConceptMap
import com.projectronin.interop.fhir.r4.resource.ConceptMapTarget
import com.projectronin.interop.fhir.r4.resource.ConceptMapUnmapped
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.ConceptMapEquivalence
import com.projectronin.interop.fhir.r4.valueset.ConceptMapMode
import com.projectronin.interop.fhir.r4.valueset.ConceptMapStatus
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

object R4ConceptMapValidator : R4ElementContainingValidator<ConceptMap>() {
    override fun validateElement(element: ConceptMap, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.status?.let {
                checkCodedEnum<ConceptMapStatus>(
                    element.status,
                    InvalidValueSetError(ConceptMap::status, element.status.value),
                    parentContext
                )
            }
        }
    }
}

object R4ConceptMapTargetValidator : R4ElementContainingValidator<ConceptMapTarget>() {
    private val requiredParticipantError = FHIRError(
        code = "R4_CNCPMPTG_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "If the map is narrower or inexact, there SHALL be some comments",
        location = LocationContext(ConceptMapTarget::class)
    )

    override fun validateElement(
        element: ConceptMapTarget,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            element.equivalence?.let {
                val codifiedEquivalence = checkCodedEnum<ConceptMapEquivalence>(
                    element.equivalence,
                    InvalidValueSetError(ConceptMapTarget::equivalence, element.equivalence.value),
                    parentContext
                )
                checkTrue(
                    (
                        (codifiedEquivalence != ConceptMapEquivalence.NARROWER && codifiedEquivalence != ConceptMapEquivalence.INEXACT) ||
                            (element.comment != null)
                        ),
                    requiredParticipantError,
                    parentContext
                )
            }
        }
    }
}

object R4ConceptMapUnmappedValidator : R4ElementContainingValidator<ConceptMapUnmapped>() {
    private val fixedAndCodeError = FHIRError(
        code = "R4_CNCPMPUM_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "If the mode is 'fixed', a code must be provided",
        location = LocationContext(ConceptMapUnmapped::class)
    )
    private val otherAndUriError = FHIRError(
        code = "R4_CNCPMPUM_002",
        severity = ValidationIssueSeverity.ERROR,
        description = "If the mode is 'other-map', a url must be provided",
        location = LocationContext(ConceptMapUnmapped::class)
    )

    override fun validateElement(element: ConceptMapUnmapped, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.mode?.let {
                val codifiedMode = checkCodedEnum<ConceptMapMode>(
                    element.mode,
                    InvalidValueSetError(ConceptMapUnmapped::mode, element.mode.value),
                    parentContext
                )
                checkTrue(
                    (codifiedMode != ConceptMapMode.FIXED || element.code != null),
                    fixedAndCodeError,
                    parentContext
                )
                checkTrue(
                    (codifiedMode != ConceptMapMode.OTHER_MAP || element.uri != null),
                    otherAndUriError,
                    parentContext
                )
            }
        }
    }
}
