package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.MedicationAdministration
import com.projectronin.interop.fhir.r4.resource.MedicationAdministrationDosage
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 MedicationAdministration](https://www.hl7.org/fhir/R4/medicationrequest.html)
 */
object R4MedicationAdministrationValidator : R4ElementContainingValidator<MedicationAdministration>() {
    private val acceptedEffectiveTypes = listOf(DynamicValueType.DATE_TIME, DynamicValueType.PERIOD)
    private val acceptedMedicationTypes = listOf(DynamicValueType.CODEABLE_CONCEPT, DynamicValueType.REFERENCE)

    private val invalidMedicationError =
        InvalidDynamicValueError(MedicationAdministration::medication, acceptedMedicationTypes)
    private val invalidEffectiveError =
        InvalidDynamicValueError(MedicationAdministration::effective, acceptedEffectiveTypes)

    override fun validateElement(
        element: MedicationAdministration,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            element.effective?.let {
                checkTrue(
                    acceptedEffectiveTypes.contains(element.effective.type),
                    invalidEffectiveError,
                    parentContext
                )
            }

            element.medication?.let {
                checkTrue(
                    acceptedMedicationTypes.contains(element.medication.type),
                    invalidMedicationError,
                    parentContext
                )
            }
        }
    }
}

object R4MedAdminDosageValidator : R4ElementContainingValidator<MedicationAdministrationDosage>() {
    private val acceptedRateTypes = listOf(DynamicValueType.RATIO, DynamicValueType.QUANTITY)
    private val invalidRateError =
        InvalidDynamicValueError(MedicationAdministrationDosage::rate, acceptedRateTypes)
    private val requiredDoseOrRateError = FHIRError(
        code = "R4_MAD_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "If dosage is provided, it SHALL have at least one of dosage.dose or dosage.rate[x]",
        location = LocationContext(MedicationAdministration::dosage)
    )

    override fun validateElement(
        element: MedicationAdministrationDosage,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkTrue(
                (element.rate != null || element.dose != null),
                requiredDoseOrRateError,
                parentContext
            )
            ifNotNull(element.rate) {
                checkTrue(
                    acceptedRateTypes.contains(element.rate!!.type),
                    invalidRateError,
                    parentContext
                )
            }
        }
    }
}
