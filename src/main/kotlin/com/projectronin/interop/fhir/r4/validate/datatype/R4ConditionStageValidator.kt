package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.ConditionStage
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 ConditionStage](http://hl7.org/fhir/R4/condition-definitions.html#Condition.stage)
 */
object R4ConditionStageValidator : R4ElementContainingValidator<ConditionStage>() {
    private val summaryOrAssessmentError = FHIRError(
        code = "R4_CNDSTG_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "stage SHALL have summary or assessment",
        location = LocationContext(ConditionStage::class)
    )

    override fun validateElement(element: ConditionStage, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkTrue(
                (element.summary != null || element.assessment.isNotEmpty()),
                summaryOrAssessmentError,
                parentContext
            )
        }
    }
}
