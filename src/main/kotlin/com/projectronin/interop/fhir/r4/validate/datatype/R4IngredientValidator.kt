package com.projectronin.interop.fhir.r4.validate.datatype
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Ingredient
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Ingredient](https://www.hl7.org/fhir/R4/medication-definitions.html#Medication.ingredient)
 */
object R4IngredientValidator : R4ElementContainingValidator<Ingredient>() {
    private val acceptedItem = listOf(DynamicValueType.CODEABLE_CONCEPT, DynamicValueType.REFERENCE)

    private val requiredItemError = RequiredFieldError(Ingredient::item)
    private val invalidItemError = InvalidDynamicValueError(Ingredient::item, acceptedItem)

    override fun validateElement(element: Ingredient, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.item, requiredItemError, parentContext)
            ifNotNull(element.item) {
                checkTrue(acceptedItem.contains(element.item.type), invalidItemError, parentContext)
            }
        }
    }
}
