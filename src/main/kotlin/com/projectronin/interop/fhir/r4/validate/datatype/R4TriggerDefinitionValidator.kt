package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.TriggerDefinition
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.TriggerType
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 TriggerDefinition](https://hl7.org/fhir/R4/metadatatypes.html#TriggerDefinition).
 */
object R4TriggerDefinitionValidator : R4ElementContainingValidator<TriggerDefinition>() {
    private val acceptedTimingTypes = listOf(
        DynamicValueType.TIMING,
        DynamicValueType.REFERENCE,
        DynamicValueType.DATE,
        DynamicValueType.DATE_TIME
    )

    private val requiredTypeError = RequiredFieldError(TriggerDefinition::type)
    private val invalidTimingError = InvalidDynamicValueError(TriggerDefinition::timing, acceptedTimingTypes)

    private val timingOrDataError = FHIRError(
        code = "R4_TRGDEF_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "Either timing, or a data requirement, but not both",
        location = LocationContext(TriggerDefinition::class)
    )
    private val requiredConditionError = FHIRError(
        code = "R4_TRGDEF_002",
        severity = ValidationIssueSeverity.ERROR,
        description = "A condition only if there is a data requirement",
        location = LocationContext(TriggerDefinition::class)
    )
    private val requiredNameError = FHIRError(
        code = "R4_TRGDEF_003",
        severity = ValidationIssueSeverity.ERROR,
        description = "A named event requires a name",
        location = LocationContext(TriggerDefinition::class)
    )
    private val requiredTimingError = FHIRError(
        code = "R4_TRGDEF_004",
        severity = ValidationIssueSeverity.ERROR,
        description = "A periodic event requires timing",
        location = LocationContext(TriggerDefinition::class)
    )
    private val requiredDataError = FHIRError(
        code = "R4_TRGDEF_005",
        severity = ValidationIssueSeverity.ERROR,
        description = "A data event requires data",
        location = LocationContext(TriggerDefinition::class)
    )

    override fun validateElement(element: TriggerDefinition, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.type, requiredTypeError, parentContext)

            checkTrue((element.data.isEmpty() xor (element.timing == null)), timingOrDataError, parentContext)
            checkTrue((element.condition == null || element.data.isNotEmpty()), requiredConditionError, parentContext)

            ifNotNull(element.type) {
                val codifiedType = checkCodedEnum<TriggerType>(
                    element.type,
                    InvalidValueSetError(TriggerDefinition::type, element.type.value),
                    parentContext
                )

                codifiedType?.let {
                    when (codifiedType) {
                        TriggerType.NAMED_EVENT -> checkNotNull(element.name, requiredNameError, parentContext)
                        TriggerType.PERIODIC -> checkNotNull(element.timing, requiredTimingError, parentContext)
                        TriggerType.DATA_ACCESSED,
                        TriggerType.DATA_ADDED,
                        TriggerType.DATA_CHANGED,
                        TriggerType.DATA_ACCESS_ENDED,
                        TriggerType.DATA_MODIFIED,
                        TriggerType.DATA_REMOVED -> checkTrue(
                            element.data.isNotEmpty(),
                            requiredDataError,
                            parentContext
                        )
                    }
                }
            }

            element.timing?.let { timing ->
                checkTrue(acceptedTimingTypes.contains(timing.type), invalidTimingError, parentContext)
            }
        }
    }
}
