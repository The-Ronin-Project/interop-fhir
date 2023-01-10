package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.resource.Ingredient
import com.projectronin.interop.fhir.r4.resource.Medication
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class R4MedicationValidatorTest {
    @Test
    fun `validate - succeeds with empty object`() {
        val medication = Medication()
        R4MedicationValidator.validate(medication).alertIfErrors()
    }

    @Test
    fun `validate - fails on bad medication status`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4MedicationValidator.validate(
                Medication(
                    status = Code("unsupported-value")
                )
            ).alertIfErrors()
        }

        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-value' is outside of required value set @ Medication.status",
            exception.message
        )
    }

    @Test
    fun `validate - succeeds with empty ingredient list`() {
        R4MedicationValidator.validate(
            Medication(
                code = CodeableConcept(
                    text = FHIRString("b"),
                    coding = listOf(
                        Coding(
                            system = CodeSystem.RXNORM.uri,
                            code = Code("b"),
                            version = FHIRString("1.0.0"),
                            display = FHIRString("b")
                        )
                    )
                ),
                ingredient = listOf()
            )
        ).alertIfErrors()
    }

    @Test
    fun `validate - succeeds with no ingredient list`() {
        R4MedicationValidator.validate(
            Medication(
                code = CodeableConcept(
                    text = FHIRString("b"),
                    coding = listOf(
                        Coding(
                            system = CodeSystem.RXNORM.uri,
                            code = Code("b"),
                            version = FHIRString("1.0.0"),
                            display = FHIRString("b")
                        )
                    )
                )
            )
        ).alertIfErrors()
    }
}

class R4IngredientValidatorTest {
    @Test
    fun `fails for missing item`() {
        val exception = assertThrows<Exception> {
            val ingredient = Ingredient()
            R4IngredientValidator.validate(ingredient).alertIfErrors()
        }

        Assertions.assertEquals(
            "Encountered validation error(s):\nERROR REQ_FIELD: item is a required element @ Ingredient.item",
            exception.message
        )
    }

    @Test
    fun `fails for bad item dynamic value`() {
        val exception = assertThrows<IllegalArgumentException> {
            val ingredient = Ingredient(
                item = DynamicValue(DynamicValueType.INTEGER, FHIRInteger(5))
            )
            R4IngredientValidator.validate(ingredient).alertIfErrors()
        }

        Assertions.assertEquals(
            "Encountered validation error(s):\nERROR INV_DYN_VAL: item can only be one of the following: CodeableConcept, Reference @ Ingredient.item",
            exception.message
        )
    }

    @Test
    fun `failure includes parent context`() {
        val exception = assertThrows<IllegalArgumentException> {
            val ingredient = Ingredient(
                item = DynamicValue(DynamicValueType.INTEGER, FHIRInteger(5))
            )
            R4IngredientValidator.validate(ingredient, LocationContext("Test", "field")).alertIfErrors()
        }

        Assertions.assertEquals(
            "Encountered validation error(s):\nERROR INV_DYN_VAL: item can only be one of the following: CodeableConcept, Reference @ Test.field.item",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val ingredient = Ingredient(
            item = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = FHIRString("test")))
        )
        R4IngredientValidator.validate(ingredient).alertIfErrors()
    }
}
