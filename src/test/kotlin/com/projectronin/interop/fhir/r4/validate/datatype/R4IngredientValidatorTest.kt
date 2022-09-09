package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Ingredient
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4IngredientValidatorTest {
    @Test
    fun `fails for missing item`() {
        val exception = assertThrows<Exception> {
            val ingredient = Ingredient()
            R4IngredientValidator.validate(ingredient).alertIfErrors()
        }

        assertEquals(
            "Encountered validation error(s):\nERROR REQ_FIELD: item is a required element @ Ingredient.item",
            exception.message
        )
    }

    @Test
    fun `fails for bad item dynamic value`() {
        val exception = assertThrows<IllegalArgumentException> {
            val ingredient = Ingredient(
                item = DynamicValue(DynamicValueType.INTEGER, 5)
            )
            R4IngredientValidator.validate(ingredient).alertIfErrors()
        }

        assertEquals(
            "Encountered validation error(s):\nERROR INV_DYN_VAL: item can only be one of the following: CodeableConcept, Reference @ Ingredient.item",
            exception.message
        )
    }

    @Test
    fun `failure includes parent context`() {
        val exception = assertThrows<IllegalArgumentException> {
            val ingredient = Ingredient(
                item = DynamicValue(DynamicValueType.INTEGER, 5)
            )
            R4IngredientValidator.validate(ingredient, LocationContext("Test", "field")).alertIfErrors()
        }

        assertEquals(
            "Encountered validation error(s):\nERROR INV_DYN_VAL: item can only be one of the following: CodeableConcept, Reference @ Test.field.item",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val ingredient = Ingredient(
            item = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = "test"))
        )
        R4IngredientValidator.validate(ingredient).alertIfErrors()
    }
}
