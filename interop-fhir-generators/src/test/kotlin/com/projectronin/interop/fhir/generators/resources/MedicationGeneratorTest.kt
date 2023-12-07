package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.DynamicValues
import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.ratio
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MedicationGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val medication = medication {}
        assertNull(medication.id)
        assertNull(medication.meta)
        assertNull(medication.implicitRules)
        assertNull(medication.language)
        assertNull(medication.text)
        assertEquals(0, medication.contained.size)
        assertEquals(0, medication.extension.size)
        assertEquals(0, medication.modifierExtension.size)
        assertTrue(medication.identifier.isEmpty())
        assertNull(medication.code)
        assertNull(medication.status)
        assertNull(medication.manufacturer)
        assertNull(medication.form)
        assertNull(medication.amount)
        assertTrue(medication.ingredient.isEmpty())
        assertNull(medication.batch)
    }

    @Test
    fun `function works with parameters`() {
        val medication =
            medication {
                id of Id("id")
                identifier of
                    listOf(
                        identifier {},
                    )
                code of
                    codeableConcept {
                        text of "code"
                    }
                status of "status"
                manufacturer of reference("Organization", "123")
                form of
                    codeableConcept {
                        text of "code"
                    }
                amount of
                    ratio {
                        numerator of Quantity(value = Decimal(BigDecimal(2.0)))
                        denominator of Quantity(value = Decimal(BigDecimal(3.5)))
                    }
                ingredient of
                    listOf(
                        ingredient {
                            isActive of true
                        },
                    )
                batch of
                    batch {
                        lotNumber of "12345"
                        expirationDate of DateTime("2017-01-01T00:00:00.000Z")
                    }
            }
        assertEquals("id", medication.id?.value)
        assertEquals(1, medication.identifier.size)
        assertEquals("code", medication.code?.text?.value)
        assertEquals("status", medication.status?.value)
        assertEquals(
            "Organization/123",
            medication.manufacturer?.reference?.value,
        )
        assertEquals("code", medication.form?.text?.value)
        assertEquals(Decimal(BigDecimal(2.0)), medication.amount?.numerator?.value)
        assertEquals(Decimal(BigDecimal(3.5)), medication.amount?.denominator?.value)
        assertEquals(true, medication.ingredient.first().isActive?.value)
        assertEquals("12345", medication.batch?.lotNumber?.value)
        assertEquals("2017-01-01T00:00:00.000Z", medication.batch?.expirationDate?.value)
    }
}

class IngredientGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val ingredient = ingredient {}
        assertNull(ingredient.id)
        assertEquals(0, ingredient.extension.size)
        assertEquals(0, ingredient.modifierExtension.size)
        assertNotNull(ingredient.item)
        assertNull(ingredient.isActive)
        assertNull(ingredient.strength)
    }

    @Test
    fun `function works with parameters`() {
        val ingredient =
            ingredient {
                item of
                    DynamicValues.codeableConcept(
                        codeableConcept {
                            text of "code"
                        },
                    )
                isActive of FHIRBoolean(false)
                strength of
                    Ratio(
                        numerator = Quantity(value = Decimal(BigDecimal(6.2))),
                        denominator = Quantity(value = Decimal(BigDecimal(9.4))),
                    )
            }

        val ingredientWithReference =
            ingredient {
                item of DynamicValues.reference(reference("Substance", "5678"))
                isActive of FHIRBoolean(false)
                strength of
                    Ratio(
                        numerator = Quantity(value = Decimal(BigDecimal(6.2))),
                        denominator = Quantity(value = Decimal(BigDecimal(9.4))),
                    )
            }

        assertEquals(DynamicValueType.CODEABLE_CONCEPT, ingredient.item?.type)
        assertEquals(codeableConcept { text of "code" }, ingredient.item?.value)
        assertEquals(false, ingredient.isActive?.value)
        assertEquals(Decimal(BigDecimal(6.2)), ingredient.strength?.numerator?.value)
        assertEquals(Decimal(BigDecimal(9.4)), ingredient.strength?.denominator?.value)
        assertEquals(DynamicValueType.REFERENCE, ingredientWithReference.item?.type)
        assertEquals(reference("Substance", "5678"), ingredientWithReference.item?.value)
    }
}

class BatchGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val batch = batch {}
        assertNull(batch.id)
        assertEquals(0, batch.extension.size)
        assertEquals(0, batch.modifierExtension.size)
        assertNull(batch.lotNumber)
        assertNull(batch.expirationDate)
    }

    @Test
    fun `function works with parameters`() {
        val batch =
            batch {
                lotNumber of "123232345"
                expirationDate of DateTime("2019-12-01T00:00:00.000Z")
            }
        assertEquals("123232345", batch.lotNumber?.value)
        assertEquals("2019-12-01T00:00:00.000Z", batch.expirationDate?.value)
    }
}
