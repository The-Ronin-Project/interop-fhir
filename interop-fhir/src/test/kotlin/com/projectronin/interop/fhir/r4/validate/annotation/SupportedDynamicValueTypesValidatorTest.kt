package com.projectronin.interop.fhir.r4.validate.annotation

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validatable
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.annotation.SupportedDynamicValueTypes
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SupportedDynamicValueTypesValidatorTest {
    private val validator = SupportedDynamicValueTypesValidator()

    @Test
    fun `no validation occurs when no dynamic value types provided`() {
        val annotation = SupportedDynamicValueTypes(value = emptyArray())
        val element = DynamicValueElement(DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.TRUE))
        Validation().apply {
            validator.validateAnnotations(
                listOf(annotation),
                DynamicValueElement::value,
                DynamicValue::class,
                element,
                "DynamicValueElement",
                LocationContext(DynamicValueElement::class),
                this,
            )
        }.alertIfErrors()
    }

    @Test
    fun `passes when provided a non-DynamicValue`() {
        val annotation = SupportedDynamicValueTypes(DynamicValueType.ANNOTATION)
        val element = NonDynamicValueElement(FHIRBoolean.TRUE)
        Validation().apply {
            validator.validateAnnotations(
                listOf(annotation),
                NonDynamicValueElement::value,
                FHIRBoolean::class,
                element,
                "NonDynamicValueElement",
                LocationContext(NonDynamicValueElement::class),
                this,
            )
        }.alertIfErrors()
    }

    @Test
    fun `passes when provided a null DynamicValue`() {
        val annotation = SupportedDynamicValueTypes(DynamicValueType.ANNOTATION)
        val element = DynamicValueElement(null)
        Validation().apply {
            validator.validateAnnotations(
                listOf(annotation),
                DynamicValueElement::value,
                DynamicValue::class,
                element,
                "DynamicValueElement",
                LocationContext(DynamicValueElement::class),
                this,
            )
        }.alertIfErrors()
    }

    @Test
    fun `passes when provided a DynamicValue with supported type`() {
        val annotation =
            SupportedDynamicValueTypes(DynamicValueType.STRING, DynamicValueType.ANNOTATION, DynamicValueType.BOOLEAN)
        val element = DynamicValueElement(DynamicValue(DynamicValueType.STRING, FHIRString("value")))
        Validation().apply {
            validator.validateAnnotations(
                listOf(annotation),
                DynamicValueElement::value,
                DynamicValue::class,
                element,
                "DynamicValueElement",
                LocationContext(DynamicValueElement::class),
                this,
            )
        }.alertIfErrors()
    }

    @Test
    fun `fails when provided a DynamicValue with unsupported type`() {
        val annotation =
            SupportedDynamicValueTypes(DynamicValueType.STRING, DynamicValueType.ANNOTATION, DynamicValueType.REFERENCE)
        val element = DynamicValueElement(DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.TRUE))

        val exception =
            assertThrows<IllegalArgumentException> {
                Validation().apply {
                    validator.validateAnnotations(
                        listOf(annotation),
                        DynamicValueElement::value,
                        DynamicValue::class,
                        element,
                        "DynamicValueElement",
                        LocationContext(DynamicValueElement::class),
                        this,
                    )
                }.alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: value can only be one of the following: String, Annotation, Reference @ DynamicValueElement.value",
            exception.message,
        )
    }

    data class DynamicValueElement(
        val value: DynamicValue<Any>?,
    ) : Validatable<DynamicValueElement>

    data class NonDynamicValueElement(
        val value: FHIRBoolean?,
    ) : Validatable<NonDynamicValueElement>
}
