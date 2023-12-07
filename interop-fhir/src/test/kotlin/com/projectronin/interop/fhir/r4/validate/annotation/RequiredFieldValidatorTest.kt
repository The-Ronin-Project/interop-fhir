package com.projectronin.interop.fhir.r4.validate.annotation

import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validatable
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.annotation.RequiredField
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RequiredFieldValidatorTest {
    private val validator = RequiredFieldValidator()

    @Test
    fun `fails validation when non-collection is null`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                Validation().apply {
                    validator.validateAnnotations(
                        listOf(RequiredField()),
                        NonCollectionElement::value,
                        FHIRString::class,
                        NonCollectionElement(null),
                        "NonCollectionElement",
                        LocationContext(NonCollectionElement::class),
                        this,
                    )
                }.alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: value is a required element @ NonCollectionElement.value",
            exception.message,
        )
    }

    @Test
    fun `passes validation when non-collection is not null`() {
        Validation().apply {
            validator.validateAnnotations(
                listOf(RequiredField()),
                NonCollectionElement::value,
                FHIRString::class,
                NonCollectionElement(FHIRString("value")),
                "NonCollectionElement",
                LocationContext(NonCollectionElement::class),
                this,
            )
        }.alertIfErrors()
    }

    @Test
    fun `fails validation when collection is null`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                Validation().apply {
                    validator.validateAnnotations(
                        listOf(RequiredField()),
                        CollectionElement::value,
                        List::class,
                        CollectionElement(null),
                        "CollectionElement",
                        LocationContext(CollectionElement::class),
                        this,
                    )
                }.alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: value is a required element @ CollectionElement.value",
            exception.message,
        )
    }

    @Test
    fun `fails validation when collection is empty`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                Validation().apply {
                    validator.validateAnnotations(
                        listOf(RequiredField()),
                        CollectionElement::value,
                        List::class,
                        CollectionElement(emptyList()),
                        "CollectionElement",
                        LocationContext(CollectionElement::class),
                        this,
                    )
                }.alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: value is a required element @ CollectionElement.value",
            exception.message,
        )
    }

    @Test
    fun `passes validation when collection is populated`() {
        Validation().apply {
            validator.validateAnnotations(
                listOf(RequiredField()),
                CollectionElement::value,
                List::class,
                CollectionElement(listOf(FHIRBoolean.TRUE)),
                "CollectionElement",
                LocationContext(CollectionElement::class),
                this,
            )
        }.alertIfErrors()
    }

    data class NonCollectionElement(
        val value: FHIRString?,
    ) : Validatable<NonCollectionElement>

    data class CollectionElement(
        val value: List<FHIRBoolean>?,
    ) : Validatable<CollectionElement>
}
