package com.projectronin.interop.fhir.r4.validate.annotation

import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validatable
import com.projectronin.interop.fhir.validate.Validation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.lang.NonNull
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

class BaseValidationAnnotationValidatorTest {
    class TestValidator : BaseValidationAnnotationValidator<Retention>(Retention::class) {
        var requests = 0
        var annotationsReceived = 0

        override fun <T : Validatable<T>> validateSupportedAnnotations(
            annotations: List<Retention>,
            property: KProperty1<T, *>,
            kotlinType: KClass<*>,
            element: T,
            elementName: String,
            parentContext: LocationContext?,
            validation: Validation
        ) {
            requests++
            annotationsReceived += annotations.size
        }
    }

    @Test
    fun `non-supported annotations are removed`() {
        val initialAnnotations =
            listOf(Retention(AnnotationRetention.SOURCE), Retention(AnnotationRetention.RUNTIME), NonNull())

        val validator = TestValidator()
        validator.validateAnnotations(
            initialAnnotations,
            Address::city,
            Address::class,
            Address(),
            "Address",
            null,
            Validation()
        )

        assertEquals(1, validator.requests)
        assertEquals(2, validator.annotationsReceived)
    }

    @Test
    fun `does not call to validate when no supported annotation is provided`() {
        val initialAnnotations =
            listOf(Target(AnnotationTarget.PROPERTY), NonNull())

        val validator = TestValidator()
        validator.validateAnnotations(
            initialAnnotations,
            Address::city,
            Address::class,
            Address(),
            "Address",
            null,
            Validation()
        )

        assertEquals(0, validator.requests)
        assertEquals(0, validator.annotationsReceived)
    }
}
