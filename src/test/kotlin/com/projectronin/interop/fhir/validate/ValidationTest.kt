package com.projectronin.interop.fhir.validate

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ValidationTest {
    @Test
    fun `check with true value does not create an error`() {
        val validation = Validation()
        validation.check(true) { "My message when true" }

        val errors = validation.errors()
        assertEquals(listOf<Exception>(), errors)
    }

    @Test
    fun `check with false value creates an error`() {
        val validation = Validation()
        validation.check(false) { "My message when false" }

        val errors = validation.errors()
        assertEquals(1, errors.size)
        assertEquals("My message when false", errors[0].message)
    }

    @Test
    fun `notNull with null value creates an error`() {
        val validation = Validation()
        validation.notNull(null) { "It was null" }

        val errors = validation.errors()
        assertEquals(1, errors.size)
        assertEquals("It was null", errors[0].message)
    }

    @Test
    fun `notNull with non-null value does not create an error`() {
        val validation = Validation()
        validation.notNull("NotNull") { "It was not null" }

        val errors = validation.errors()
        assertEquals(listOf<Exception>(), errors)
    }

    @Test
    fun `ifNotNull ignores block if value is null`() {
        val validation = Validation()
        validation.ifNotNull(null) {
            notNull(null) { "It was null" }
        }

        val errors = validation.errors()
        assertEquals(listOf<Exception>(), errors)
    }

    @Test
    fun `ifNotNull applies block if value is not null`() {
        val validation = Validation()
        validation.ifNotNull("notNull") {
            notNull(null) { "It was null" }
        }

        val errors = validation.errors()
        assertEquals(1, errors.size)
        assertEquals("It was null", errors[0].message)
    }

    @Test
    fun `merge combines 2 validations`() {
        val validation1 = Validation()
        validation1.notNull(null) { "Null" }

        val validation2 = Validation()
        validation2.notNull(null) { "Also null" }

        validation1.merge(validation2)

        val errors = validation1.errors()
        assertEquals(2, errors.size)
        assertEquals("Null", errors[0].message)
        assertEquals("Also null", errors[1].message)
    }

    @Test
    fun `alertIfErrors does nothing if no errors exist`() {
        val validation = Validation()
        validation.check(true) { "My message when true" }
        validation.alertIfErrors()
    }

    @Test
    fun `alertIfErrors returns a single exception if only one error exists`() {
        val validation = Validation()
        validation.check(false) { "My message when false" }

        val exception = assertThrows<IllegalArgumentException> {
            validation.alertIfErrors()
        }
        assertEquals("My message when false", exception.message)
    }

    @Test
    fun `alertIfErrors returns an error combining exceptions`() {
        val validation = Validation()
        validation.check(false) { "My message when false" }
        validation.check(false) { "Second message when false" }

        val exception = assertThrows<IllegalArgumentException> {
            validation.alertIfErrors()
        }
        assertEquals(
            "Encountered multiple validation errors:\nMy message when false\nSecond message when false",
            exception.message
        )
    }

    @Test
    fun `validation function creates an instance`() {
        val validation = validation {
            check(false) { "My message when false" }
        }

        val errors = validation.errors()
        assertEquals(1, errors.size)
        assertEquals("My message when false", errors[0].message)
    }

    @Test
    fun `validateAndAlert function creates an instance and does nothing if no errors exist`() {
        validateAndAlert {
            check(true) { "My message when true" }
        }
    }

    @Test
    fun `validateAndAlert function creates an instance and throws an exception if errors exist`() {
        val exception = assertThrows<IllegalArgumentException> {
            validateAndAlert {
                check(false) { "My message when false" }
            }
        }
        assertEquals("My message when false", exception.message)
    }
}
