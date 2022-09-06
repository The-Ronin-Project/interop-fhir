package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.ConditionEvidence
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 ConditionEvidence](http://hl7.org/fhir/R4/condition-definitions.html#Condition.evidence)
 */
object R4ConditionEvidenceValidator : R4ElementContainingValidator<ConditionEvidence>() {
    private val codeOrDetailError = FHIRError(
        code = "R4_CNDEV_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "evidence SHALL have code or details",
        location = LocationContext(ConditionEvidence::class)
    )

    override fun validateElement(element: ConditionEvidence, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkTrue((element.code.isNotEmpty() || element.detail.isNotEmpty()), codeOrDetailError, parentContext)
        }
    }
}
