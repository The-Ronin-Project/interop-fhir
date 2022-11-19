package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CarePlanDetail
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4CarePlanDetailValidatorTest {
    @Test
    fun `validates successfully`() {
        val carePlanDetail = CarePlanDetail(
            id = "12345",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.INTEGER, 1)
                )
            ),
            kind = Code("Appointment"),
            goal = listOf(
                Reference(reference = "ABC123")
            ),
            status = Code("scheduled"),
            scheduled = DynamicValue(DynamicValueType.STRING, "Value"),
            product = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = "product")),
            description = "Description"
        )
        R4CarePlanDetailValidator.validate(carePlanDetail).alertIfErrors()
    }

    @Test
    fun `fails on wrong dynamic type values for scheduled`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlanDetail = CarePlanDetail(
                id = "12345",
                extension = listOf(
                    Extension(
                        url = Uri("http://localhost/extension"),
                        value = DynamicValue(DynamicValueType.STRING, "Value")
                    )
                ),
                modifierExtension = listOf(
                    Extension(
                        url = Uri("http://localhost/modifier-extension"),
                        value = DynamicValue(DynamicValueType.INTEGER, 1)
                    )
                ),
                kind = Code("Appointment"),
                goal = listOf(
                    Reference(reference = "ABC123")
                ),
                status = Code("scheduled"),
                scheduled = DynamicValue(DynamicValueType.BOOLEAN, true),
                product = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = "product")),
                description = "Description"
            )
            R4CarePlanDetailValidator.validate(carePlanDetail).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: scheduled can only be one of the following: Timing, Period, String @ CarePlanDetail.scheduled",
            exception.message
        )
    }

    @Test
    fun `fails on wrong dynamic type values for product`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlanDetail = CarePlanDetail(
                id = "12345",
                extension = listOf(
                    Extension(
                        url = Uri("http://localhost/extension"),
                        value = DynamicValue(DynamicValueType.STRING, "Value")
                    )
                ),
                modifierExtension = listOf(
                    Extension(
                        url = Uri("http://localhost/modifier-extension"),
                        value = DynamicValue(DynamicValueType.INTEGER, 1)
                    )
                ),
                kind = Code("Appointment"),
                goal = listOf(
                    Reference(reference = "ABC123")
                ),
                status = Code("scheduled"),
                scheduled = DynamicValue(DynamicValueType.STRING, "Value"),
                product = DynamicValue(DynamicValueType.STRING, "product"),
                description = "Description"
            )
            R4CarePlanDetailValidator.validate(carePlanDetail).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: product can only be one of the following: CodeableConcept, Reference @ CarePlanDetail.product",
            exception.message
        )
    }

    @Test
    fun `fails on invalid code for kind`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlanDetail = CarePlanDetail(
                id = "12345",
                extension = listOf(
                    Extension(
                        url = Uri("http://localhost/extension"),
                        value = DynamicValue(DynamicValueType.STRING, "Value")
                    )
                ),
                modifierExtension = listOf(
                    Extension(
                        url = Uri("http://localhost/modifier-extension"),
                        value = DynamicValue(DynamicValueType.INTEGER, 1)
                    )
                ),
                kind = Code("potato"),
                goal = listOf(
                    Reference(reference = "ABC123")
                ),
                status = Code("scheduled"),
                scheduled = DynamicValue(DynamicValueType.STRING, "Value"),
                product = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = "product")),
                description = "Description"
            )
            R4CarePlanDetailValidator.validate(carePlanDetail).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: kind is outside of required value set @ CarePlanDetail.kind",
            exception.message
        )
    }

    @Test
    fun `fails on null status`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlanDetail = CarePlanDetail(
                id = "12345",
                extension = listOf(
                    Extension(
                        url = Uri("http://localhost/extension"),
                        value = DynamicValue(DynamicValueType.STRING, "Value")
                    )
                ),
                modifierExtension = listOf(
                    Extension(
                        url = Uri("http://localhost/modifier-extension"),
                        value = DynamicValue(DynamicValueType.INTEGER, 1)
                    )
                ),
                kind = Code("Appointment"),
                goal = listOf(
                    Reference(reference = "ABC123")
                ),
                status = null,
                scheduled = DynamicValue(DynamicValueType.STRING, "Value"),
                product = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = "product")),
                description = "Description"
            )
            R4CarePlanDetailValidator.validate(carePlanDetail).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ CarePlanDetail.status",
            exception.message
        )
    }

    @Test
    fun `fails on invalid code for status`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlanDetail = CarePlanDetail(
                id = "12345",
                extension = listOf(
                    Extension(
                        url = Uri("http://localhost/extension"),
                        value = DynamicValue(DynamicValueType.STRING, "Value")
                    )
                ),
                modifierExtension = listOf(
                    Extension(
                        url = Uri("http://localhost/modifier-extension"),
                        value = DynamicValue(DynamicValueType.INTEGER, 1)
                    )
                ),
                kind = Code("Appointment"),
                goal = listOf(
                    Reference(reference = "ABC123")
                ),
                status = Code("potato"),
                scheduled = DynamicValue(DynamicValueType.STRING, "Value"),
                product = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = "product")),
                description = "Description"
            )
            R4CarePlanDetailValidator.validate(carePlanDetail).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: status is outside of required value set @ CarePlanDetail.status",
            exception.message
        )
    }
}
