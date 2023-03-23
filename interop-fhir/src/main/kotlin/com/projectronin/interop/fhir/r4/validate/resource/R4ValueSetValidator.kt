package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.ValueSet
import com.projectronin.interop.fhir.r4.resource.ValueSetCompose
import com.projectronin.interop.fhir.r4.resource.ValueSetConcept
import com.projectronin.interop.fhir.r4.resource.ValueSetContains
import com.projectronin.interop.fhir.r4.resource.ValueSetDesignation
import com.projectronin.interop.fhir.r4.resource.ValueSetExpansion
import com.projectronin.interop.fhir.r4.resource.ValueSetFilter
import com.projectronin.interop.fhir.r4.resource.ValueSetInclude
import com.projectronin.interop.fhir.r4.resource.ValueSetParameter
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.FilterOperator
import com.projectronin.interop.fhir.r4.valueset.PublicationStatus
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

object R4ValueSetValidator : R4ElementContainingValidator<ValueSet>() {
    private val requiredStatusError = RequiredFieldError(ValueSet::status)

    override fun validateElement(
        element: ValueSet,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkNotNull(element.status, requiredStatusError, parentContext)
            ifNotNull(element.status) {
                checkCodedEnum<PublicationStatus>(
                    element.status,
                    InvalidValueSetError(ValueSet::status, element.status.value),
                    parentContext
                )
            }
        }
    }
}

object R4ValueSetComposeValidator : R4ElementContainingValidator<ValueSetCompose>() {
    private val requiredIncludeError = RequiredFieldError(ValueSetCompose::include)

    override fun validateElement(
        element: ValueSetCompose,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkTrue(element.include.isNotEmpty(), requiredIncludeError, parentContext)
        }
    }
}

object R4ValueSetIncludeValidator : R4ElementContainingValidator<ValueSetInclude>() {
    private val requiredValueSetOrSystemError = FHIRError(
        code = "R4_VALSTINC_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "A value set include/exclude SHALL have a value set or a system",
        location = LocationContext(ValueSetInclude::class)
    )
    private val missingRequiredFilterError = FHIRError(
        code = "R4_VALSTINC_002",
        severity = ValidationIssueSeverity.ERROR,
        description = "A value set with concepts or filters SHALL include a system",
        location = LocationContext(ValueSetInclude::class)
    )
    private val conflictingPropertiesError = FHIRError(
        code = "R4_VALSTINC_003",
        severity = ValidationIssueSeverity.ERROR,
        description = "Cannot have both concept and filter",
        location = LocationContext(ValueSetInclude::class)
    )

    override fun validateElement(
        element: ValueSetInclude,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkTrue(
                element.valueSet.isNotEmpty() || element.system != null,
                requiredValueSetOrSystemError,
                parentContext
            )
            checkTrue(
                (element.concept.isEmpty() && element.filter.isEmpty()) || element.system != null,
                missingRequiredFilterError,
                parentContext
            )
            checkTrue(
                element.concept.isEmpty() || element.filter.isEmpty(),
                conflictingPropertiesError,
                parentContext
            )
        }
    }
}

object R4ValueSetConceptValidator : R4ElementContainingValidator<ValueSetConcept>() {
    private val requiredCodeError = RequiredFieldError(ValueSetConcept::code)

    override fun validateElement(
        element: ValueSetConcept,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkNotNull(element.code, requiredCodeError, parentContext)
        }
    }
}

object R4ValueSetDesignationValidator : R4ElementContainingValidator<ValueSetDesignation>() {
    private val requiredValueError = RequiredFieldError(ValueSetDesignation::value)

    override fun validateElement(
        element: ValueSetDesignation,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkNotNull(element.value, requiredValueError, parentContext)
        }
    }
}

object R4ValueSetFilterValidator : R4ElementContainingValidator<ValueSetFilter>() {
    private val requiredPropertyError = RequiredFieldError(ValueSetFilter::property)
    private val requiredOpError = RequiredFieldError(ValueSetFilter::op)
    private val requiredValueError = RequiredFieldError(ValueSetFilter::value)

    override fun validateElement(
        element: ValueSetFilter,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkNotNull(element.property, requiredPropertyError, parentContext)
            checkNotNull(element.op, requiredOpError, parentContext)
            ifNotNull(element.op) {
                checkCodedEnum<FilterOperator>(
                    element.op,
                    InvalidValueSetError(ValueSetFilter::op, element.op.value),
                    parentContext
                )
            }
            checkNotNull(element.value, requiredValueError, parentContext)
        }
    }
}

object R4ValueSetExpansionValidator : R4ElementContainingValidator<ValueSetExpansion>() {
    private val requiredTimestampError = RequiredFieldError(ValueSetExpansion::timestamp)

    override fun validateElement(
        element: ValueSetExpansion,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkNotNull(element.timestamp, requiredTimestampError, parentContext)
        }
    }
}

object R4ValueSetParameterValidator : R4ElementContainingValidator<ValueSetParameter>() {
    private val requiredNameError = RequiredFieldError(ValueSetParameter::name)

    private val acceptedValueTypes = listOf(
        DynamicValueType.STRING,
        DynamicValueType.BOOLEAN,
        DynamicValueType.INTEGER,
        DynamicValueType.DECIMAL,
        DynamicValueType.URI,
        DynamicValueType.CODE,
        DynamicValueType.DATE_TIME
    )

    private val invalidValueError = InvalidDynamicValueError(ValueSetParameter::value, acceptedValueTypes)

    override fun validateElement(
        element: ValueSetParameter,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkNotNull(element.name, requiredNameError, parentContext)
            ifNotNull(element.value) {
                checkTrue(
                    acceptedValueTypes.contains(element.value?.type),
                    invalidValueError,
                    parentContext
                )
            }
        }
    }
}

object R4ValueSetContainsValidator : R4ElementContainingValidator<ValueSetContains>() {
    private val requiredNameError = RequiredFieldError(ValueSetParameter::name)

    private val requiredCodeOrDisplayError = FHIRError(
        code = "R4_VALSTCON_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "SHALL have a code or a display",
        location = LocationContext(ValueSetContains::class)
    )
    private val missingRequiredCodeError = FHIRError(
        code = "R4_VALSTCON_002",
        severity = ValidationIssueSeverity.ERROR,
        description = "Must have a code if not abstract",
        location = LocationContext(ValueSetContains::class)
    )
    private val missingRequiredSystemError = FHIRError(
        code = "R4_VALSTCON_003",
        severity = ValidationIssueSeverity.ERROR,
        description = "Must have a system if a code is present",
        location = LocationContext(ValueSetContains::class)
    )

    override fun validateElement(
        element: ValueSetContains,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            if (element.code == null) {
                checkTrue(element.display != null, requiredCodeOrDisplayError, parentContext)
                checkTrue(element.abstract?.value == true, missingRequiredCodeError, parentContext)
            } else {
                checkTrue(element.system != null, missingRequiredSystemError, parentContext)
            }
        }
    }
}
