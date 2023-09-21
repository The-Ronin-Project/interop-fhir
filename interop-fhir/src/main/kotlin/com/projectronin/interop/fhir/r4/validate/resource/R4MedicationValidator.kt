package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.Ingredient
import com.projectronin.interop.fhir.r4.resource.Medication
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.MedicationStatus
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Medication](https://www.hl7.org/fhir/R4/medication.html)
 */
object R4MedicationValidator : R4ElementContainingValidator<Medication>() {
    override fun validateElement(element: Medication, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.status?.let { code ->
                checkCodedEnum<MedicationStatus>(
                    code,
                    InvalidValueSetError(Medication::status, code.value),
                    parentContext
                )
            }
        }
    }
}

/**
 * Validator for the [R4 Ingredient](https://www.hl7.org/fhir/R4/medication-definitions.html#Medication.ingredient)
 */
object R4IngredientValidator : R4ElementContainingValidator<Ingredient>() {
    private val acceptedItem = listOf(DynamicValueType.CODEABLE_CONCEPT, DynamicValueType.REFERENCE)

    private val invalidItemError = InvalidDynamicValueError(Ingredient::item, acceptedItem)

    override fun validateElement(element: Ingredient, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.item?.let {
                checkTrue(acceptedItem.contains(element.item.type), invalidItemError, parentContext)
            }
        }
    }
}
