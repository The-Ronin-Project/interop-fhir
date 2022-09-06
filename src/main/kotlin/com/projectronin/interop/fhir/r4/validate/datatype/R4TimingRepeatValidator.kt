package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.TimingRepeat
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.DayOfWeek
import com.projectronin.interop.fhir.r4.valueset.EventTiming
import com.projectronin.interop.fhir.r4.valueset.UnitOfTime
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 TimingRepeat](https://hl7.org/fhir/R4/datatypes-definitions.html#Timing.repeat).
 */
object R4TimingRepeatValidator : R4ElementContainingValidator<TimingRepeat>() {
    private val acceptedBoundTypes =
        listOf(DynamicValueType.DURATION, DynamicValueType.RANGE, DynamicValueType.PERIOD)
    private val invalidWhens =
        listOf(EventTiming.MEAL.code, EventTiming.BREAKFAST.code, EventTiming.LUNCH.code, EventTiming.DINNER.code)

    private val invalidDurationUnitError = InvalidValueSetError(TimingRepeat::durationUnit)
    private val invalidPeriodUnitError = InvalidValueSetError(TimingRepeat::periodUnit)
    private val invalidDayOfWeekError = InvalidValueSetError(TimingRepeat::dayOfWeek)
    private val invalidWhenError = InvalidValueSetError(TimingRepeat::`when`)
    private val invalidBoundsError = InvalidDynamicValueError(TimingRepeat::bounds, acceptedBoundTypes)

    private val requiredDurationUnitError = FHIRError(
        code = "R4_TMRPT_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "if there's a duration, there needs to be duration units",
        location = LocationContext(TimingRepeat::class)
    )
    private val requiredPeriodUnitError = FHIRError(
        code = "R4_TMRPT_002",
        severity = ValidationIssueSeverity.ERROR,
        description = "if there's a period, there needs to be period units",
        location = LocationContext(TimingRepeat::class)
    )
    private val negativeDurationError = FHIRError(
        code = "R4_TMRPT_003",
        severity = ValidationIssueSeverity.ERROR,
        description = "duration SHALL be a non-negative value",
        location = LocationContext(TimingRepeat::duration)
    )
    private val negativePeriodError = FHIRError(
        code = "R4_TMRPT_004",
        severity = ValidationIssueSeverity.ERROR,
        description = "period SHALL be a non-negative value",
        location = LocationContext(TimingRepeat::period)
    )
    private val requiredPeriodError = FHIRError(
        code = "R4_TMRPT_005",
        severity = ValidationIssueSeverity.ERROR,
        description = "If there's a periodMax, there must be a period",
        location = LocationContext(TimingRepeat::class)
    )
    private val requiredDurationError = FHIRError(
        code = "R4_TMRPT_006",
        severity = ValidationIssueSeverity.ERROR,
        description = "If there's a durationMax, there must be a duration",
        location = LocationContext(TimingRepeat::class)
    )
    private val requiredCountError = FHIRError(
        code = "R4_TMRPT_007",
        severity = ValidationIssueSeverity.ERROR,
        description = "If there's a countMax, there must be a count",
        location = LocationContext(TimingRepeat::class)
    )
    private val requiredWhenError = FHIRError(
        code = "R4_TMRPT_008",
        severity = ValidationIssueSeverity.ERROR,
        description = "If there's an offset, there must be a when (and not ${invalidWhens.joinToString()})",
        location = LocationContext(TimingRepeat::class)
    )
    private val whenOrTimeOfDay = FHIRError(
        code = "R4_TMRPT_009",
        severity = ValidationIssueSeverity.ERROR,
        description = "If there's a timeOfDay, there cannot be a when, or vice versa",
        location = LocationContext(TimingRepeat::class)
    )

    override fun validateElement(element: TimingRepeat, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.durationUnit?.let {
                checkCodedEnum<UnitOfTime>(it, invalidDurationUnitError, parentContext)
            }
            element.periodUnit?.let {
                checkCodedEnum<UnitOfTime>(it, invalidPeriodUnitError, parentContext)
            }

            checkTrue(
                element.dayOfWeek.none { runCatching { CodedEnum.byCode<DayOfWeek>(it.value) }.getOrNull() == null },
                invalidDayOfWeekError,
                parentContext
            )
            checkTrue(
                element.`when`.none { runCatching { CodedEnum.byCode<EventTiming>(it.value) }.getOrNull() == null },
                invalidWhenError,
                parentContext
            )

            element.bounds?.let { bounds ->
                checkTrue(acceptedBoundTypes.contains(bounds.type), invalidBoundsError, parentContext)
            }

            checkTrue(
                (element.duration == null || element.durationUnit != null),
                requiredDurationUnitError,
                parentContext
            )
            checkTrue((element.period == null || element.periodUnit != null), requiredPeriodUnitError, parentContext)
            checkTrue((element.duration == null || element.duration >= 0), negativeDurationError, parentContext)
            checkTrue((element.period == null || element.period >= 0), negativePeriodError, parentContext)
            checkTrue((element.periodMax == null || element.period != null), requiredPeriodError, parentContext)
            checkTrue((element.durationMax == null || element.duration != null), requiredDurationError, parentContext)
            checkTrue((element.countMax == null || element.count != null), requiredCountError, parentContext)
            checkTrue(
                (
                    element.offset == null || (
                        element.`when`.isNotEmpty() && element.`when`.none {
                            invalidWhens.contains(
                                it.value
                            )
                        }
                        )
                    ),
                requiredWhenError, parentContext
            )
            checkTrue((element.timeOfDay.isEmpty() || element.`when`.isEmpty()), whenOrTimeOfDay, parentContext)
        }
    }
}
