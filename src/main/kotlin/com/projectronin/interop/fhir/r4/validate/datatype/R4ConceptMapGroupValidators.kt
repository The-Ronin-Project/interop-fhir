package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.ConceptMapDependsOn
import com.projectronin.interop.fhir.r4.datatype.ConceptMapElement
import com.projectronin.interop.fhir.r4.datatype.ConceptMapGroup
import com.projectronin.interop.fhir.r4.datatype.ConceptMapTarget
import com.projectronin.interop.fhir.r4.datatype.ConceptMapUnmapped
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.ConceptMapEquivalence
import com.projectronin.interop.fhir.r4.valueset.ConceptMapMode
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

object R4ConceptMapGroupValidator : R4ElementContainingValidator<ConceptMapGroup>() {
    private val requiredElementError = RequiredFieldError(ConceptMapGroup::element)

    override fun validateElement(element: ConceptMapGroup, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.element, requiredElementError, parentContext)
            ifNotNull(element.element) {
                checkTrue(element.element.isNotEmpty(), requiredElementError, parentContext)
            }
        }
    }
}

object R4ConceptMapElementValidator : R4ElementContainingValidator<ConceptMapElement>() {
    override fun validateElement(element: ConceptMapElement, parentContext: LocationContext?, validation: Validation) {
    }
}

object R4ConceptMapTargetValidator : R4ElementContainingValidator<ConceptMapTarget>() {
    private val requiredEquivalenceError = RequiredFieldError(ConceptMapTarget::equivalence)
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
            checkNotNull(element.equivalence, requiredEquivalenceError, parentContext)
            ifNotNull(element.equivalence) {
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
                    requiredParticipantError, parentContext
                )
            }
        }
    }
}

object R4ConceptMapDependsOnValidator : R4ElementContainingValidator<ConceptMapDependsOn>() {
    private val requiredPropertyError = RequiredFieldError(ConceptMapDependsOn::property)
    private val requiredValueError = RequiredFieldError(ConceptMapDependsOn::value)

    override fun validateElement(
        element: ConceptMapDependsOn,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkNotNull(element.property, requiredPropertyError, parentContext)
            checkNotNull(element.value, requiredValueError, parentContext)
        }
    }
}

object R4ConceptMapUnmappedValidator : R4ElementContainingValidator<ConceptMapUnmapped>() {
    private val requiredModeError = RequiredFieldError(ConceptMapUnmapped::mode)
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
            checkNotNull(element.mode, requiredModeError, parentContext)

            ifNotNull(element.mode) {
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
